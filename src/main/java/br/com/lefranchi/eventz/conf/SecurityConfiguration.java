package br.com.lefranchi.eventz.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/login").permitAll().anyRequest().fullyAuthenticated().and()
				.formLogin().loginPage("/login").permitAll().and().logout().permitAll();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").antMatchers("/images/**").antMatchers("/css/**");
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("pwd").roles("USER");
	}

}
