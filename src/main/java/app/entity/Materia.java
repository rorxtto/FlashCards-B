package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Materia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @NotBlank(message = "O nome da matéria é obrigatório.")
	private String nome;
    
    @NotBlank(message = "A descrição da matéria é obrigatória.")
	private String descricao;
	
    @NotBlank(message = "A imagem em formato Base64 é obrigatória.	")
	@Column(columnDefinition = "LONGTEXT")
	private String base64;
	
	@OneToMany(mappedBy = "materia")
	@JsonIgnoreProperties("materia")
	private List<SubMateria> submateria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<SubMateria> getSubmateria() {
	    return submateria;
	}

	public void setSubmateria(List<SubMateria> submateria) {
	    this.submateria = submateria;
	}
	
	 public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

		public String getBase64() {
			return base64;
		}

		public void setBase64(String base64) {
			this.base64 = base64;
		}
	
}
