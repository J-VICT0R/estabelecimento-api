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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.juciano.estabelecimento.model.dto.EstabelecimentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estabelecimento")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "estabelecimentoId")
public class Estabelecimento implements Serializable {

	private static final long serialVersionUID = -4165104751877996386L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estabelecimento_id")
    private Long estabelecimentoId;
	private String nome;

	@Embedded
    private Endereco endereco;
    private String residencial;
    private String celular;

    @ManyToMany(mappedBy = "estabelecimentos")
    private Set<Profissional> profissionais = new HashSet<>();
    
	public EstabelecimentoDTO convertEntityToDTO() {
		return new ModelMapper().map(this, EstabelecimentoDTO.class);
	}
}
