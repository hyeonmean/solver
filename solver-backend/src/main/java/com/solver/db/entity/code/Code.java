package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.Message;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Code {
	@Id
	private String code;
	
	private String codeName;
	private boolean useYn;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="commonCode")
	private CommonCode commonCode;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<User> user;
	
	@JsonManagedReference
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Category> category;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Notification> notification;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Message> message;
	
	@JsonIgnore
	@OneToMany(mappedBy="pointCode", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointLog> pointLog;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> question;
	
	@JsonIgnore
	@OneToMany(mappedBy="mainCategory", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> questionMainCategory;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Answer> answer;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Conference> conference;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ConferenceLog> conferenceLog;
	
	@JsonIgnore
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointCode> pointCode;
}
