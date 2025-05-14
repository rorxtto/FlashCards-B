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
    
    @Query(value = "SELECT s.*, COALESCE(q.quantidade, 0) as quantidade_questoes " +
           "FROM sub_materia s " +
           "LEFT JOIN (SELECT submateria_id, COUNT(*) as quantidade FROM questoes GROUP BY submateria_id) q " +
           "ON s.id = q.submateria_id", 
           nativeQuery = true)
    List<Object[]> findSubmateriasWithQuantidadeQuestoes();
    
    @Query(value = "SELECT s.*, COALESCE(q.quantidade, 0) as quantidade_questoes " +
           "FROM sub_materia s " +
           "LEFT JOIN (SELECT submateria_id, COUNT(*) as quantidade FROM questoes GROUP BY submateria_id) q " +
           "ON s.id = q.submateria_id " +
           "WHERE s.materia_id = ?1", 
           nativeQuery = true)
    List<Object[]> findSubmateriasWithQuantidadeQuestoesByMateriaId(Long materiaId);
}