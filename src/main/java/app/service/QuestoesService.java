package app.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Questoes;
import app.entity.SubMateria;
import app.repository.QuestoesRepository;

@Service
public class QuestoesService {

	@Autowired
	private QuestoesRepository questoesRepository;

	public String save (Questoes questoes) {

		if(questoes.getAlternativas() != null) {
			for(int i=0; i<questoes.getAlternativas().size(); i++) {
				questoes.getAlternativas().get(i).setQuestoes(questoes);
				if(questoes.getAlternativas().get(i).isCorreta()) {
					questoes.setAlternativaCorreta(questoes.getAlternativas().get(i));
				}
			}
		}

		this.questoesRepository.save(questoes);

		return "Questão salva com sucesso";
	}


	public Questoes findById(Long id) {
		Optional<Questoes> questoes = questoesRepository.findById(id);
		return questoes.get();
	}

	public String delete (Long id) {
		this.questoesRepository.deleteById(id);
		return "Questão deletada com sucesso";
	}

	public List<Questoes> findAll (){
		List<Questoes> lista = this.questoesRepository.findAll();
		return lista;
	}

	public String update (Questoes questoes, Long id) {
		questoes.setId(id);

		if(questoes.getAlternativas() != null) {
			for(int i=0; i<questoes.getAlternativas().size(); i++) {
				questoes.getAlternativas().get(i).setQuestoes(questoes);
				if(questoes.getAlternativas().get(i).isCorreta()) {
					questoes.setAlternativaCorreta(questoes.getAlternativas().get(i));
				}
			}
		}


		this.questoesRepository.save(questoes);
		return "Questão foi atualizada com sucesso";
	}

	/*
	public String avaliar(Long idQuestao, int avaliacao) {

		if(avaliacao == 1)
			this.questoesRepository.avaliarMuitoFacil(idQuestao);
		else if(avaliacao == 2)
			this.questoesRepository.avaliarFacil(idQuestao);
		else if(avaliacao == 3)
			this.questoesRepository.avaliarMedio(idQuestao);
		else if(avaliacao == 4)
			this.questoesRepository.avaliarDificil(idQuestao);
		else
			this.questoesRepository.avaliarMuitoDificil(idQuestao);
		return "Alvalicao realizada com scuesso!";
	}

	 */
	public Questoes findNextQuestionBySubmateria(long idSubmateria, long idQuestaoEmTela) {
		SubMateria submateria = new SubMateria();
		submateria.setId(idSubmateria);



		//L[OGICAAAA DA TRETA

		if(idQuestaoEmTela == 0) {
			List<Questoes> questoes = questoesRepository.findNextQuestionBySubmateria(submateria);

			Random random = new Random();
			int randomIndex = random.nextInt(questoes.size()); 
			return questoes.get(randomIndex);
		}else{
			List<Questoes> questoes = questoesRepository.findNextQuestionBySubmateriaB(submateria, idQuestaoEmTela);

			Random random = new Random();
			int randomIndex = random.nextInt(questoes.size()); 
			return questoes.get(randomIndex);
		}

	}

}