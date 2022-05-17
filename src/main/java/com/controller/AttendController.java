package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.MemberDTO;
import com.dto.RSpotDTO;
import com.dto.SSpotDTO;
import com.service.RSpotService;
import com.service.SSpotService;

@Controller
public class AttendController {
	@Autowired
	RSpotService Rservice;
	@Autowired
	SSpotService Sservice;
	
	
	@RequestMapping("/RAttend")
	public String RAttend(RSpotDTO dto) {//응모
		Rservice.registinglist(dto);
		return null;
	}
	@RequestMapping("/SAttend")
	@ResponseBody
	public String SAttend(SSpotDTO dto) {
		System.out.println(dto);
		Sservice.registinglist(dto);
		return "응모";
	}
	@RequestMapping("UpdateMyAttendingRR")
	public String UpdateMyAttendingRR(RSpotDTO rdto, HttpSession session) {//수정화면에서 update 실행
		Rservice.UpdateMyAttendingR(rdto);
		return "redirect:Mypage";
	}
	@RequestMapping("UpdateMyAttendingSS")
	public String UpdateMyAttendingSS(SSpotDTO rdto, HttpSession session) {
		Sservice.UpdateMyAttendingS(rdto);
		return "redirect:Mypage";
	}
	@RequestMapping("DeleteMyAttendingRR")// Raffleno도 같이 넘겨주도록 향후 수정
	public String DeleteMyAttendingRR(HttpSession session) {//delete 실행
			MemberDTO dto = (MemberDTO)session.getAttribute("login");
			int memberno = dto.getMemberno();
			Rservice.DeleteMyAttendingR(memberno);
			return "redirect:Mypage";
	}
	@RequestMapping("DeleteMyAttendingSS")// Raffleno도 같이 넘겨주도록 향후 수정
	public String DeleteMyAttendingSS(HttpSession session) {
			MemberDTO dto = (MemberDTO)session.getAttribute("login");
			int memberno = dto.getMemberno();
			Sservice.DeleteMyAttendingS(memberno);
			return "redirect:Mypage";
	}
	
}
