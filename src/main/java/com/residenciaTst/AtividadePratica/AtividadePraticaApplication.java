package com.residenciaTst.AtividadePratica;

import com.residenciaTst.AtividadePratica.enums.ERole;
import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtividadePraticaApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AtividadePraticaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		// Inserting the data in the mysql table.
		Role roleUser = Role.builder()
				.name(ERole.ROLE_USER)
				.build();
		Role roleAdmin = Role.builder()
				.name(ERole.ROLE_ADMIN)
				.build();
		Role roleMode = Role.builder()
				.name(ERole.ROLE_MODERATOR)
				.build();
		// ob.save() method
		roleRepository.save(roleUser);
		roleRepository.save(roleAdmin);
		roleRepository.save(roleMode);
	}
}
