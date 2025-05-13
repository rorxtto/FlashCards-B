package app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import app.entity.SubMateria;

public interface SubMateriaRepository extends JpaRepository<SubMateria, Long>, JpaSpecificationExecutor<SubMateria> {
    List<SubMateria> findByMateriaId(Long materiaId);
    
    // Método para buscar submaterias com paginação
    Page<SubMateria> findAll(Pageable pageable);
    
    // Método para buscar submaterias com paginação e filtros
    Page<SubMateria> findAll(Specification<SubMateria> spec, Pageable pageable);
    
    // Método para buscar submaterias por materiaId com paginação
    Page<SubMateria> findByMateriaId(Long materiaId, Pageable pageable);
    
    @Query("SELECT s, COALESCE(COUNT(q.id), 0) " +
    	       "FROM SubMateria s " +
    	       "LEFT JOIN Questoes q ON s.id = q.submateria.id " +
    	       "GROUP BY s.id")
    	List<Object[]> findSubmateriasWithQuantidadeQuestoes();

}