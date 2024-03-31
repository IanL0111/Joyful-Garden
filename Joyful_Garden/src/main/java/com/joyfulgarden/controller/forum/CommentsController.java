package com.joyfulgarden.controller.forum;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyfulgarden.model.forum.Comments;
import com.joyfulgarden.model.forum.Replies;
import com.joyfulgarden.service.forum.CommentsService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CommentsController {

	@Autowired
	private CommentsService commentsService;
	
	// 刪除
	@DeleteMapping("/forum/postandreply/comments/{commentID}")
	public String doDeletetComments(@PathVariable(name = "commentID") Integer commentID) {;
		commentsService.deleteById(commentID);
		return "已刪除留言";
	}
	
	// 找單一
	@GetMapping("/forum/postandreply/comments/{commentID}")
	public Comments doCommentsByID(@PathVariable(name = "commentID")Integer  commentID) {
		return commentsService.findById(commentID);
	}
	
	// 找全by postID
	@GetMapping("/forum/postandreply/comments")
	public List<Comments> doAllCommentsByPost(String targetType, Integer postID, Integer replyID) {
		return commentsService.findByTargetTypeAndID(targetType, postID, replyID);
		//return commentsService.findByTargetTypeAndID("Posts", 1, null);
	}
	
	// 作者查全
	@GetMapping("/forum/postandreplyDesc/{authorNickname}")
	public List<Comments> doAllCommentsByAuthorNicknameDesc(@PathVariable(name = "authorNickname")String authorNickname) {
		return commentsService.findAllCommentsByAuthorNicknameOrderByCommentIDDesc(authorNickname);
	}
	
	// 新增
	@PostMapping("/forum/postandreply/comments")
	public Comments doInsertComments(@RequestBody Comments comments) {
		Comments ncomments = new Comments();
/*
		String typepString = String.valueOf(comments.getPostID()); // 將 typep 轉換為 String
		String typerString = String.valueOf(comments.getReplyID()); // 將 typer 轉換為 String
		String targetType = null;
		if (typepString.equals("null")) {
		    targetType = "Posts";
		} else if (typerString.equals("null")) {
		    targetType = "Replies";
		}
*/
		Integer p = comments.getPostID();
	    Integer r = comments.getReplyID();
	    String targetType = null;
	    boolean hasPostID = p != null;
	    boolean hasReplyID = r != null;

	    if (hasPostID) {
	        targetType = "Posts";
	    } else if (hasReplyID) {
	        targetType = "Replies";
	    }
	    
		ncomments.setTargetType(targetType);
		ncomments.setCommentContent(comments.getCommentContent());
		ncomments.setAuthorNickname(comments.getAuthorNickname());
		//ncomments.setTargetType(comments.getTargetType());
		ncomments.setPostID(comments.getPostID());
		ncomments.setReplyID(comments.getReplyID());
		ncomments.setLikesCount(comments.getLikesCount());
		ncomments.setCommentTime(LocalDateTime.now());
		ncomments.setDeleted(false);
		commentsService.insert(ncomments);
		System.out.println(ncomments);
		return ncomments;
		
	}
	
	
//	// 找全by replyID
//	@GetMapping("/forum/postreply/comments")
//	public List<Comments> doAllCommentsByReply(String targetType, Integer replyID) {
//		return commentsService.findByTargetTypeAndReplyID(targetType, replyID);
//	}
}
