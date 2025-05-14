package app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.entity.Questoes;
import app.entity.SubMateria;
import app.repository.QuestoesRepository;
import app.repository.SubMateriaRepository;

@Service
public class SubMateriaService {
	
	@Autowired
	private QuestoesRepository questoesRepository;
	
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
		


		 List<Questoes> listaQuestoes = questoesRepository.findBySubmateriaId(id);


		    for (Questoes questao : listaQuestoes) {
		        questoesRepository.deleteById(questao.getId());
		    }
		
		this.submateriaRepository.deleteById(id);
		return "Sub-Materia deletada com sucesso";
	}
	
	public List<SubMateria> findAll (){
		List<SubMateria> lista = this.submateriaRepository.findAll();
		return lista;
	}
	
	public Page<SubMateria> findAllPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return this.submateriaRepository.findAll(pageable);
	}
	
	public Page<SubMateria> findAllPaginatedByMateriaId(int page, int size, Long materiaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return this.submateriaRepository.findByMateriaId(materiaId, pageable);
	}
	
	public Page<SubMateria> findAllPaginatedAndFiltered(int page, int size, String filtroNome, Long materiaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		
		// Criar especificações para filtros
		Specification<SubMateria> spec = Specification.where(null);
		
		// Adicionar filtro por nome se fornecido
		if (StringUtils.hasText(filtroNome)) {
			spec = spec.and((root, query, criteriaBuilder) -> 
				criteriaBuilder.like(
					criteriaBuilder.lower(root.get("nome")), 
					"%" + filtroNome.toLowerCase() + "%"
				)
			);
		}
		
		// Adicionar filtro por materia se fornecido
		if (materiaId != null) {
			spec = spec.and((root, query, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("materia").get("id"), materiaId)
			);
		}
		
		return this.submateriaRepository.findAll(spec, pageable);
	}
	
	public String update (SubMateria subMateria, Long id) {
		subMateria.setId(id);
		this.submateriaRepository.save(subMateria);
		return "Materia foi atualizada com sucesso";
	}
	
	public List<SubMateria> getSubmateriasComQuantidadeQuestoes() {
	    List<Object[]> results = submateriaRepository.findSubmateriasWithQuantidadeQuestoes();
	    
	    return processarResultadosSubmateria(results);
	}
	
	public List<SubMateria> getSubmateriasComQuantidadeQuestoesByMateriaId(Long materiaId) {
	    List<Object[]> results = submateriaRepository.findSubmateriasWithQuantidadeQuestoesByMateriaId(materiaId);
	    
	    return processarResultadosSubmateria(results);
	}
	
	private List<SubMateria> processarResultadosSubmateria(List<Object[]> results) {
	    return results.stream().map(r -> {
	        Object[] row = (Object[]) r;
	        SubMateria submateria = new SubMateria();
	        
	        // Mapear os campos da consulta nativa para o objeto SubMateria
	        submateria.setId(((Number) row[0]).longValue());
	        submateria.setNome((String) row[1]);
	        // Outros campos conforme necessário
	        
	        // Definir a quantidade de questões
	        Long quantidade = row[row.length - 1] != null ? ((Number) row[row.length - 1]).longValue() : 0L;
	        submateria.setQuantidadeQuestoes(quantidade);
	        
	        return submateria;
	    }).collect(Collectors.toList());
	}
}