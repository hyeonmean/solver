package com.solver.db.entity.answer;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer extends BaseEntity{
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	private Date regDt;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="questionId")
	private Question question;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Evaluation> evaluation;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Comment> comment;
}
