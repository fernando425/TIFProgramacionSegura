package ar.edu.ps.tif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TifApplication {

	public static void main(String[] args) {
		SpringApplication.run(TifApplication.class, args);
		 System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}

}
	