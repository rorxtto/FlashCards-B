package app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.SubMateria;
import app.service.SubMateriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/submateria")
@CrossOrigin("*")
public class SubMateriaController {
	
	@Autowired
	private SubMateriaService subMateriaService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody SubMateria submateria){
		try {
			
			String mensagem = this.subMateriaService.save(submateria);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<SubMateria> findById (@PathVariable Long id){
		try {

			SubMateria submateria = this.subMateriaService.findById(id);
			return new ResponseEntity<>(submateria, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<SubMateria>> findAll(){

		try {

			List<SubMateria> lista = this.subMateriaService.findAll();
			return new ResponseEntity<> (lista, HttpStatus.OK);
		} catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping ("/delete/{id}")
	public ResponseEntity<String> delete (@PathVariable Long id){

		try {

			String mensagem =  this.subMateriaService.delete (id);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update (@Valid @RequestBody SubMateria subMateria, @PathVariable Long id) {

		try {

			String mensagem =  this.subMateriaService.update (subMateria, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
	        return new ResponseEntity<>("Erro ao atualizar a sub-materia. Tente novamente.", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/com-quantidade-questoes")
	public ResponseEntity<List<SubMateria>> getSubmateriasComQuantidadeQuestoes() {
	    return ResponseEntity.ok(subMateriaService.getSubmateriasComQuantidadeQuestoes());
	}
	
	

}
