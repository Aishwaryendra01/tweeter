package com.project.TwitterClone.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName; 
	private String location; 
	private String website; 
	private String birthDate; 
	private String email; 
	private String password; 
	private String mobile; 
	private String image; 
	private String backgroundmage; 
	private String bio; 
	private boolean req_user; 
	private boolean login_with_google; 
	
	@JsonIgnore
	@OneToMany(mappedBy ="user", cascade=CascadeType.ALL)
	private List<Twit> twit =new ArrayList<>();
	
	// mappedBy ="user" means--> it indicates that the "user" field in the 
	//related entity is responsible for mapping the relationship.
	
	
	@OneToMany(mappedBy ="user", cascade=CascadeType.ALL)
	private List<like> likes =new ArrayList<>();
	//cascadeType.All means---> if we delete any user the all the likes 
	//corresponding to that user will be deleted
	
	@Embedded
	private Varification verification;
	
	@ManyToMany
	private List<User> followers  =new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany
	private List<User> followings =new ArrayList<>();
	
	
	
	
	

}
