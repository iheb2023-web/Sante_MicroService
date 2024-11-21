package com.santé.users;

import com.santé.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class UsersMicroserviceApplication {

	@Autowired
	UserService userService;

	@Autowired
	private JavaMailSender javaMailSender;


	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);
	}
/*
	@PostConstruct
	void init_users() {
		//ajouter les rôles
		userService.addRole(new Role(null,"ADMIN"));
		userService.addRole(new Role(null,"USER"));

		//ajouter les users
		userService.saveUser(new User(null,"admin","123",true,"admin@gmail.com",null));
		userService.saveUser(new User(null,"iheb","123",true,"iheb@gmail.com",null));

		//ajouter les rôles aux users
		userService.addRoleToUser("admin", "ADMIN");
		userService.addRoleToUser("admin", "USER");

		userService.addRoleToUser("iheb", "USER");
	}*/


	/*
	@PostConstruct
	public void sendEmailWithBody() {
		// Envoi d'un email de test avec le service JavaMailSender
		sendEmail("deruich.iheb@gmail.com", "Test Email Subject", "Ceci est un test de l'envoi d'email avec un body dans Spring Boot.");
	}

	public void sendEmail(String recipient, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("deruich.iheb@gmail.com"); // Adresse email de l'expéditeur
		message.setTo(recipient); // Destinataire
		message.setSubject(subject); // Sujet de l'email
		message.setText(body); // Corps de l'email

		try {
			javaMailSender.send(message);
			System.out.println("Email envoyé avec succès !");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
			e.printStackTrace();
		}
	}*/





}
