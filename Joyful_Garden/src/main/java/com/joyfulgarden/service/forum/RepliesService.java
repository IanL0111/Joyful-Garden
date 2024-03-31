package com.joyfulgarden.service.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.forum.Posts;
import com.joyfulgarden.model.forum.Replies;
import com.joyfulgarden.model.forum.RepliesRepository;

@Service
@Transactional
public class RepliesService {

	@Autowired
	private RepliesRepository repRepos;
	
	@Autowired
	private PostsService postsService;
	
	// 新增
	public Replies insert(Replies replies) {
		return repRepos.save(replies);
	}
	
	//修改Post
	public Replies update(Replies replies) {
		return repRepos.save(replies);
	}
	
	// 刪除
	public void deleteById(Integer replyID) {
		repRepos.deleteById(replyID);
	}
	
	//找單一
	public Replies findById(Integer replyID) {
		Optional<Replies> optional = repRepos.findById(replyID);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ;
		}
	}
	
	//找全o
	public List<Replies> findAllReplies() {
		return repRepos.findAll();
	}
	//找全by postID
	public List<Replies> findAllRepliesByPostsID(Integer postID) {
		return repRepos.findByPostID(postID);
	}
	
	// 作者查全
	public List<Replies> findAllRepliesByAuthorNicknameOrderByReplyIDDesc(String authorNickname) {
		return repRepos.findAllRepliesByAuthorNicknameOrderByReplyIDDesc(authorNickname);
	}
}
