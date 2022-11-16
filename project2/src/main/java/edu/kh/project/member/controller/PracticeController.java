package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.service.PracticeService;
import edu.kh.project.member.model.vo.Member;

@Controller
public class PracticeController {
	
	@Autowired
	private PracticeService service;
	
	// 로그인
	@PostMapping
	public String memberLogin(Member inputMember,
							  RedirectAttributes ra,
							  Model model) {
		Member loginMember = service.memberLogin(inputMember);
		
		
		return null;
	}
}
