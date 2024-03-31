package com.joyfulgarden.model.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {

	List<Replies> findByPostID(Integer postID);
	
	// 作者查全
	public List<Replies> findAllRepliesByAuthorNicknameOrderByReplyIDDesc(String authorNickname);
	
}
