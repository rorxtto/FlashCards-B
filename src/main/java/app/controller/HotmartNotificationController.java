package app.controller;

import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HotmartNotificationController {
		
	 @PostMapping("/hotmart-notifications")
	    public ResponseEntity<String> receiveNotification(@RequestBody java.util.Map<String, Object> payload) {
	        System.out.println("Recebido: " + payload);
	        return ResponseEntity.ok("Notificação recebida com sucesso!");
	    }
	 


	
}
