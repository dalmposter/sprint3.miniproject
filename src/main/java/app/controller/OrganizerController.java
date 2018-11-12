package app.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.OrganizerApp;
import app.domain.Todo;

@Controller
@RequestMapping("/")
public class OrganizerController {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new TodoValidator());
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("todo", new Todo());
		return "CreateTodo";
	}

	@RequestMapping(value = "create", params = "add", method = RequestMethod.POST)
	public String addNewTodo(@Valid @ModelAttribute("todo") Todo t, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "CreateTodo";
		} else {
			OrganizerApp.organizer.addTodo(t);
			return "redirect:/list";
		}
	}

	@RequestMapping(value = "create", params = "cancel", method = RequestMethod.POST)
	public String cancelNewTodo() {
		return "redirect:/list";
	}

	@RequestMapping(value = "delete", params = "id", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam("id") int id) {
		OrganizerApp.organizer.deleteTodo(id);
		return "redirect:/list";
	}
}
