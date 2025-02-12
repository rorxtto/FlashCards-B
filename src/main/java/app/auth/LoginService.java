//AuthenticationService.java
package app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

import app.config.JwtServiceGenerator;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String HOTMART_API_URL = "https://developers.hotmart.com/payments/api/v1/subscriptions";
    private static final String HOTMART_AUTH_URL = "https://api-sec-vlc.hotmart.com/security/oauth/token";
    private static final String CLIENT_ID = "55d72f9d-3c3e-4ca6-ab3b-8e6cca8e1bf7";
    private static final String CLIENT_SECRET = "4002264d-4b3f-48ce-8ad5-4f92a078adee";
    private static final String BASIC_AUTH = "Basic NTVkNzJmOWQtM2MzZS00Y2E2LWFiM2ItOGU2Y2NhOGUxYmY3OjQwMDIyNjRkLTRiM2YtNDhjZS04YWQ1LTRmOTJhMDc4YWRlZQ==";

    private String accessToken;
    private final ReentrantLock lock = new ReentrantLock();

    public String logar(Login login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        User user = repository.findByUsername(login.getUsername()).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!user.getRole().contains("ADMIN") && !isAssinaturaAtiva(user.getUsername())) {
            throw new RuntimeException("Assinatura inativa ou inexistente");
        }

        return jwtService.generateToken(user);
    }

    private boolean isAssinaturaAtiva(String username) {
        RestTemplate restTemplate = new RestTemplate();
        String token = getAccessToken();

        String url = UriComponentsBuilder.fromHttpUrl(HOTMART_API_URL)
                .queryParam("product_id", "4805348")
                .queryParam("subscriber_email", username)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        
        System.out.println("URL: " + url);
        System.out.println("Token: " + token);
        System.out.println("Headers: " + headers);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        if (items != null && !items.isEmpty()) {
            String status = (String) items.get(0).get("status");
            return "ACTIVE".equalsIgnoreCase(status);
        }

        return false;
    }

    private String getAccessToken() {
        lock.lock();
        try {
            if (accessToken == null || accessToken.isEmpty()) {
                refreshAccessToken();
            }
            return accessToken;
        } finally {
            lock.unlock();
        }
    }

    private void refreshAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BASIC_AUTH);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        String body = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(HOTMART_AUTH_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                accessToken = (String) responseBody.get("access_token");
            }
        } else {
            throw new RuntimeException("Falha ao obter token de acesso");
        }
    }
}
