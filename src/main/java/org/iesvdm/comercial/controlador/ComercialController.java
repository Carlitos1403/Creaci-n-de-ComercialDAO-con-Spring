package org.iesvdm.comercial.controlador;

import java.net.URI;
import java.util.List;

import org.iesvdm.comercial.modelo.Comercial;
import org.iesvdm.comercial.servicio.ComercialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/comerciales")
public class ComercialController {
	private final ComercialService comercialService;

	public ComercialController(ComercialService comercialService) {
		this.comercialService = comercialService;
	}

	@GetMapping
	public List<Comercial> getAll() {
		return comercialService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Comercial> find(@PathVariable int id) {
		return comercialService.find(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Comercial> create(@RequestBody Comercial comercial) {
		comercialService.create(comercial);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(comercial.getId()).toUri();
		return ResponseEntity.created(location).body(comercial);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Comercial comercial) {
		if (comercialService.find(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		comercial.setId(id);
		comercialService.update(comercial);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		if (comercialService.find(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		comercialService.delete(id);
		return ResponseEntity.noContent().build();
	}
}