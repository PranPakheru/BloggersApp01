package com.myblog.blogapp.config;


import com.myblog.blogapp.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration          //this will make this class a configuration class for roles and everything.
@EnableWebSecurity      //our own authentication requirements.
@EnableGlobalMethodSecurity(prePostEnabled = true)      //this gives permission for admin and user, without this @PreAuthorize does not work.
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //WebSecurityConfigurerAdapter this to get various methods.

    //there are two roles admin and user, we can decide who can do what.
    //I am allowing all access to admin and user can only use GET method.

    //for better flow and readability
    private static final String[] PUBLIC_URLS = {"/api/auth/**", "/api/v1/auth/**", "/api/v2/auth/**", "/api/v3/auth/**",
        "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //remember "hcd4ah"

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //adding one more 'a' above, at that particular spot.
        //that line permits the GET method to all and allows the rest to specific users.
        //going to PostController to put @PreAuthorize.
        //adding another ant matcher for sign in.
        //adding more antMatchers for Swagger

    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //this method returns an object which has encoder method to encode the password, which I will use in userDetailsService method.


    //using one more WebSecurityConfigurerAdapter method.
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
////        UserDetails pranav = User.builder().username("pranav").password("pranav123").roles("USER").build();
////        UserDetails admin = User.builder().username("admin").password("admin123").roles("ADMIN").build();
////        return new InMemoryUserDetailsManager(pranav, admin);
//        //this is new kind of object.
//
//        UserDetails pranav = User.builder().username("pranav").password(passwordEncoder().encode("pranav123")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(pranav, admin);
//        //this way will encode the password and springBoot will accept this, otherwise it thinks that developer has done
//        //some mistake and it creates its own encoded password which we can not have.
//    }
    //commenting above out because we are moving towards databased based authentication.

    @Autowired
    private CustomUserDetailsService userDetailsServices;
//    public SecurityConfig(CustomerDetailsService userDetailsServices){
//        this.userDetailsServices = userDetailsServices;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServices).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

//this was in aplication.properties file.
// Spring security.
//        spring.security.user.name=pranav
//        spring.security.user.password=pranav123
//        spring.security.user.roles=ADMIN
//        in next step i had to comment this out.
