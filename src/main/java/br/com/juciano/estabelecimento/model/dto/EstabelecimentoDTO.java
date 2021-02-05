package br.com.juciano.estabelecimento.model.dto;


import java.util.Set;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.juciano.estabelecimento.model.Endereco;
import br.com.juciano.estabelecimento.model.Estabelecimento;
import br.com.juciano.estabelecimento.model.Profissional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstabelecimentoDTO extends RepresentationModel<EstabelecimentoDTO> {

	private Long estabelecimentoId;
	
	@NotNull(message="nome não pode estar nulo")
	private String nome;

	@NotNull(message="endereco não pode estar nulo")
    private Endereco endereco;
	
	@NotNull(message="residencial não pode estar nulo")
    private String residencial;
	
	@NotNull(message="celular não pode estar nulo")
    private String celular;

	@NotNull(message="profissionais não pode estar nulo")
    private Set<Profissional> profissionais;

	
	public Estabelecimento convertDTOToEntity() {
		return new ModelMapper().map(this, Estabelecimento.class);
	}
}
