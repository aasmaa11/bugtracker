package com.bugtracker.SpringBootRestApp.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bugtracker.SpringBootRestApp.model.RefreshToken;

public interface RefreshTokenRepository  extends CrudRepository<RefreshToken, String>{

	
	Optional<RefreshToken> findByToken(String token);
	
	RefreshToken findByUserAccountUsername(String UserAccountUsername);
	
	RefreshToken findByUserAccountId(Long UserAccountId);

}
