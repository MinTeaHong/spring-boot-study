package com.salgam.transaction.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiMessage<T> {
	
	private final T data;
	private final String errorMsg;
	
	
	ApiMessage(T data, String errorMsg) {
	    this.data 			= data;
	    this.errorMsg 		= errorMsg;
	}
	
	public static <T> ApiMessage<T> succeed(T data) {
		return new ApiMessage<>( data , null );
	}
	
}
