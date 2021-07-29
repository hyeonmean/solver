package com.solver.db.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.UserCalendar;

public interface UserCalendarRepository extends JpaRepository<UserCalendar, String>{

	@Transactional
	void deleteByUserId(String userId);

}
