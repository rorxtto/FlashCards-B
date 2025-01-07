package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Questoes;
import app.entity.SubMateria;
import jakarta.websocket.server.PathParam;

public interface QuestoesRepository extends JpaRepository<Questoes, Long>{
	
    List<Questoes> findBySubmateriaId(Long submateriaId);

	
	/*
	@Query("UPDATE Questoes q SET q.muitoFacil = (q.muitoFacil + 1) WHERE q.id = :idQuestao")
	public void avaliarMuitoFacil(long idQuestao);
	
	@Query("UPDATE Questoes q SET q.facil = (q.facil + 1) WHERE q.id = :idQuestao")
	public void avaliarFacil(long idQuestao);
	
	@Query("UPDATE Questoes q SET q.medio = (q.medio + 1) WHERE q.id = :idQuestao")
	public void avaliarMedio(long idQuestao);
	
	@Query("UPDATE Questoes q SET q.dificil = (q.dificil + 1) WHERE q.id = :idQuestao")
	public void avaliarDificil(long idQuestao);
	
	@Query("UPDATE Questoes q SET q.muitoDificil = (q.muitoDificil + 1) WHERE q.id = :idQuestao")
	public void avaliarMuitoDificil(long idQuestao);
	*/
	
	/*
	@Query("SELECT Questoes q WHERE q.submateria = :submateria AND q.muitoFacil > q.facil and q.muitoFacil > q.medio and q.muitoFacil > q.dificil and q.muitoFacil > q.muitoDificil and q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaMuitoFacil(SubMateria submateria, long idQuestaoEmTela);
	
	@Query("SELECT Questoes q WHERE q.submateria = :submateria AND q.facil > q.muitoFacil and q.facil > q.medio and q.facil > q.dificil and q.facil > q.muitoDificil and q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaFacil(SubMateria submateria, long idQuestaoEmTela);
	
	@Query("SELECT Questoes q WHERE q.submateria = :submateria AND q.medio > q.muitoFacil and q.medio > q.facil and q.medio > q.dificil and q.medio > q.muitoDificil and q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaMedio(SubMateria submateria, long idQuestaoEmTela);
	
	@Query("SELECT Questoes q WHERE q.submateria = :submateria AND q.dificil > q.muitoFacil and q.dificil > q.facil and q.dificil > q.medio and q.dificil > q.muitoDificil and q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaDificil(SubMateria submateria, long idQuestaoEmTela);
	
	@Query("SELECT Questoes q WHERE q.submateria = :submateria AND q.muitoDificil > q.muitoFacil and q.muitoDificil > q.facil and q.muitoDificil > q.medio and q.muitoDificil > q.dificil and q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaMuitoDificil(SubMateria submateria, long idQuestaoEmTela);
	
	*/
	@Query("SELECT q FROM Questoes q WHERE q.submateria = :submateria")
	public List<Questoes> findNextQuestionBySubmateria(@PathParam("submateria") SubMateria submateria);
	
	@Query("SELECT q FROM Questoes q WHERE q.submateria = :submateria AND q.id <> :idQuestaoEmTela")
	public List<Questoes> findNextQuestionBySubmateriaB(@PathParam("submateria") SubMateria submateria, @PathParam("idQuestaoEmTela") long idQuestaoEmTela);
	
	

}
