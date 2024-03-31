package com.joyfulgarden.service.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.forum.Comments;
import com.joyfulgarden.model.forum.CommentsRepository;

@Service
@Transactional
public class CommentsService {

	@Autowired
	private CommentsRepository comRepos;
	
	// 新增
	public Comments insert(Comments comments) {
		return comRepos.save(comments);
	}
	
	//修改Post
	public Comments update(Comments comments) {
		return comRepos.save(comments);
	}
	
	// 刪除
	public void deleteById(Integer replyID) {
		comRepos.deleteById(replyID);
	}
	
	//找單一
	public Comments findById(Integer replyID) {
		Optional<Comments> optional = comRepos.findById(replyID);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ;
		}
	}
	
	//找全o
	public List<Comments> findAllReplies() {
		return comRepos.findAll();
	}

	//找全by postID
	public List<Comments> findByTargetTypeAndID(String targetType, Integer postID, Integer replyID) {
		return comRepos.findByTargetTypeAndPostIDAndReplyID(targetType, postID, replyID);
	}
	
	//作者全
	public List<Comments> findAllCommentsByAuthorNicknameOrderByCommentIDDesc(String authorNickname) {
		return comRepos.findAllCommentsByAuthorNicknameOrderByCommentIDDesc(authorNickname);
	}
	
//	//找全by replyID
//	public List<Comments> findByTargetTypeAndReplyID(String targetType, Integer replyID) {
//		return comRepos.findByTargetTypeAndReplyID(targetType, replyID);
//	}
	
}
