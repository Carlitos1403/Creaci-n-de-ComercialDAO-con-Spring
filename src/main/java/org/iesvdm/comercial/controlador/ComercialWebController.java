package org.iesvdm.comercial.controlador;

import org.iesvdm.comercial.modelo.Comercial;
import org.iesvdm.comercial.servicio.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ComercialWebController {
	private final ComercialService comercialService;

	public ComercialWebController(ComercialService comercialService) {
		this.comercialService = comercialService;
	}

	@GetMapping("/")
	public String inicio() {
		return "redirect:/comercial";
	}

	@GetMapping("/comercial")
	public String listar(Model model) {
		model.addAttribute("comerciales", comercialService.getAll());
		return "comerciales";
	}

	@GetMapping("/comercial/crear")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("comercial", new Comercial());
		return "crear-comercial";
	}

	@PostMapping("/comercial/crear")
	public String crear(@ModelAttribute Comercial comercial, RedirectAttributes redirectAttributes) {
		comercialService.create(comercial);
		redirectAttributes.addFlashAttribute("mensaje", "Comercial creado correctamente.");
		return "redirect:/comercial";
	}

	@GetMapping("/comercial/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable int id, Model model,
			RedirectAttributes redirectAttributes) {
		return comercialService.find(id)
				.map(comercial -> {
					model.addAttribute("comercial", comercial);
					return "editar-comercial";
				})
				.orElseGet(() -> {
					redirectAttributes.addFlashAttribute("error", "No se encontro el comercial solicitado.");
					return "redirect:/comercial";
				});
	}

	@PostMapping("/comercial/editar/{id}")
	public String editar(@PathVariable int id, @ModelAttribute Comercial comercial,
			RedirectAttributes redirectAttributes) {
		if (comercialService.find(id).isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "No se encontro el comercial solicitado.");
			return "redirect:/comercial";
		}
		comercial.setId(id);
		comercialService.update(comercial);
		redirectAttributes.addFlashAttribute("mensaje", "Comercial actualizado correctamente.");
		return "redirect:/comercial";
	}

	@PostMapping("/comercial/eliminar/{id}")
	public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
		if (comercialService.find(id).isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "No se encontro el comercial solicitado.");
			return "redirect:/comercial";
		}
		comercialService.delete(id);
		redirectAttributes.addFlashAttribute("mensaje", "Comercial eliminado correctamente.");
		return "redirect:/comercial";
	}
}