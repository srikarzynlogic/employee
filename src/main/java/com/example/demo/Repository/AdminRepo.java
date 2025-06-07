package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.AdminEntity;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity, Integer> {

    // Example custom method
    AdminEntity findByEmail(String email);
   
	List<AdminEntity> findByDeletedFalse();

	List<AdminEntity> findByActiveTrueAndDeletedFalse();

	AdminEntity findByEmailAndActiveTrueAndDeletedFalse(String email);

	AdminEntity findByEmailAndPasswordAndActiveTrueAndDeletedFalse(String email, String password);
	AdminEntity findByEmailAndPassword(String email,String password); 
	
}
