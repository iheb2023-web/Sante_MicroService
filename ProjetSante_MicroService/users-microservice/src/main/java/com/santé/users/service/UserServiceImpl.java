package com.santé.users.service;

import com.santé.users.service.exceptions.EmailAlreadyExistsException;
import com.santé.users.service.exceptions.ExpiredTokenException;
import com.santé.users.service.exceptions.InvalidTokenException;
import com.santé.users.service.register.RegistrationRequest;
import com.santé.users.service.register.VerificationToken;
import com.santé.users.service.register.VerificationTokenRepository;
import com.santé.users.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.santé.users.entities.Role;
import com.santé.users.entities.User;
import com.santé.users.repos.RoleRepository;
import com.santé.users.repos.UserRepository;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserRepository userRep;

    @Autowired
    RoleRepository roleRep;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepo;

    @Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);

        usr.getRoles().add(r);
        return usr;
    }


    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optionalUser =  userRep.findByEmail(request.getEmail());
        if(optionalUser.isPresent())
            throw  new EmailAlreadyExistsException("Email deja existant!");
        User newuser = new User();
        newuser.setUsername(request.getUsername());
        newuser.setEmail(request.getEmail());

        newuser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newuser.setEnabled(false);
        userRep.save(newuser);

        Role r = roleRep.findByRole("USER");
        List<Role>  roles = new ArrayList<>();
        roles.add(r);
        newuser.setRoles(roles);

        //génére le code secret
        String code = this.generateCode();

        VerificationToken token = new VerificationToken(code, newuser);
        verificationTokenRepo.save(token);

        //envoyer le code par email à l'utilisateur
        sendEmailUser(newuser,token.getToken());

        return userRep.save(newuser);

    }

    private String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }
    /*@Override
    public void sendEmailUser(User u, String code) {
        String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" + " Votre code de validation est "+"<h1>"+code+"</h1>";
        emailSender.sendEmail(u.getEmail(), emailBody);
    }*/

    @Override
    public void sendEmailUser(User u, String code) {
        //String emailBody = "Bonjour " + u.getUsername() + " Votre code de validation est "+ code ;
        String emailBody = "Bonjour " + u.getUsername() + "\n\n" +
                "******************************************\n" +
                "Votre code de validation est : " + code + "\n" +
                "******************************************";

        String subject = "Code de validation de votre compte";
        emailSender.sendEmail(u.getEmail(), subject, emailBody);
    }

    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepo.findByToken(code);
        if(token == null){
            throw new InvalidTokenException("Invalid Token");
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepo.delete(token);
            throw new ExpiredTokenException("expired Token");
        }
        user.setEnabled(true);
        userRep.save(user);
        return user;
    }


}