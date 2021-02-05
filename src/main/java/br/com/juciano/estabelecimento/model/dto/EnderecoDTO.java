package br.com.juciano.estabelecimento.model.dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.juciano.estabelecimento.model.Endereco;
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
public class EnderecoDTO {

	@NotNull(message = "rua não pode estar nulo")
	private String rua;
	
	@NotNull(message = "numCasa não pode estar nulo")
	private String numCasa;
	
	@NotNull(message = "bairro não pode estar nulo")
	private String bairro;
	
	@NotNull(message = "cidade não pode estar nulo")
	private String cidade;
	
	@NotNull(message = "estado não pode estar nulo")
	private String estado;
	
	public Endereco convertDTOToEntity() {
		return new ModelMapper().map(this, Endereco.class);
	}
}
