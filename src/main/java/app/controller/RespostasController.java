//package app.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import app.entity.Alternativas;
//import app.service.RespostasService;
//
//@RestController
//@RequestMapping("/respostas")
//public class RespostasController {
//	
//	@Autowired
//	private RespostasService respostasService;
//	
//	@PostMapping("/save")
//	public ResponseEntity<String> save(@RequestBody Alternativas respostas){
//
//		try {
//
//			String mensagem =  this.respostasService.save(respostas);
//			return new ResponseEntity<String>(mensagem, HttpStatus.OK);
//
//		} catch (Exception e) {
//			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
//		}
//
//	}
//	
//	@GetMapping("/findById/{id}")
//	public ResponseEntity<Alternativas> findById (@PathVariable Long id){
//		try {
//
//			Alternativas respostas = this.respostasService.findById(id);
//			return new ResponseEntity<>(respostas, HttpStatus.OK);
//
//		} catch (Exception e) {
//
//			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//	}
//	
//	@GetMapping("/findAll")
//	public ResponseEntity<List<Alternativas>> findAll(){
//
//		try {
//
//			List<Alternativas> lista = this.respostasService.findAll();
//			return new ResponseEntity<> (lista, HttpStatus.OK);
//		} catch (Exception e) {
//	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//
//	}
//	
//	@DeleteMapping ("/delete/{id}")
//	public ResponseEntity<String> delete (@PathVariable Long id){
//
//		try {
//
//			String mensagem =  this.respostasService.delete (id);
//			return new ResponseEntity<String>(mensagem, HttpStatus.OK);
//
//		} catch (Exception e) {
//			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
//		}
//
//	}
//	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<String> update (@RequestBody Alternativas respostas, @PathVariable Long id) {
//
//		try {
//
//			String mensagem =  this.respostasService.update (respostas, id);
//			return new ResponseEntity<>(mensagem, HttpStatus.OK);
//
//		} catch (Exception e) {
//	        return new ResponseEntity<>("Erro ao atualizar a questao. Tente novamente.", HttpStatus.BAD_REQUEST);
//		}
//
//	}
//}
