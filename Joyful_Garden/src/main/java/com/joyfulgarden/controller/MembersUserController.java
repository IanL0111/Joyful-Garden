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
import org.springframework.web.servlet.ModelAndView;

import com.joyfulgarden.model.MemberDTO;
import com.joyfulgarden.model.Members;
import com.joyfulgarden.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")


public class MembersUserController {

	@Autowired
	private MembersService mService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 註冊功能(新增會員)
	@ResponseBody
	@PostMapping("/registerPage")
	public ModelAndView Memberinsert(@ModelAttribute MemberDTO memberDTO, ModelAndView modelAndView) {
		// 設定預設值: 會員等級預設為1
		if (memberDTO.getMemberLevel() == 0) {
			memberDTO.setMemberLevel(1);
		}

		// 創建 Members 物件並插入資料庫
		Members insertBean = new Members();

		// 對密碼進行編碼
		String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
		insertBean.setPassword(encodedPassword);

		insertBean.setUsername(memberDTO.getUsername());
		insertBean.setNickName(memberDTO.getNickName());
		insertBean.setMemberPicture(memberDTO.getMemberPicture());
		insertBean.setPhoneNumber(memberDTO.getPhoneNumber());
		insertBean.setBirthdate(memberDTO.getBirthdate());
		insertBean.setAddress(memberDTO.getAddress());
		insertBean.setVerificationCode(memberDTO.getVerificationCode());
		insertBean.setVerified(memberDTO.isVerified());
		insertBean.setDeleted(memberDTO.isDeleted());
		insertBean.setMemberLevel(memberDTO.getMemberLevel());
		mService.insert(insertBean);
		modelAndView.setViewName("redirect:/user/loginPage");
		return modelAndView;
	}
	
	

	 //登入功能
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		try {
		
			// 使用 AuthenticationManager 進行驗證
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			
			// 將驗證結果設置到 SecurityContext 中
			SecurityContextHolder.getContext().setAuthentication(authentication);
			

			// 获取 memberId 和 nickName
	        int memberId = mService.getMemberIdByUsername(username);
	        Members member = mService.getMemberById(memberId);
	        
	        System.out.println("nickName : "+member.getNickName());
			
			// 將使用者資料存儲到Session中
			session.setAttribute("member", member);
	

			// 登入成功後重定向到首頁或其他需要登入後訪問的頁面
			return "Login success";
			
//			 // 根据用户名进行跳转
//	        if ("admin@gmail.com".equals(username)) {
//	            // 如果用户名为 admin@gmail.com，则重定向到/admin/members页面
//	            return "redirect:/admin/members";
//	        } else {
//	            // 否则登录成功后重定向到首頁或其他需要登入後訪問的頁面
//	            return "Login success";
//	        }

		} catch (AuthenticationException e) {

			return "Login Fail";
		}

	}
//	
//	  登出功能
	 @GetMapping("/logout")
	 @ResponseBody
	 public String logout(HttpServletRequest request) {
	 // 實現登出邏輯
	 HttpSession session = request.getSession();
	 session.removeAttribute("member");
	 System.err.println("登出成功");
	 return "redirect:/user/loginPage"; // 重定向到登入頁面
	 }
	
	
//	 @ResponseBody
//	 @PostMapping("/logout")
//	
//	 public ModelAndView logout(HttpServletRequest request,ModelAndView modelAndView) {
//		 HttpSession session = request.getSession();
//		 session.removeAttribute("username");
//		 System.err.println("登出成功");
//		 
//		 modelAndView.setViewName("redirect:/loginPage");
//		 return modelAndView; // 重定向到登入頁面
//	 }
	 
	 
	 
	 

	// 更新會員資料
	@GetMapping("/members/{memberId}")
	public String getMemberDetails(@PathVariable("memberId") int memberId, Model model) {
		// 獲取會員詳細資料的邏輯
		Members member = mService.findById(memberId);
		model.addAttribute("member", member);
		return "web/editMember"; // 這裡返回的是編輯會員的頁面
	}

//	@PostMapping("/edit/{memberId}")
//	public String processEditMember(@PathVariable("memberId") int memberId, @ModelAttribute MemberDTO memberDTO) {
//		Members member = mService.findById(memberId);
//		// 更新會員資料
//		member.setUsername(memberDTO.getUsername());
//		member.setPassword(memberDTO.getPassword());
//		member.setNickName(memberDTO.getNickName());
//		member.setMemberPicture(memberDTO.getMemberPicture());
//		member.setPhoneNumber(memberDTO.getPhoneNumber());
//		member.setBirthdate(memberDTO.getBirthdate());
//		member.setAddress(memberDTO.getAddress());
//		member.setVerificationCode(memberDTO.getVerificationCode());
//		member.setVerified(memberDTO.isVerified());
//		member.setDeleted(memberDTO.isDeleted());
//		member.setMemberLevel(memberDTO.getMemberLevel());
//		mService.update(member);
//		return "redirect:/members"; // 更新成功後重定向到會員列表頁面
//	}
	
	@PostMapping("/web/memberCenter/{memberId}")
	public String processEditMember(@PathVariable("memberId") int memberId, @RequestParam String nickName, @RequestParam String phoneNumber, @RequestParam String address,HttpSession session) {
	    Members member = mService.findById(memberId);
	    
	    // 只更新用户修改过的个人信息


	    if (nickName != null && !nickName.isEmpty()) {
	        member.setNickName(nickName);
	    }
	    if (phoneNumber != null && !phoneNumber.isEmpty()) {
	        member.setPhoneNumber(phoneNumber);
	    }
	    if (address != null && !address.isEmpty()) {
	        member.setAddress(address);
	    }
	    
	    // 更新数据库中的会员信息
	    mService.update(member);
	    
	    session.setAttribute("member", member);
	    
	    
	    // 重定向到会员中心页面
	    return "redirect:/user/memberCenter"; 
	   
	}


	// 刪除會員
	@DeleteMapping("/members/{MemberID}")
	@ResponseBody
	public String processDeleteAction(@PathVariable int MemberID) {
		mService.deleteById(MemberID);
		return "刪除成功!";
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
	@GetMapping("/homePage")
	public String showhomePage() {
		return "web/homePage";
	}
	
	
	// 跳轉顯示: 主畫面
//	@GetMapping(value = { "/homePage", "/" })
	
	public String showHomePage(@PathVariable("MemberId") Integer MemberId, HttpSession session, Model model) {
	    // 从会话中获取用户名
	    String username = (String) session.getAttribute("username");
	    String memberId = (String) session.getAttribute("memberId");
	    String nickName = (String) session.getAttribute("nickName");
	    
	    System.out.println("Username from session: " + username);
	    System.out.println("Member ID: " + memberId);
	    System.out.println("NickName: " + nickName);

	    
	    // 检查会员是否存在
	    Members resultBean = mService.findByUsername(username);
	    if (resultBean == null) {
	        // 如果会员不存在，可能是未登录或会话已过期，重定向到登录页面
	        return "redirect:/loginPage";
	    }
	    
	    // 将会员昵称添加到模型中
	    model.addAttribute("nickName", nickName);
	    model.addAttribute("nickName", nickName);

	    // 返回首页视图名称
	    return "web/homePage";
	}
	
	
	// 跳轉顯示: 會員中心
	@GetMapping("/memberCenter")
	public String showmemberCenter() {
		return "web/memberCenter";
	}
	
	// 單筆會員資料查詢(透過MemberID)
	@ResponseBody
	@GetMapping("/searchbymemberid/{MemberId}")
	public String processFindByIdAction(@PathVariable("MemberId") Integer MemberId) {
		Members resultBean = mService.findById(MemberId);
		if (resultBean != null) {
			return resultBean.getMemberId() + " " 
				 + resultBean.getUsername() + " " 
				 + resultBean.getPassword() + " "
				 + resultBean.getNickName() + " " 
				 + resultBean.getMemberPicture() + " " 
				 + resultBean.getPhoneNumber()+ " " 
				 + resultBean.getBirthdate() + " " 
				 + resultBean.getAddress() + " "
				 + resultBean.getVerificationCode() + " " 
				 + resultBean.isVerified() + " " 
				 + resultBean.isDeleted() + " " 
				 + resultBean.getMemberLevel();
		}
		return "no result";
	}
	
//	@GetMapping("/homePage")
//	public String showHomePage(@RequestParam("MemberId") int MemberId, Model model) {
//		Members resultBean = mService.findById(MemberId);
//	    // 假设您已经从用户身份验证中获取了会员的ID，或者通过其他途径获得了会员ID
//	    int memberId = resultBean.getMemberId(); // 假设会员ID为123
//
//	    // 调用 MembersService 中的方法获取会员昵称
//	    String nickName = mService.getNickNameByMemberId(memberId);
//
//	    // 将会员昵称添加到模型中
//	    model.addAttribute("nickName", nickName);
//
//		// 返回首页视图名称
//	    return "web/homePage";
//	
//	}

}
