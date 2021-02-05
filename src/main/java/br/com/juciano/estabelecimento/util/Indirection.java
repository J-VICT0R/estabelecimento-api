package br.com.juciano.estabelecimento.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Indirection<T> {
	private T indirection;
}
