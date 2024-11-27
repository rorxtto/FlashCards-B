package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alternativas {
	
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; 
	 	
	    private String texto;
	    
	    private int ordem;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "questao_id")
		private Questoes questoes;
	    
	   

		@Transient
	    private boolean correta;
	    
	    
	    
	    
	    
	    
	 	public boolean isCorreta() {
			return correta;
		}


		public void setCorreta(boolean correta) {
			this.correta = correta;
		}


		public Questoes getQuestoes() {
			return questoes;
		}


		public void setQuestoes(Questoes questoes) {
			this.questoes = questoes;
		}


		public String getTexto() {
			return texto;
		}


		public void setTexto(String texto) {
			this.texto = texto;
		}
		
		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public int getOrdem() {
			return ordem;
		}


		public void setOrdem(int ordem) {
			this.ordem = ordem;
		}


}
