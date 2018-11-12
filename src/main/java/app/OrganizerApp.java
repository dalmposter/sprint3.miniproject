package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.domain.Organizer;

@SpringBootApplication
public class OrganizerApp implements WebMvcConfigurer {
	/**
	 * An organizer object for everyone to use.
	 */
	public static Organizer organizer = new Organizer();

	public static void main(String[] args) {
		SpringApplication.run(OrganizerApp.class, args);
	}

}
