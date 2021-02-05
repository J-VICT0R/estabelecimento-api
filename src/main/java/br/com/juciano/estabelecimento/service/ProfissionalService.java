package br.com.juciano.estabelecimento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.juciano.estabelecimento.model.Profissional;

public interface ProfissionalService {
	Optional<Profissional> findById(Long id);
	Page<Profissional> findAll(Pageable pageable);
	List<Profissional> findAll();
	Profissional save(Profissional profissional);
	Optional<Profissional> update(Profissional profissional);
	void deleteById(Long id);
	void deleteByIds(List<Long> id);
}
