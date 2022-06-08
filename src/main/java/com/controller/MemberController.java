package com.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.MemberDTO;
import com.service.MemberSerivce;

@Controller
public class MemberController {
	@Autowired
	MemberSerivce service;

	@RequestMapping(value = "/memberAdd")
	public String memberAdd(MemberDTO m, Model model) {
		service.memberAdd(m);
		model.addAttribute("success", "회원가입성공");
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String login(@RequestParam Map<String, String> map, Model model, HttpSession session) {
		MemberDTO dto = service.login(map);
		if (dto != null) {
			session.setAttribute("login", dto);
			return "index";// main.jsp
		} else {
			model.addAttribute("mesg", "아이디 또는 비번이 잘못되었습니다.");
			return "loginForm";
		}

	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:../raffle";
	}
	
	@RequestMapping(value = "/idDuplicateCheck", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String idDuplicatedCheck(@RequestParam("id") String userid) {
		MemberDTO dto= service.idCheck(userid);
		String mesg="";
		if(dto != null) {
			mesg="아이디 중복";
		}
		return mesg;
	}
	@RequestMapping(value = "/updateMyinfo")
	public ModelAndView updateMyinfo(MemberDTO dto, HttpSession session) {
		MemberDTO dto2 = (MemberDTO)session.getAttribute("login");
		int memberno = dto2.getMemberno();
		dto.setMemberno(memberno);
		System.out.println(dto);
		service.updateMyinfo(dto);
		dto = service.myinfo(memberno);
		ModelAndView mav = new ModelAndView();
		mav.addObject("mypage",dto);
		mav.setViewName("Mypage");
		return mav;
	}

}
