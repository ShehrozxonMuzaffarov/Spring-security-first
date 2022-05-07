package com.appsecurityfirst.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize annotatsiya ishlashi uchun kk
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()

                //ROLE BASED UCHUN
//                .withUser("director").password(passwordEncoder().encode("director")).roles("DIRECTOR")
//                .and()
//                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER")
//                .and()
//                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");

                //permission based authentication uchun(
                .withUser("director1").password(passwordEncoder().encode("director1")).roles("DIRECTOR").authorities("READ_ONE_PRODUCT","READ_ALL_PRODUCT","EDIT_PRODUCT","DELETE_PRODUCT","ADD_PRODUCT")
                .and()
                .withUser("director2").password(passwordEncoder().encode("director2")).roles("DIRECTOR").authorities("READ_ONE_PRODUCT","READ_ALL_PRODUCT","EDIT_PRODUCT","ADD_PRODUCT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // cross site request forgery bunda bazadagi malumotlarni add delete edit qilishga ruxsat beradi, default faqat get qilishu mumkin
                .authorizeRequests()

                //ROLE BASED UCHUN
//                .antMatchers(HttpMethod.GET,"/api/product/*").hasAnyRole("USER","DIRECTOR","MANAGER")  //role-based
//                .antMatchers(HttpMethod.GET,"/api/product").hasAnyRole("DIRECTOR","MANAGER")
//                .antMatchers("/api/product/**").hasRole("DIRECTOR")

                //permission based uchun
//                .antMatchers(HttpMethod.DELETE,"/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ONE_PRODUCT","READ_ALL_PRODUCT","EDIT_PRODUCT","ADD_PRODUCT")

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();  // basic authenticationda tekshir( alertda soraydi parolni), login parolni bilgan Crudni qila oladi basicda

    }


    // passwordni shifrlab tashlidi tushunib bolmas darajada
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




    /**
     * form-based authentication bu kirganda login parol soraydi toliq ekranda
     *
     * basic authenticationda  login parolni bilgan odam CRUD ni qila oladi basicda (dependency,configure(HttpSecurity http), password encoder)
     *
     * Role-based authenticationda (inMemoryAuthentication, antMatchers yoki @EnableGlobalMethodSecurity(prePostEnabled = true) in config with  @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')") in controller
     *
     *permission-based  authenticationda (inMemoryAuthenticationda .authorities, antMatcher yoki @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
     */
}
