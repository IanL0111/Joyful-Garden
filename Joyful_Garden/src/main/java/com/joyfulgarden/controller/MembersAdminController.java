package com.joyfulgarden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyfulgarden.model.MemberDTO;
import com.joyfulgarden.model.Members;
import com.joyfulgarden.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")

public class MembersAdminController {

	@Autowired
	private MembersService mService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 註冊功能(新增會員)
	@PostMapping("/register")
	public String Memberinsert(@ModelAttribute MemberDTO memberDTO) {
		// 設定預設值: 會員等級預設為1
		if (memberDTO.getMemberLevel() == 0) {
			memberDTO.setMemberLevel(1);
		}

		// 創建 Members 物件並插入資料庫
		Members insertBean = new Members();

		// 對密碼進行編碼，並上傳資料庫
		String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
		insertBean.setPassword(encodedPassword);
		
		// 組合圖片的完整 URL (有問題QQ)
		String storagePath = "images/";
	    String fullPictureURL = "https://firebasestorage.googleapis.com/v0/b/joyfulgarden-fee93.appspot.com/o/"+storagePath + memberDTO.getMemberPicture();
	    insertBean.setMemberPicture(fullPictureURL);

		insertBean.setUsername(memberDTO.getUsername());
		insertBean.setNickName(memberDTO.getNickName());
		insertBean.setPhoneNumber(memberDTO.getPhoneNumber());
		insertBean.setBirthdate(memberDTO.getBirthdate());
		insertBean.setAddress(memberDTO.getAddress());
		insertBean.setVerificationCode(memberDTO.getVerificationCode());
		insertBean.setVerified(memberDTO.isVerified());
		insertBean.setDeleted(memberDTO.isDeleted());
		insertBean.setMemberLevel(memberDTO.getMemberLevel());
		mService.insert(insertBean);
		return "redirect:/members";
	
	}

	// 登入功能
//	@PostMapping("/login")
//	public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
//		try {
//
//			// 使用 AuthenticationManager 進行驗證
//			Authentication authentication = authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//			// 將驗證結果設置到 SecurityContext 中
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			System.out.println("登入成功");
//
//			// 將用戶名存儲到Session中
//			session.setAttribute("username", username);
//
//			// 登入成功後重定向到首頁或其他需要登入後訪問的頁面
//			return "redirect:web/homePage";
//
//		} catch (AuthenticationException e) {
//
//			// 登入失敗時的錯誤處理
//			System.out.println("登入失敗");
//			return "redirect:/loginPage"; // 重定向到登入頁面並顯示錯誤訊息
//		}
//	}
//	
	 // 登出功能
	 @GetMapping("/logout")
	 public String logout(HttpServletRequest request) {
	 // 實現登出邏輯
	 HttpSession session = request.getSession();
	 session.removeAttribute("loggedInUser");
	 System.err.println("登出成功");
	 return "redirect:/web/loginPage"; // 重定向到登入頁面
	 }
	 

	// 更新會員資料

	@GetMapping("/members/{memberId}")
	public String getMemberDetails(@PathVariable("memberId") int memberId, Model model) {
		// 獲取會員詳細資料的邏輯
		Members member = mService.findById(memberId);
		model.addAttribute("member", member);
		return "web/editMember"; // 這裡返回的是編輯會員的頁面
	}

	@PostMapping("/edit/{memberId}")
	public String processEditMember(@PathVariable("memberId") int memberId, @ModelAttribute MemberDTO memberDTO) {
		Members member = mService.findById(memberId);
		// 更新會員資料
		member.setUsername(memberDTO.getUsername());
		member.setPassword(memberDTO.getPassword());
		member.setNickName(memberDTO.getNickName());
		member.setMemberPicture(memberDTO.getMemberPicture());
		member.setPhoneNumber(memberDTO.getPhoneNumber());
		member.setBirthdate(memberDTO.getBirthdate());
		member.setAddress(memberDTO.getAddress());
		member.setVerificationCode(memberDTO.getVerificationCode());
		member.setVerified(memberDTO.isVerified());
		member.setDeleted(memberDTO.isDeleted());
		member.setMemberLevel(memberDTO.getMemberLevel());
		mService.update(member);
		return "redirect:/members"; // 更新成功後重定向到會員列表頁面
	}

	// 刪除會員
	@DeleteMapping("/members/{MemberID}")
	@ResponseBody
	public String processDeleteAction(@PathVariable int MemberID) {
		mService.deleteById(MemberID);
		return "刪除成功!";
	}

	// // 單筆會員資料查詢(透過query內容進行模糊查詢)
	// @GetMapping("/members/{query}")
	// public String processFindAction(@PathVariable("query") String query) {
	// // 根據query的不同內容進行模糊查詢
	// List<Members> resultBeans = mService.findByQuery(query);
	//
	// if (!resultBeans.isEmpty()) {
	// StringBuilder result = new StringBuilder();
	// for (Members resultBean : resultBeans) {
	// result.append(resultBean.getMemberId()).append(" ")
	// .append(resultBean.getMemberName()).append(" ")
	// .append(resultBean.getMemberPicture()).append(" ")
	// .append(resultBean.getAccount()).append(" ")
	// .append(resultBean.getPassword()).append(" ")
	// .append(resultBean.getPhoneNumber()).append(" ")
	// .append(resultBean.getTelNumber()).append(" ")
	// .append(resultBean.getBirthday()).append(" ")
	// .append(resultBean.getResidentialAddress()).append(" ")
	// .append(resultBean.getDefaultShippingAddress()).append(" ")
	// .append(resultBean.getVerificationPwd()).append(" ")
	// .append(resultBean.getVerificationStatus()).append(" ")
	// .append(resultBean.getMemberStatus()).append(" ")
	// .append(resultBean.getMemberLevel()).append("<br>");
	// }
	// return result.toString();
	// } else {
	// return "no result";
	// }
	// }

	// 單筆會員資料查詢(透過MemberID)
	@ResponseBody
	@GetMapping("/searchbymemberid/{MemberId}")
	public String processFindByIdAction(@PathVariable("MemberId") Integer MemberId) {
		Members resultBean = mService.findById(MemberId);
		if (resultBean != null) {
			return resultBean.getMemberId() + " " + resultBean.getUsername() + " " + resultBean.getPassword() + " "
					+ resultBean.getNickName() + " " + resultBean.getMemberPicture() + " " + resultBean.getPhoneNumber()
					+ " " + resultBean.getBirthdate() + " " + resultBean.getAddress() + " "
					+ resultBean.getVerificationCode() + " " + resultBean.isVerified() + " " + resultBean.isDeleted()
					+ " " + resultBean.getMemberLevel();
		}
		return "no result";
	}

	// 查詢全部會員(顯示會員清單)
	@GetMapping("/members")
	public String processFindAllAction(Model model) {
		model.addAttribute("members", mService.findAll());
		return "web/memberList";
	}

	// 跳轉顯示: 註冊頁面
	@GetMapping("/registerPage")
	public String showRegisterPage() {
		return "web/registerPage";
	}

	// 跳轉顯示: 登入頁面
	@GetMapping("/loginPage")
	public String showloginPage() {
		return "web/loginPage";
	}

	// 跳轉顯示: 主畫面
	@GetMapping(value = { "/homePage", "/" })
	public String showhomePage(Model model, HttpSession session) {
	    // 從 Session 中獲取登入使用者的名稱
	    String loggedInUsername = (String) session.getAttribute("loggedInUser");
	    
	    // 使用登入使用者的名稱來查詢該使用者的詳細資料，然後將其傳遞到前端頁面
	    Members loggedInMember = mService.findByUsername(loggedInUsername);
	    model.addAttribute("loggedInMember", loggedInMember);
	    
	    return "web/homePage";

	}
	
	
	// 跳轉顯示: 會員中心
	@GetMapping("/memberCenter")
	public String showmemberCenter() {
		return "web/memberCenter";
	}

}
