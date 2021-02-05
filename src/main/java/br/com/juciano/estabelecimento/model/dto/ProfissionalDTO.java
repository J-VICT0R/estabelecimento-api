package br.com.juciano.estabelecimento.model.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ProfissionalDTO extends RepresentationModel<ProfissionalDTO> {
    private Long profissionalId;

    @NotNull(message="nome não pode estar nulo")
	private String nome;

	@NotNull(message="endereco não pode estar nulo")
    private EnderecoDTO endereco;
	
	@NotNull(message="celular não pode estar nulo")
    private String celular;
	
	@NotNull(message="residencial não pode estar nulo")
    private String residencial;
	
	@NotNull(message="funcao não pode estar nulo")
    private String funcao;
	
	@NotNull(message="estabelecimentos não pode estar nulo")
	private Set<EstabelecimentoDTO> estabelecimentos;

	public Profissional convertDTOToEntity() {
		return new ModelMapper().map(this, Profissional.class);
	}
}
