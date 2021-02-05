package br.com.juciano.estabelecimento.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.juciano.estabelecimento.model.Profissional;
import br.com.juciano.estabelecimento.repository.ProfissionalRepository;
import br.com.juciano.estabelecimento.service.ProfissionalService;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {
	
	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Override
	public Page<Profissional> findAll(Pageable pageable) {
		return this.profissionalRepository.findAll(pageable);
	}

	@Override
	public List<Profissional> findAll() {
		return StreamSupport.stream(this.profissionalRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public Profissional save(Profissional profissional) {
		return this.profissionalRepository.save(profissional);
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		this.profissionalRepository.deleteById(id);
	}

	@Override
	public Optional<Profissional> findById(Long id) {
		return this.profissionalRepository.findById(id);
	}
	
	@Transactional
	@Override
	public void deleteByIds(List<Long> ids) {
		this.profissionalRepository.deleteByIds(ids);
	}

	@Transactional
	@Override
	public Optional<Profissional> update(Profissional profissional) {
		this.findById(profissional.getProfissionalId()).ifPresent(_unused -> this.profissionalRepository.save(profissional));
		return this.findById(profissional.getProfissionalId());
	}
	
}
