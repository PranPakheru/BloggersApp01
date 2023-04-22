package com.myblog.blogapp;

import com.myblog.blogapp.entity.Role;
import com.myblog.blogapp.repository.RoleRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//use this url http://bloggerapp01-env.eba-mnrguivh.eu-north-1.elasticbeanstalk.com/
@SpringBootApplication
public class BlogappApplication implements CommandLineRunner {
    //implementing to commandlinerunner to give meta data.

	//creating bean for ModelMapper library because it is not a SpringBoot library, it ia a java library.
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
//@Bean will help us perform constructor based dependency injection.


	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("db_url", dotenv.get("db_url"));
        System.setProperty("db_username", dotenv.get("db_username"));
        System.setProperty("db_password", dotenv.get("db_password"));

        System.setProperty("JWT_secretKey", dotenv.get("JWT_secretKey"));
        System.setProperty("JWT_expirationTime", dotenv.get("JWT_expirationTime"));

//        System.setProperty("Server_Port", dotenv.get("Server_Port"));

		SpringApplication.run(BlogappApplication.class, args);

	}

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepo.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepo.save(userRole);
    }
}
