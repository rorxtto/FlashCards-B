package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

}
