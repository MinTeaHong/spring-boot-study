package com.jpa.shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpa.shop.domain.Address;
import com.jpa.shop.domain.Member;
import com.jpa.shop.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/members/new")
	public String createForm( Model model ) {
		//빈 껍대기를 가져가는 이유는 validation 같은 것들을 위해서이다.
		model.addAttribute("memberForm" , new MemberForm() );
		return "members/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(@Valid MemberForm form , BindingResult result) {
		
		//BindingResult result는 @Valid의 결과가 팅기지 않고 result로 묶이게 한다.
		//그래서 아래 코드가 실행이 되면서 그리고 타임리프를 이용해서 th:if="${#fields.hasErrors('name')}" th:errors="*{name}"
		//로 에러를 쉽게 술력을 가능하게 한다.
		if (result.hasErrors()) {
			return "members/createMemberForm";
		}
		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
		
		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);
		
		memberService.join(member);
		return "redirect:/";
		
	}
	
	// 현재는 엔터티를 반환 했는데 실제 api를 만들 경우에는 
	// 엔터티를 반환하면 안된다.
	// 이는 엔터티가 변하게 되면 api 스펙 자체도 변하게 되는 것이다.
	// 즉 화면에서 받는것과 주는것은 dto를 기준으로 하는 것이 가장 깔끔하다.
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members",members);
		return "members/memberList";
	}
	
}
