package com.solver.db.entity.comment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReportComment extends BaseEntity{
	private String reason;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="reporterUserId")
	private User reporterUser;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="commentId")
	private Comment comment;
}
