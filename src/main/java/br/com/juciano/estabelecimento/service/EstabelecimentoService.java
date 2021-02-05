package br.com.juciano.estabelecimento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.juciano.estabelecimento.model.Estabelecimento;

public interface EstabelecimentoService {
	Optional<Estabelecimento> findById(Long id);
	Page<Estabelecimento> findAll(Pageable pageable);
	List<Estabelecimento> findAll();
	Estabelecimento save(Estabelecimento estabelecimento);
	Optional<Estabelecimento> update(Estabelecimento estabelecimento);
	void deleteById(Long id);
	void deleteByIds(List<Long> id);
}
