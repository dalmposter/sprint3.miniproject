package app.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * An organizer contains and manages a list of Todos.
 *
 */
public class Organizer {
	public List<Todo> todos;

	public Organizer() {
		todos = new ArrayList<>();
	}

	/**
	 * retrieve all todos
	 * 
	 * @return list of todos
	 */
	public List<Todo> getTodos() {
		return todos;
	}

	/**
	 * adds a todo to the list
	 * 
	 * @param t
	 */
	public void addTodo(Todo t) {
		todos.add(t);
	}

	/**
	 * obtain a todo with the highest priority
	 * 
	 * @return
	 */
	public Todo getHighestPrioTodo() {
		Todo highest = todos.get(0);
		for (Todo t : todos) {
			if (t.getPriority() > highest.getPriority()) {
				highest = t;
			}
		}
		return highest;
	}

	/**
	 * Deletes a the todo for a given id.
	 * 
	 * @param id
	 */
	public void deleteTodo(int id) {
		todos.removeIf(t -> t.getId() == id);
	}
}