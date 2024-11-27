//package app.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import app.entity.Alternativas;
//import app.repository.RespostasRepository;
//
//@Service
//public class RespostasService {
//	
//	@Autowired
//	private RespostasRepository respostasRepository;
//	
//	public String save (Alternativas respostas) {
//		
//		this.respostasRepository.save(respostas);
//		
//		return "Resposta salva com sucesso";
//	}
//	
//	
//	public Alternativas findById(Long id) {
//		Optional<Alternativas> respostas = respostasRepository.findById(id);
//		return respostas.get();
//	}
//	
//	public String delete (Long id) {
//		this.respostasRepository.deleteById(id);
//		return "Resposta deletada com sucesso";
//	}
//	
//	public List<Alternativas> findAll (){
//		List<Alternativas> lista = this.respostasRepository.findAll();
//		return lista;
//	}
//	
//	public String update (Alternativas respostas, Long id) {
//		respostas.setId(id);
//		this.respostasRepository.save(respostas);
//		return "Resposta foi atualizada com sucesso";
//	}
//
//}
