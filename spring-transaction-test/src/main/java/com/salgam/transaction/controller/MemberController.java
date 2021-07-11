package com.salgam.transaction.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salgam.transaction.api.ApiMessage;
import com.salgam.transaction.domain.Member;
import com.salgam.transaction.service.MemberService;


@RestController
public class MemberController {
	
	private final MemberService memberService;
	
	MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/member/{name}")
	public ApiMessage<Member> addMember( @PathVariable String name ) {
		return ApiMessage.succeed( memberService.addMember(name) );
	}
	
	@GetMapping("/members_1")
	public ApiMessage<List<Member>> getAllMember1() {
		return ApiMessage.succeed( memberService.getAllMember_READ_UNCOMMITTED() );
	}
	
	@GetMapping("/members_2")
	public ApiMessage<List<Member>> getAllMember2() {
		return ApiMessage.succeed( memberService.getAllMember_READ_COMMITTED() );
	}
	
	
	@GetMapping("/members_3")
	public ApiMessage<List<Member>> getAllMember3() {
		return ApiMessage.succeed( memberService.getAllMember_REPEATABLE_READ() );
	}
		
	@GetMapping("/members_4/{id}")
	public ApiMessage<Member> getAllMember4( @PathVariable int id) {
		return ApiMessage.succeed( memberService.getMember_SERIALIZABLE(id) );
	}
	
	@GetMapping("/members/{id}/{name}")
	public ApiMessage<Member> updateMember( @PathVariable int id, @PathVariable String name ) {
		return ApiMessage.succeed( memberService.updateMember(id, name) );
	}
	
}
