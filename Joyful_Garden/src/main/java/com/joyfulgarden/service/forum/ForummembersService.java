package com.joyfulgarden.service.forum;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.Members;
import com.joyfulgarden.model.forum.Forummembers;
import com.joyfulgarden.model.forum.ForummembersRepository;
import com.joyfulgarden.model.forum.Posts;

@Service
@Transactional
public class ForummembersService {

	@Autowired
	private ForummembersRepository forummemRepos;

	public Forummembers register(Forummembers forummembers) {
		// 檢查帳號是否已存在
		if (forummemRepos.findByFnickname(forummembers.getFnickname()) != null) {
			throw new RuntimeException("帳號已存在");
		}
		// 其他驗證邏輯可在此處加入

		// 密碼加密等處理可在此處加入

		// 儲存會員資料
		return forummemRepos.save(forummembers);
	}

	public Forummembers login(String fnickname, String fpassword) {
		Forummembers forummembers = forummemRepos.findByFnickname(fnickname);
		if (forummembers == null || !forummembers.getFpassword().equals(fpassword)) {
			throw new RuntimeException("帳號或密碼錯誤");
		}
		// 登入成功，可在此處進行其他相關處理，如產生 token 等
		return forummembers;
	}

	public Forummembers findById(Integer forummemberID) {
		Optional<Forummembers> optional = forummemRepos.findById(forummemberID);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public Forummembers findFnicknameByForummemberID(Integer forummemberID) {
		return forummemRepos.findFnicknameByForummemberID(forummemberID);
	}

	public Members findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
