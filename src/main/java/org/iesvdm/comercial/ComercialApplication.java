package org.iesvdm.comercial;

import org.iesvdm.comercial.modelo.Comercial;
import org.iesvdm.comercial.servicio.ComercialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

@SpringBootApplication
public class ComercialApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(ComercialApplication.class);

	private final ComercialService comercialService;

	public ComercialApplication(ComercialService comercialService) {
		this.comercialService = comercialService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ComercialApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Comercial comercial = new Comercial();
		comercial.setNombre("Prueba");
		comercial.setApellido1("JdbcTemplate");
		comercial.setApellido2("Spring");
		comercial.setComision(0.10f);

		comercialService.create(comercial);
		Assert.isTrue(comercial.getId() > 0, "El alta debe asignar un identificador");
		Assert.isTrue(comercialService.find(comercial.getId()).isPresent(), "El comercial creado debe existir");

		comercial.setNombre("Prueba actualizada");
		comercialService.update(comercial);
		Assert.isTrue(comercialService.find(comercial.getId())
				.map(found -> "Prueba actualizada".equals(found.getNombre()))
				.orElse(false), "La actualización debe persistirse");

		Assert.isTrue(comercialService.getAll().stream().anyMatch(found -> found.getId() == comercial.getId()),
				"El listado debe incluir el comercial creado");
		comercialService.delete(comercial.getId());
		Assert.isTrue(comercialService.find(comercial.getId()).isEmpty(), "El comercial eliminado no debe existir");

		log.info("Pruebas CRUD de ComercialDAO completadas correctamente.");
	}
}
