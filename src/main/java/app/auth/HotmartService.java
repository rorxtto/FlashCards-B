//package app.auth;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpMethod;
//
//@Service
//public class HotmartService {
//	
//	    @Value("${H4sIAAAAAAAAAB2Py5KqMABEv8gpDA9xqSJKlAgEB5INJQ8hQBAdBki%2B%2FnJn1ZvTp7oLAav0lLEbg%2FgunTVizo%2FTBXp2cAyn6ePvA9x%2BFQJKArYTxY4RCShoZDcOm1iuwj4%2F3VmMJ0bjanLq14ykC1Doa0iW2vUAqzwOXukf17aZ%2BOs74TpA%2Fje8x4rtYwzzhXvnp%2Bb%2FCO5yH9AwU12hTKgu1WsYMMrd4WbZDGFFIoAaZAUt4bSi3GdLt04Xd9oFz0fkL47lROSrLic6PSiSSNQujpqEZCCRIwlTNBrSmoDjkjajdcae%2FldT4W60Rc%2BrlxHuL%2FK4uU5t9HhK4%2FLbkk6stgPIuks6J95t5OoL6PZ797bn%2B%2FkUXT20DvTcqllw0ZPnp7jtRJyVpAhzMO%2BTsbCkq%2FJBNfVV8Urf5tYHFTQoTnbaK0%2Bwx%2Bbenebez7b7XwvjiqYq8JWre2%2FWrdLjBtwwFjhEYnWij%2BKsS7rafRtxAZ%2FHwYh7kqS9B%2FXQ%2FADjnnUzXj03mnpuSLvJ8xh62OVnlhxM%2FJmIUWrHRwzlD0Bs9LV1ft0gb3DQQKzaLLhl%2F6S6HXMM2hF0ys6msJKrvTIO5gkKsQwfSQYtNYi8yz5Lz%2FBoS7McBu7M8Vov2%2FGTNH65%2B2SDkQiPlP8AJbN5hF4CAAA%3D}")
//	    private String accessToken;
//
//	    @Value("${https://developers.hotmart.com/payments/api/v1}")
//	    private String hotmartApiUrl;
//
//	    // Método para verificar o status da assinatura
//	    public boolean verificarAssinaturaAtiva(String email, String productId) {
//	        String url = hotmartApiUrl + "/subscriptions?email=" + email + "&product_id=" + productId;
//	        
//	        // Configurando o cabeçalho com o token de acesso
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.set("Authorization", "Bearer " + accessToken);
//	        
//	        // Fazendo a requisição GET
//	        RestTemplate restTemplate = new RestTemplate();
//	        HttpEntity<String> entity = new HttpEntity<>(headers);
//	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//	        
//	        // Verifique o status de "ACTIVE"
//	        if (response.getStatusCode().is2xxSuccessful()) {
//	            String responseBody = response.getBody();
//	            // Analisar a resposta JSON para verificar o status da assinatura
//	            return responseBody.contains("\"status\":\"ACTIVE\"");
//	        } else {
//	            return false;
//	        }
//	    }
//
//}
