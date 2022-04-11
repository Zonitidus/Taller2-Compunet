package co.edu.icesi.dev.uccareapp.transport.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class UserApp {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Size(min=2, message = "2 character minimum")
	private String username;
	
	@NotBlank
	@Size(min=8, message = "8 character minimum")
	private String password;
	
	@NotNull
	private UserType type;
	

	
//	@OneToMany
//	private List<Appointment> appointments;
}
