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

    private static final String STRIPE_API_URL = "https://api.stripe.com/v1";
    private static final String SECRET_KEY = "";

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

        // Se não for admin, verifica assinatura no Stripe
        if (!user.getRole().contains("ADMIN") && !isAssinaturaAtiva(user.getUsername())) {
            throw new RuntimeException("Assinatura inativa ou inexistente");
        }

        return jwtService.generateToken(user);
    }

    private boolean isAssinaturaAtiva(String email) {
        RestTemplate restTemplate = new RestTemplate();

        // 1) Buscar customer_id pelo email
        String customerUrl = STRIPE_API_URL + "/customers?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(SECRET_KEY, ""); // Secret key como username, senha vazia

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> customerResponse = restTemplate.exchange(
                customerUrl,
                HttpMethod.GET,
                entity,
                Map.class
        );

        List<Map<String, Object>> customers = (List<Map<String, Object>>) customerResponse.getBody().get("data");
        if (customers == null || customers.isEmpty()) {
            return false; // Nenhum cliente encontrado
        }

        String customerId = (String) customers.get(0).get("id");

        // 2) Buscar assinatura pelo customer_id
        String subscriptionUrl = STRIPE_API_URL + "/subscriptions?customer=" + customerId;

        ResponseEntity<Map> subscriptionResponse = restTemplate.exchange(
                subscriptionUrl,
                HttpMethod.GET,
                entity,
                Map.class
        );

        List<Map<String, Object>> subscriptions = (List<Map<String, Object>>) subscriptionResponse.getBody().get("data");
        if (subscriptions == null || subscriptions.isEmpty()) {
            return false; // Nenhuma assinatura encontrada
        }

        String status = (String) subscriptions.get(0).get("status");
        return "active".equalsIgnoreCase(status);
    }
}
