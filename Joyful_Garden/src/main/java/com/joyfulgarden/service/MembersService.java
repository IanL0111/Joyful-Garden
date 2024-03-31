package com.joyfulgarden.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.Members;
import com.joyfulgarden.model.MembersRepository;

@Service
@Transactional
public class MembersService {

	@Autowired
	private MembersRepository mRepos;

	public Members insert(Members m) {
		return mRepos.save(m);
	}

	public Members update(Members m) {
		return mRepos.save(m);
	}

	public void deleteById(Integer id) {
		mRepos.deleteById(id);
	}

	public Members findById(Integer id) {
		Optional<Members> op1 = mRepos.findById(id);
		if (op1.isPresent()) {
			return op1.get();
		}
		return null;
	}

//	public List<Members> findByQuery(String query) {
////		return mRepos.findByMemberIDContaining(query);
//		return mRepos.findBynickNameContaining(query);
//	}

	public List<Members> findAll() {
		return mRepos.findAll();
	}

	public boolean existsByUsername(String username) {
		return mRepos.existsByUsername(username);
	}

	public boolean existsByPassword(String phone) {
		return mRepos.existsByPassword(phone);
	}

	public Members getMemberById(int memberId) {
		Optional<Members> optionalMember = mRepos.findById(memberId);
		if (optionalMember.isPresent()) {
			return optionalMember.get();
		}
		return null;
	}

	public Members findByUsername(String username) {
		Optional<Members> uResp = mRepos.findByUsername(username);
		if (uResp.isPresent()) {
			return uResp.get();
		}
		return null;
	}

	// 根据会员ID检索會員暱稱
	public String getNickNameByMemberId(int memberId) {
		Members member = findById(memberId);
		if (member != null) {
			return member.getNickName();
		} else {
			return "你哪位"; // 如果找不到会员信息，可以返回一个默认昵称
		}
	}
	
	// 根据会员ID检索username
	public int getMemberIdByUsername(String username) {
		Members member = findByUsername(username);
		if (member != null) {
			return member.getMemberId();
		} else {
			return 0; 
		}
	}
	
	// 根据会员ID检索会员信息
	public String getNickNameByUsername(String username) {
		Members member = findByUsername(username);
		if (member != null) {
			return member.getNickName();
		} else {
			return "你哪位"; // 如果找不到会员信息，可以返回一个默认昵称
		}
	}

	
}
