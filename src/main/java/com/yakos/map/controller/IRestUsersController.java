package com.yakos.map.controller;


import org.springframework.web.bind.annotation.RequestBody;

import com.yakos.map.dto.CreateUserRequest;


public interface IRestUsersController {
	
	public boolean addUser(@RequestBody CreateUserRequest request);

}
