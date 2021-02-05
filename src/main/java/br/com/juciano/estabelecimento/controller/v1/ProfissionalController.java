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

import br.com.juciano.estabelecimento.model.Profissional;
import br.com.juciano.estabelecimento.model.dto.ProfissionalDTO;
import br.com.juciano.estabelecimento.model.dto.Response;
import br.com.juciano.estabelecimento.service.ProfissionalService;
import br.com.juciano.estabelecimento.util.Indirection;

@RestController
@RequestMapping("/estabelecimento-api/v1/profissionais")
public class ProfissionalController {
	ProfissionalService profissionalService;

	@Autowired
	public ProfissionalController(ProfissionalService profissionalService) {
	  this.profissionalService = profissionalService;
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<Response<Page<ProfissionalDTO>>> findAll(
		@PageableDefault(page = 1, size = Integer.MAX_VALUE, sort = {"nome"}) Pageable pageable
	) {
		Response<Page<ProfissionalDTO>> response = new Response<>();

		Page<ProfissionalDTO> profissionais = this.profissionalService.findAll(pageable).map(this::hateoasResponse);

		response.setData(profissionais);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Response<ProfissionalDTO>> save(
		@Valid @RequestBody ProfissionalDTO newProfissionalDTO,
		BindingResult validationResult 
	) {
		Response<ProfissionalDTO> response = new Response<>();

		if (validationResult.hasErrors()) {
			validationResult.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Profissional createdProfissional = this.profissionalService.save(newProfissionalDTO.convertDTOToEntity());

		response.setData(hateoasResponse(createdProfissional));
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ProfissionalDTO>> update(
		@PathVariable("id") Long id,
		@Valid @RequestBody ProfissionalDTO updateProfissionalDTO,
		BindingResult validationResult 
	) {
		updateProfissionalDTO.setProfissionalId(id);
		Response<ProfissionalDTO> response = new Response<>();

		if (validationResult.hasErrors()) {
			validationResult.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Indirection<Profissional> updatedProfissional = new Indirection<>(null);
		Indirection<HttpStatus> status = new Indirection<>(HttpStatus.NOT_FOUND);
		
		this.profissionalService.update(updateProfissionalDTO.convertDTOToEntity())
			.ifPresent(p -> {
				updatedProfissional.setIndirection(p);
				status.setIndirection(HttpStatus.OK);
			});

		response.setData(hateoasResponse(updatedProfissional.getIndirection()));
		
		return new ResponseEntity<>(response, status.getIndirection());
	}
	
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ProfissionalDTO>> findById(@PathVariable("id") Long id) {
		Response<ProfissionalDTO> response = new Response<>();
		
		 Indirection<HttpStatus> status = new Indirection<HttpStatus>(HttpStatus.NOT_FOUND);

		this.profissionalService.findById(id).ifPresent(p -> response.setData(hateoasResponse(p)));
		
		return new ResponseEntity<>(response, status.getIndirection());
	}
	
	@CrossOrigin
	@DeleteMapping
	public ResponseEntity<Response<String>> deleteAll(
		@RequestParam(value = "id") List<Long> ids
	) {
		Response<String> response = new Response<>();
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		if (this.profissionalService.findAll().stream().map(Profissional::getProfissionalId).collect(Collectors.toList()).containsAll(ids)) {
			status = HttpStatus.NO_CONTENT;
			this.profissionalService.deleteByIds(ids);
		}

		return new ResponseEntity<>(response, status);
	}

	@CrossOrigin
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> deleteById(@PathVariable("id") Long id) {
		Response<String> response = new Response<>();

		Indirection<HttpStatus> status = new Indirection<>(HttpStatus.NOT_FOUND);

		this.profissionalService.findById(id).ifPresent(p -> {
			status.setIndirection(HttpStatus.NO_CONTENT);
			this.profissionalService.deleteById(id);
		});

		return new ResponseEntity<>(response, status.getIndirection());
	}

	private ProfissionalDTO hateoasResponse(Profissional profissional) {
		Link selfLink = WebMvcLinkBuilder.linkTo(ProfissionalController.class).slash(profissional.getProfissionalId()).withSelfRel();
		return profissional.convertEntityToDTO().add(selfLink);
	}
}
