package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Entity.userentity;
import com.example.demo.Repository.IRepository;

import jakarta.servlet.http.HttpSession;
@Service
public class userservice {
	@Autowired
	private IRepository repo;
	
	public userentity savedata(userentity user) {
		return repo.save(user);
	}
	public List<userentity>getAllData(){
    return repo.findAll();
}

    public userentity login(String email, String password, HttpSession session) {
        userentity user = repo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        session.setAttribute("SESSION_USER", user);
        return user;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
    public userentity getUserById(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with ID: " + id
            ));
    }

	
    
}
