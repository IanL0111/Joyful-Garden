package com.joyfulgarden.service.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.forum.Sboards;
import com.joyfulgarden.model.forum.SboardsRepository;

@Service
@Transactional
public class SboardsService {

	@Autowired
	private SboardsRepository sbRepos;
	
	// 找全
	public List<Sboards> findAllSBoards() {
		return sbRepos.findAll();
	}
	
	// 新增
	public Sboards insert(Sboards sboards) {
		return sbRepos.save(sboards);
	}
	
	// 修改
	public Sboards update(Sboards sboards) {
		return sbRepos.save(sboards);
	}
	
	// 刪除
	public void deleteById(Integer sboardID) {
		sbRepos.deleteById(sboardID);
	}
	
	
	public Sboards findById(Integer sboardID) {
		Optional<Sboards> optional = sbRepos.findById(sboardID);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ;
		}
	}
	
	
}
