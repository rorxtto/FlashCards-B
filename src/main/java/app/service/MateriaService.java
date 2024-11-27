package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Materia;
import app.repository.MateriaRepository;

@Service
public class MateriaService {
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	public String save (Materia materia) {
		
		if(materia.getSubmateria() != null)
			for(int i=0; i<materia.getSubmateria().size(); i++) {
				materia.getSubmateria().get(i).setMateria(materia);
			}
		
		this.materiaRepository.save(materia);
		
		return "Materia salva com sucesso";
	}
	
	
	public Materia findById(Long id) {
		Optional<Materia> materia = materiaRepository.findById(id);
		return materia.get();
	}
	
	public String delete (Long id) {
		this.materiaRepository.deleteById(id);
		return "Materia deletada com sucesso";
	}
	
	public List<Materia> findAll (){
		List<Materia> lista = this.materiaRepository.findAll();
		return lista;
	}
	
	public String update (Materia materia, Long id) {
		materia.setId(id);
		this.materiaRepository.save(materia);
		return "Materia foi atualizada com sucesso";
	}
}
