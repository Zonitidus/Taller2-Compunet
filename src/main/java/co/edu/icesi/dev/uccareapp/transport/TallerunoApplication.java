package co.edu.icesi.dev.uccareapp.transport;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.model.UserType;
import co.edu.icesi.dev.uccareapp.transport.repository.UserRepository;

@SpringBootApplication
@ComponentScan ({"co.edu.icesi.dev.uccareapp.transport"})
@EnableJpaRepositories ("co.edu.icesi.dev.uccareapp.transport.repository")
public class TallerunoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerunoApplication.class, args);
	}

	@Bean
	public CommandLineRunner dummy(UserRepository ur) {

		return (args) -> {
			UserApp u = new UserApp();
			u.setId(1);
			u.setType(UserType.admin);
			u.setUsername("Admin");
			u.setPassword("{noop}123456789");
		
			ur.save(u);
			
			UserApp u2 = new UserApp();
			u2.setId(2);
			u2.setType(UserType.operator);
			u2.setUsername("Operator");
			u2.setPassword("{noop}123456789");
		
			ur.save(u2);
			
		};

	}
}
