package br.com.lefranchi.eventz.service;

import java.util.Collection;
import java.util.Optional;

import br.com.lefranchi.eventz.domain.User;

public interface UserService {

	Optional<User> getUserById(long id);

	Optional<User> getUserByEmail(String email);

	Collection<User> getAllUsers();

}
