package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.SubMateria;
import app.repository.SubMateriaRepository;

@Service
public class SubMateriaService {
	
	@Autowired
	private SubMateriaRepository submateriaRepository;
	
	public String save (SubMateria submateria) {
	
		this.submateriaRepository.save(submateria);
		
		return "Sub-Materia salva com sucesso";
	}
	
	
	public SubMateria findById(Long id) {
		Optional<SubMateria> submateria = submateriaRepository.findById(id);
		return submateria.get();
	}
	
	public String delete (Long id) {
		this.submateriaRepository.deleteById(id);
		return "Sub-Materia deletada com sucesso";
	}
	
	public List<SubMateria> findAll (){
		List<SubMateria> lista = this.submateriaRepository.findAll();
		return lista;
	}
	
	public String update (SubMateria subMateria, Long id) {
		subMateria.setId(id);
		this.submateriaRepository.save(subMateria);
		return "Materia foi atualizada com sucesso";
	}

}
