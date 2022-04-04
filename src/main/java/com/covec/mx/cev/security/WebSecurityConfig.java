package com.covec.mx.cev.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/reset_password","/forgot_password","/login", "/css/**", "/js/**", "/image/**", "/imagenes/**")
                .permitAll()
                .antMatchers("/colonias/**").hasAnyAuthority("ROL_ENLACE", "ROL_ADMIN")
                .antMatchers("/comites/**").hasAnyAuthority("ROL_ENLACE", "ROL_ADMIN")
                .antMatchers("/categorias/**").hasAnyAuthority("ROL_ENLACE", "ROL_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard")
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }


    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
				.authoritiesByUsernameQuery("SELECT u.username, r.authority FROM authorities AS ur "
						+ "INNER JOIN users AS u ON u.id_usuario = ur.id_usuario "
						+ "INNER JOIN roles AS r ON r.id_rol = ur.id_rol WHERE u.username = ?");
	}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
