package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.userentity;

public interface IRepository extends JpaRepository<userentity, Integer> {
	Optional<userentity> findByEmail(String email);

	void findAllById(Integer empid);
}


