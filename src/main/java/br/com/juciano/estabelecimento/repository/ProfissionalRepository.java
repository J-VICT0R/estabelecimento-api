package br.com.juciano.estabelecimento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.juciano.estabelecimento.model.Profissional;

@Repository
public interface ProfissionalRepository extends PagingAndSortingRepository<Profissional, Long> {
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Profissional p WHERE p.id IN(?1)")
	void deleteByIds(List<Long> ids);
}
