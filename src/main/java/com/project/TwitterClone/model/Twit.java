package com.project.TwitterClone.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Twit {

	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id;
	private String content;
	private String image;
	private String video;
	
	@OneToMany(mappedBy = "twit",cascade = CascadeType.ALL)
	private List<Twit> likes=new ArrayList<>();
	
	
	@OneToMany
	private List<Twit> replyTwits=new ArrayList<>();
	
	@ManyToOne
	private Twit replyFor;
	
	private boolean isReply;
	private boolean isTwit;
	
	
	
}
