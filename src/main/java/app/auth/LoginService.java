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
import java.util.Map;
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

	    // Token da API Hotmart (substituir pelo valor real)
	    private final String HOTMART_API_URL = "https://api.hotmart.com/payments/v1/subscriptions";
	    
	    

	    public String logar(Login login) {
	        // Autenticar usuário
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                login.getUsername(),
	                login.getPassword()
	            )
	        );

	        // Buscar usuário no banco
	        User user = repository.findByUsername(login.getUsername()).orElseThrow(() ->
	            new RuntimeException("Usuário não encontrado")
	        );

	        // Se o usuário tiver a role ADMIN, não verificar assinatura
	        if (user.getRole().contains("ADMIN")) {
	            return jwtService.generateToken(user);
	        }

	        // Validar assinatura ativa no Hotmart
	        if (!verificarAssinaturaAtiva(user.getUsername())) {
	            throw new RuntimeException("Assinatura inativa ou não encontrada");
	        }

	        // Gerar JWT e retornar
	        return jwtService.generateToken(user);
	    }

	    private String obterNovoTokenHotmart() {
	        RestTemplate restTemplate = new RestTemplate();

	        String url = "https://api-sec-vlc.hotmart.com/security/oauth/token";
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        headers.set("Authorization", "Basic NTVkNzJmOWQtM2MzZS00Y2E2LWFiM2ItOGU2Y2NhOGUxYmY3OjQwMDIyNjRkLTRiM2YtNDhjZS04YWQ1LTRmOTJhMDc4YWRlZQ==");

	        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	        body.add("grant_type", "client_credentials");
	        body.add("client_id", "55d72f9d-3c3e-4ca6-ab3b-8e6cca8e1bf7");
	        body.add("client_secret", "4002264d-4b3f-48ce-8ad5-4f92a078adee");

	        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

	        try {
	            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
	            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
	                return (String) response.getBody().get("access_token");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        throw new RuntimeException("Erro ao obter novo token da Hotmart.");
	    }

	    private boolean verificarAssinaturaAtiva(String email) {
	        String tokenAtualizado = obterNovoTokenHotmart();

	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(tokenAtualizado);
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        String url = HOTMART_API_URL + "?subscriber_email=" + email + "&product_id=4805348";

	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        try {
	            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
	            if (response.getStatusCode() == HttpStatus.OK) {
	                Map<String, Object> body = response.getBody();
	                if (body != null && body.containsKey("items")) {
	                    for (Map<String, Object> item : (List<Map<String, Object>>) body.get("items")) {
	                        String status = (String) item.get("status");
	                        if ("ACTIVE".equals(status)) {
	                            return true;
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return false;
	    }
}
