package com.myblog.blogapp.controller;

import com.myblog.blogapp.entity.Role;
import com.myblog.blogapp.entity.User;
import com.myblog.blogapp.payload.JWTAuthResponse;
import com.myblog.blogapp.payload.LoginDto;
import com.myblog.blogapp.payload.SignupDto;
import com.myblog.blogapp.repository.RoleRepository;
import com.myblog.blogapp.repository.UserRepository;
import com.myblog.blogapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @PostMapping("/signIn")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return new ResponseEntity<>("user signed in successfully.", HttpStatus.OK);
//    }
    //it was changed to below.
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")              //http://localhost:8080/api/auth/signin
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signUp")         //http://localhost:8080/api/auth/signUp
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){
        if(userRepo.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        //got and converted everything.

        Role role = roleRepo.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));

        userRepo.save(user);

        return new ResponseEntity<>("user registered successfully.", HttpStatus.OK);

    }
}
