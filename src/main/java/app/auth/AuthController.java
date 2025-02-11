package app.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class AuthController {
	
	  private final UserService userService;

	    @Autowired
	    public AuthController(UserService userService) {
	        this.userService = userService;
	    }

	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        userService.registerUser(user.getUsername(), user.getPassword());
	        return ResponseEntity.ok("Cadastro realizado com sucesso!");
	    }
	    
	    @PostMapping("/reset-password")
	    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
	        Map<String, String> response = new HashMap<>();
	        try {
	            userService.resetPassword(request.getUsername(), request.getNewPassword());
	            response.put("message", "Senha redefinida com sucesso!");
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            response.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        } catch (IllegalStateException e) {
	            response.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	        }
	    }


}
