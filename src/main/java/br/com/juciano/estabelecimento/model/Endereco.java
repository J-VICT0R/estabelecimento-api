package br.com.juciano.estabelecimento.model;

import javax.persistence.Embeddable;

import org.modelmapper.ModelMapper;

import br.com.juciano.estabelecimento.model.dto.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {
	private String rua;
	private String numCasa;
	private String bairro;
	private String cidade;
	private String estado;
	
	public EnderecoDTO convertEntityToDTO() {
		return new ModelMapper().map(this, EnderecoDTO.class);
	}
}
