package com.crissguinther;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {

	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

	static {
		LISTA_CONTATOS.add(new Contato("1", "Maria", "+55 34 00000 000"));
		LISTA_CONTATOS.add(new Contato("2", "João", "+55 34 00000 000"));
		LISTA_CONTATOS.add(new Contato("3", "Normandes", "+55 34 00000 000"));
		LISTA_CONTATOS.add(new Contato("4", "Thiago", "+55 34 00000 000"));
		LISTA_CONTATOS.add(new Contato("5", "Alexandre", "+55 34 00000 000"));
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/contatos")
	public ModelAndView contato() {
		ModelAndView modelAndView = new ModelAndView("listar");
		modelAndView.addObject("contatos", LISTA_CONTATOS);

		return modelAndView;
	}

	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView mav = new ModelAndView("formulario");
		mav.addObject("contato", new Contato());

		return mav;
	}

	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("formulario");

		mav.addObject(procurarContato(id));

		return mav;
	}

	@PutMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		int index = procurarIndiceContato(contato.getId());

		LISTA_CONTATOS.remove(index);
		LISTA_CONTATOS.add(index, contato);

		return "redirect:/contatos";
	}

	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();

		contato.setId(id);

		LISTA_CONTATOS.add(contato);

		return "redirect:/contatos";
	}

	private Contato procurarContato(String id) {
		Integer indice = procurarIndiceContato(id);

		if (indice != null) {
			return LISTA_CONTATOS.get(indice);
		}

		return null;
	}

	private Integer procurarIndiceContato(String id) {
		Integer index = null;
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			if (LISTA_CONTATOS.get(i).getId().equals(id)) {
				index = i;
				break;
			} else
				continue;
		}

		return index;
	}
}
