package com.project.TwitterClone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.TwitterClone.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmail(String email);
	
	@Query("SELECT DISTINCT u FROM User u WHERE u.fullName Like %:query% OR u.email LIKE %:query%")
	public List<User> searchUser(@Param("query")String query);
    
	
}
