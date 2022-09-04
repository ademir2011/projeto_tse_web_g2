package com.residenciaTst.AtividadePratica;

import com.residenciaTst.AtividadePratica.enums.ERole;
import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.model.User;
import com.residenciaTst.AtividadePratica.repository.RoleRepository;
import com.residenciaTst.AtividadePratica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AtividadePraticaApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AtividadePraticaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		if(!roleRepository.findByName(String.valueOf(ERole.ROLE_USER)).isPresent() &&
				!roleRepository.findByName(String.valueOf(ERole.ROLE_ADMIN)).isPresent() &&
				!roleRepository.findByName(String.valueOf(ERole.ROLE_MODERATOR)).isPresent()
		){
			// Inserindo os dados
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

			Set<Role> roles = new HashSet<>();
			roles.add(roleUser);
			roles.add(roleAdmin);
			roles.add(roleMode);


			// Criando um usuário admin
			User user = User.builder()
					.username("admin")
					.email("admin@gmail.com")
					.password(encoder.encode("admin"))
					.roles(roles)
					.build();


			userRepository.save(user);

		}

	}
}
