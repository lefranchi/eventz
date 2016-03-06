package br.com.ablebit.eventz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ablebit.eventz.domain.CurrentUser;
import br.com.ablebit.eventz.domain.User;
import br.com.ablebit.eventz.service.UserService;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public CurrentUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		final User user = userService.getUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário %s não encontrado", email)));
		return new CurrentUser(user);
	}
}
