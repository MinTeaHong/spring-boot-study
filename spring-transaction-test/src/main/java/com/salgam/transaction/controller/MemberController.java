package com.salgam.transaction.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salgam.transaction.api.ApiMessage;
import com.salgam.transaction.domain.Member;
import com.salgam.transaction.service.ErrorTestService;
import com.salgam.transaction.service.MemberService;


@RestController
public class MemberController {
	
	private final MemberService memberService;
	private final ErrorTestService errorTestService;
	
	
	MemberController(MemberService memberService,ErrorTestService errorTestService) {
		this.memberService = memberService;
		this.errorTestService = errorTestService;
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
	
	
	@PostMapping("transaction/test/members_1/{name}")
	public ApiMessage<Member> transactionTest1(@PathVariable String name ) {
		return ApiMessage.succeed( errorTestService.addMember1(name));
	}
	@PostMapping("transaction/test/members_2/{name}")
	public ApiMessage<Member> transactionTest2(@PathVariable String name ) {
		return ApiMessage.succeed( errorTestService.addMember2(name));
	}
	
	
	@PostMapping("transaction/test/members_3/{name}")
	public ApiMessage<Member> transactionTest3(@PathVariable String name ) {
		return ApiMessage.succeed( errorTestService.addMember3(name));
	}
	
	@PostMapping("transaction/test/members_3_throw/{name}")
	public ApiMessage<Member> transactionTestThrow3(@PathVariable String name ) throws Exception {
		return ApiMessage.succeed( errorTestService.addMember3_Throw(name));
	}
	
	
	@PostMapping("transaction/test/members_4/{name}")
	public ApiMessage<Member> transactionTest4(@PathVariable String name ) {
		return ApiMessage.succeed( errorTestService.addMember4(name));
	}
	
}
