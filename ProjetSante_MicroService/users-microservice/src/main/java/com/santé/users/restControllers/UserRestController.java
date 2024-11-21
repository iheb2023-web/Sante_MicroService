package com.santé.users.restControllers;

import com.santé.users.entities.User;
import com.santé.users.repos.UserRepository;
import com.santé.users.service.UserService;
import com.santé.users.service.register.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    UserRepository userRep;
    @Autowired
    UserService userService;
    @GetMapping("all")
    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    @PostMapping("/register")
    public  User register (@RequestBody RegistrationRequest request){
        return userService.registerUser(request);
    }

    @GetMapping("/verifyEmail/{token}")
    public User verifyEmail(@PathVariable("token") String token){
        return userService.validateToken(token);
    }

}
