package app.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app.domain.Todo;

public class TodoValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Todo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Todo t = (Todo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "task", "", "Field cannot be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "", "Field cannot be empty.");

		if (t.isImportant() && t.getDescription().isEmpty()) {
			errors.rejectValue("description", "", "Important Todos require a description.");
		} else if (!t.isImportant()) {
			if (t.getPriority() >= 100) {
				errors.rejectValue("priority", "", "If the Todo is not important the priority must be below 100.");
			} else if (t.getDescription().length() > 20) {
				errors.rejectValue("description", "", "Todos with low priority should have short descriptions.");
			}
		} else if (t.getDescription().equals(t.getTask())) {
			errors.rejectValue("task", "", "The task cannot equal the descriptions.");
		}
	}

}
