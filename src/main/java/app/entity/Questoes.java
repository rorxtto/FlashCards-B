package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Questoes {
	
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; 

	 	@NotBlank(message = "O campo 'enunciado' não pode estar vazio.")
	 	@Column(columnDefinition = "TEXT")
	    private String enunciado;
	 	
	 	@NotBlank(message = "O campo 'justificativa' não pode estar vazio.")
	 	@Column(columnDefinition = "TEXT")
	    private String justificativa;
	    
	    @ManyToOne
	    @NotNull(message = "A questão deve estar vinculada a uma submatéria.")
	    @JsonIgnoreProperties("questoes")
	    private SubMateria submateria;
	    
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questoes")
	    @JsonIgnoreProperties("questoes") 
	    @Size(min = 1, message = "A questão deve conter pelo menos uma alternativa.")
	    private List<Alternativas> alternativas;
	    
	    @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "alternativa_correta_id") 
	    @NotNull(message = "A questão deve ter uma alternativa correta.")
	    @JsonIgnoreProperties("questoes") 
	    private Alternativas alternativaCorreta;
	    
	    
	    
	    public SubMateria getSubmateria() {
			return submateria;
		}

		public void setSubmateria(SubMateria submateria) {
			this.submateria = submateria;
		}

		public List<Alternativas> getAlternativas() {
			return alternativas;
		}

		public void setAlternativas(List<Alternativas> alternativas) {
			this.alternativas = alternativas;
		}

		public Alternativas getAlternativaCorreta() {
			return alternativaCorreta;
		}

		public void setAlternativaCorreta(Alternativas alternativaCorreta) {
			this.alternativaCorreta = alternativaCorreta;
		}
/*
		public int getMuitoFacil() {
			return muitoFacil;
		}

		public void setMuitoFacil(int muitoFacil) {
			this.muitoFacil = muitoFacil;
		}

		public int getFacil() {
			return facil;
		}

		public void setFacil(int facil) {
			this.facil = facil;
		}

		public int getMedio() {
			return medio;
		}

		public void setMedio(int medio) {
			this.medio = medio;
		}

		public int getDificil() {
			return dificil;
		}

		public void setDificil(int dificil) {
			this.dificil = dificil;
		}

		public int getMuitoDificil() {
			return muitoDificil;
		}

		public void setMuitoDificil(int muitoDificil) {
			this.muitoDificil = muitoDificil;
		}
*/
		public String getEnunciado() {
			return enunciado;
		}

		public void setEnunciado(String enunciado) {
			this.enunciado = enunciado;
		}

		public String getJustificativa() {
			return justificativa;
		}

		public void setJustificativa(String justificativa) {
			this.justificativa = justificativa;
		}
		
		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
		
		
	    
	    
	    
	    
	  
}
