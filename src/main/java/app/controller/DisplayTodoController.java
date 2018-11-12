package app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.OrganizerApp;
import app.domain.Todo;

@Controller
@RequestMapping("/")
public class DisplayTodoController {

	@RequestMapping("/")
	public String start() {
		return "redirect:/list";
	}

	@RequestMapping("/list")
	public String listTodos(Model model) {
		List<Todo> todos = OrganizerApp.organizer.getTodos();
		if (todos.isEmpty()) {
			return "NoTodo";
		} else {
			model.addAttribute("todos", OrganizerApp.organizer.getTodos());
		}
		return "ListTodos";
	}

	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public String next(Model model) {
		Todo t = null;
		try {
			t = OrganizerApp.organizer.getHighestPrioTodo();
		} catch (Exception e) {
			return "NoTodo";
		}
		model.addAttribute("todo", t);
		return "NextTodo";
	}

}
