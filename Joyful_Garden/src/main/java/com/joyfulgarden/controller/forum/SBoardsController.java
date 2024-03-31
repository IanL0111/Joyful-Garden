package com.joyfulgarden.controller.forum;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joyfulgarden.model.forum.Sboards;
import com.joyfulgarden.service.forum.SboardsService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class SBoardsController {
	
	@Autowired
	private SboardsService sboardsService;
	
	// 找全
	@GetMapping("/forum/sboards/all")
	public List<Sboards> doAllSBoards() {
		return sboardsService.findAllSBoards();
	}
	
	// 找單一
	@GetMapping("/forum/sboards/{sboardID}")
	public Sboards doPostsByID(@PathVariable(name = "sboardID")Integer sboardID) {
		return sboardsService.findById(sboardID);
	}
	
	// 新增
	@SuppressWarnings("deprecation")
	@PostMapping("/forum/sboards")
	public Sboards doInsertSBoards(/*@PathVariable(name ="mboardID") Integer mboardID,*/ @RequestBody Sboards sboards) {
		Sboards nsBoards = new Sboards();
		nsBoards.setSboardTitle(sboards.getSboardTitle());
		nsBoards.setSbulletin(sboards.getSbulletin());
		//nsBoards.setMboardID(mboardID);
		nsBoards.setMboardID(sboards.getMboardID());
		nsBoards.setCreateTime(LocalDateTime.now());
		nsBoards.setDeleted(false);
		sboardsService.insert(nsBoards);
		return nsBoards;
	}
	
	// 修改
	@PutMapping("/forum/posts/update/{postID}")
	public Sboards doUpdatePosts(@PathVariable(name = "") Integer mboardID, @RequestBody Sboards sboards) {
		Sboards upost = sboardsService.findById(mboardID);
		upost.setSboardTitle(sboards.getSboardTitle());
		upost.setSbulletin(sboards.getSbulletin());
		upost.setMboardID(mboardID);
		//upost.setCreateTime(sBoards.getLikesCount());
		//npost.setDeleted(false);
		sboardsService.update(upost);
		return upost;		
	}

}
