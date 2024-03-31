package com.joyfulgarden.model.forum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForummembersRepository extends JpaRepository<Forummembers, Integer> {

	Forummembers findByFnickname(String fnickname);

	
	Forummembers  findFnicknameByForummemberID(Integer forummemberID);

}
