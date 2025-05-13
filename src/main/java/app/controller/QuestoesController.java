package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Questoes;
import app.service.QuestoesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/questoes")
@CrossOrigin("*")
public class QuestoesController {
	
	@Autowired
	private QuestoesService questoesService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Questoes questoes){

		try {

			String mensagem =  this.questoesService.save(questoes);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Questoes> findById (@PathVariable Long id){
		try {

			Questoes questoes = this.questoesService.findById(id);
			return new ResponseEntity<>(questoes, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Questoes>> findAll(){

		try {

			List<Questoes> lista = this.questoesService.findAll();
			return new ResponseEntity<> (lista, HttpStatus.OK);
		} catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/paginated")
	public ResponseEntity<Page<Questoes>> findAllPaginated(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size){

		try {
			Page<Questoes> questoesPage = this.questoesService.findAllPaginated(page, size);
			return new ResponseEntity<>(questoesPage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/filtered")
	public ResponseEntity<Page<Questoes>> findAllPaginatedAndFiltered(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String filtroEnunciado,
			@RequestParam(required = false) Long submateriaId){

		try {
			Page<Questoes> questoesPage = this.questoesService.findAllPaginatedAndFiltered(
				page, size, filtroEnunciado, submateriaId);
			return new ResponseEntity<>(questoesPage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping ("/delete/{id}")
	public ResponseEntity<String> delete (@PathVariable Long id){

		try {

			String mensagem =  this.questoesService.delete (id);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Deu algo de errado", HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update (@Valid @RequestBody Questoes questoes, @PathVariable Long id) {

		try {

			String mensagem =  this.questoesService.update (questoes, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
	        return new ResponseEntity<>("Erro ao atualizar a questao. Tente novamente.", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@GetMapping("/findNextQuestionBySubmateria/{idSubmateria}/{idQuestaoEmTela}")
	public ResponseEntity<Questoes> findNextQuestionBySubmateria (@PathVariable long idSubmateria, @PathVariable long idQuestaoEmTela){
		try {

			Questoes questoes = this.questoesService.findNextQuestionBySubmateria(idSubmateria, idQuestaoEmTela);
			return new ResponseEntity<>(questoes, HttpStatus.OK);

		} catch (Exception e) {
			
			e.printStackTrace();

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	

}