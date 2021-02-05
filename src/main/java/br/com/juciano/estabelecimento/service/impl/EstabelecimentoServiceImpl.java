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

import br.com.juciano.estabelecimento.model.Estabelecimento;
import br.com.juciano.estabelecimento.repository.EstabelecimentoRepository;
import br.com.juciano.estabelecimento.service.EstabelecimentoService;
import br.com.juciano.estabelecimento.util.Indirection;

@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Override
	public Page<Estabelecimento> findAll(Pageable pageable) {
		return this.estabelecimentoRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Estabelecimento save(Estabelecimento estabelecimento) {
		return this.findById(this.estabelecimentoRepository.save(estabelecimento).getEstabelecimentoId()).get();
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		this.estabelecimentoRepository.deleteById(id);
	}

	@Override
	public Optional<Estabelecimento> findById(Long id) {
		return this.estabelecimentoRepository.findById(id);
	}
	
	@Transactional
	@Override
	public void deleteByIds(List<Long> ids) {
		this.estabelecimentoRepository.deleteByIds(ids);
	}

	@Transactional
	@Override
	public Optional<Estabelecimento> update(Estabelecimento estabelecimento) {
		Indirection<Optional<Estabelecimento>> estabelecimentoInd = new Indirection<>(Optional.ofNullable(null));

		this.findById(estabelecimento.getEstabelecimentoId())
			.ifPresent(_unused -> 
				estabelecimentoInd.setIndirection(
					Optional.ofNullable(this.estabelecimentoRepository.save(estabelecimento))
				)
			);
		return estabelecimentoInd.getIndirection();
	}

	@Override
	public List<Estabelecimento> findAll() {
		return StreamSupport.stream(this.estabelecimentoRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

}
