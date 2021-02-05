package br.com.juciano.estabelecimento.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.juciano.estabelecimento.model.dto.ProfissionalDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissional")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "profissionalId")
public class Profissional implements Serializable {

	private static final long serialVersionUID = -1002282625802058416L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profissional_id")
    private Long profissionalId;
	private String nome;

	@Embedded
    private Endereco endereco;
    private String celular;
    private String residencial;
    private String funcao;

    @ManyToMany
    @JoinTable(name = "profissional_estabelecimento",
    joinColumns = { @JoinColumn(name = "profissional_id") },
    inverseJoinColumns = { @JoinColumn(name = "estabelecimento_id") })
    private Set<Estabelecimento> estabelecimentos = new HashSet<>();
    
	public ProfissionalDTO convertEntityToDTO() {
		return new ModelMapper().map(this, ProfissionalDTO.class);
	}
}
