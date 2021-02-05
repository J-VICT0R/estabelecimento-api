package br.com.juciano.estabelecimento.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.juciano.estabelecimento.model.Estabelecimento;
import br.com.juciano.estabelecimento.model.dto.EstabelecimentoDTO;
import br.com.juciano.estabelecimento.model.dto.Response;
import br.com.juciano.estabelecimento.service.EstabelecimentoService;
import br.com.juciano.estabelecimento.util.Indirection;

@RestController
@RequestMapping("/estabelecimento-api/v1/estabelecimentos")
public class EstabelecimentoController {
	EstabelecimentoService estabelecimentoService;

	@Autowired
	public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
	  this.estabelecimentoService = estabelecimentoService;
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<Response<Page<EstabelecimentoDTO>>> findAllPageable(
		@PageableDefault(page = 1, size = Integer.MAX_VALUE, sort = {"nome"}) Pageable pageable
	) {
		Response<Page<EstabelecimentoDTO>> response = new Response<>();

		Page<EstabelecimentoDTO> estabelecimentos = this.estabelecimentoService.findAll(pageable).map(this::hateoasResponse);

		response.setData(estabelecimentos);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Response<EstabelecimentoDTO>> save(
		@Valid @RequestBody EstabelecimentoDTO newEstabelecimentoDTO,
		BindingResult validationResult 
	) {
		Response<EstabelecimentoDTO> response = new Response<>();

		if (validationResult.hasErrors()) {
			validationResult.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Estabelecimento createdEstabelecimento = this.estabelecimentoService.save(newEstabelecimentoDTO.convertDTOToEntity());

		response.setData(hateoasResponse(createdEstabelecimento));
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EstabelecimentoDTO>> update(
		@PathVariable("id") Long id,
		@Valid @RequestBody EstabelecimentoDTO updateEstabelecimentoDTO,
		BindingResult validationResult 
	) {
		updateEstabelecimentoDTO.setEstabelecimentoId(id);
		Response<EstabelecimentoDTO> response = new Response<>();

		if (validationResult.hasErrors()) {
			validationResult.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Indirection<Estabelecimento> updatedEstabelecimento = new Indirection<>(null);
		Indirection<HttpStatus> status = new Indirection<>(HttpStatus.NOT_FOUND);
		
		this.estabelecimentoService.update(updateEstabelecimentoDTO.convertDTOToEntity())
			.ifPresent(p -> {
				updatedEstabelecimento.setIndirection(p);
				status.setIndirection(HttpStatus.OK);
			});

		response.setData(hateoasResponse(updatedEstabelecimento.getIndirection()));
		
		return new ResponseEntity<>(response, status.getIndirection());
	}
	
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<EstabelecimentoDTO>> findById(@PathVariable("id") Long id) {
		Response<EstabelecimentoDTO> response = new Response<>();
		
		 Indirection<HttpStatus> status = new Indirection<HttpStatus>(HttpStatus.NOT_FOUND);

		this.estabelecimentoService.findById(id).ifPresent(p -> response.setData(hateoasResponse(p)));
		
		return new ResponseEntity<>(response, status.getIndirection());
	}
	
	@CrossOrigin
	@DeleteMapping
	public ResponseEntity<Response<String>> deleteAll(
		@RequestParam(value = "id") List<Long> ids
	) {
		Response<String> response = new Response<>();
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		if (this.estabelecimentoService.findAll().stream().map(Estabelecimento::getEstabelecimentoId).collect(Collectors.toList()).containsAll(ids)) {
			status = HttpStatus.NO_CONTENT;
			this.estabelecimentoService.deleteByIds(ids);
		}

		return new ResponseEntity<>(response, status);
	}

	@CrossOrigin
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> deleteById(@PathVariable("id") Long id) {
		Response<String> response = new Response<>();

		Indirection<HttpStatus> status = new Indirection<>(HttpStatus.NOT_FOUND);

		this.estabelecimentoService.findById(id).ifPresent(p -> {
			status.setIndirection(HttpStatus.NO_CONTENT);
			this.estabelecimentoService.deleteById(id);
		});

		return new ResponseEntity<>(response, status.getIndirection());
	}

	private EstabelecimentoDTO hateoasResponse(Estabelecimento estabelecimento) {
		Link selfLink = WebMvcLinkBuilder.linkTo(EstabelecimentoController.class).slash(estabelecimento.getEstabelecimentoId()).withSelfRel();
		return estabelecimento.convertEntityToDTO().add(selfLink);
	}
}
