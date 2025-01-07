package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubMateria {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

	@NotBlank(message="O campo nome não pode ser vazio!")
    private String nome;
    
    @ManyToOne
    @JsonIgnoreProperties("submateria")
    @NotNull(message = "Uma SubMateria deve estar associada a uma Materia!")
    private Materia materia; 
    
    
    

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	 public void setId(Long id) {
	        this.id = id;
	    }
	 
	 public void setMateria(Materia materia) {
		    this.materia = materia;
		}

	public Long getId() {
		return id;
	}

	public Materia getMateria() {
		return materia;
	}

   

}
