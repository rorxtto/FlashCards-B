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

import app.entity.Materia;
import app.repository.MateriaRepository;
import app.service.MateriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/materia")
@CrossOrigin("*")
public class MateriaController {

	@Autowired
	private MateriaService materiaService;


	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Materia materia){

		try {

			String mensagem =  this.materiaService.save(materia);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Materia> findById (@PathVariable Long id){
		try {

			Materia materia = this.materiaService.findById(id);
			return new ResponseEntity<>(materia, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Materia>> findAll(){

		try {

			List<Materia> lista = this.materiaService.findAll();
			return new ResponseEntity<> (lista, HttpStatus.OK);
		} catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping ("/delete/{id}")
	public ResponseEntity<String> delete (@PathVariable Long id){

		try {

			String mensagem =  this.materiaService.delete (id);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update (@RequestBody Materia materia, @PathVariable Long id) {

		try {

			String mensagem =  this.materiaService.update (materia, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
	        return new ResponseEntity<>("Erro ao atualizar a matéria. Tente novamente.", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	





}
