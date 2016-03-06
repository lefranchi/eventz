package br.com.ablebit.eventz.service;

import java.util.Collection;
import java.util.Optional;

import br.com.ablebit.eventz.domain.User;

public interface UserService {

	Optional<User> getUserById(long id);

	Optional<User> getUserByEmail(String email);

	Collection<User> getAllUsers();

}
