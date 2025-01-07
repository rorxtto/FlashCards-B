package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.SubMateria;

public interface SubMateriaRepository extends JpaRepository<SubMateria, Long>{
    List<SubMateria> findByMateriaId(Long materiaId);
}
