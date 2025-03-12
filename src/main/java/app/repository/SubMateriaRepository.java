package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.SubMateria;

public interface SubMateriaRepository extends JpaRepository<SubMateria, Long>{
    List<SubMateria> findByMateriaId(Long materiaId);
    
    @Query("SELECT s, COALESCE(COUNT(q.id), 0) " +
    	       "FROM SubMateria s " +
    	       "LEFT JOIN Questoes q ON s.id = q.submateria.id " +
    	       "GROUP BY s.id")
    	List<Object[]> findSubmateriasWithQuantidadeQuestoes();

}
