package app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<String> logar(@RequestBody Login login) {
	    try {
	        return ResponseEntity.ok(loginService.logar(login));
	    } catch (AuthenticationException ex) {
	        return new ResponseEntity<>("Usuário ou senha incorretos", HttpStatus.UNAUTHORIZED);
	    } catch (RuntimeException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Erro interno", HttpStatus.BAD_REQUEST);
	    }
	}
}
