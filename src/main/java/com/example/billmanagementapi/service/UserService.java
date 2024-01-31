package com.example.billmanagementapi.service;

import com.example.billmanagementapi.entity.User;
import com.example.billmanagementapi.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	
	User readUser();
	
	User updateUser(UserModel user);
	
	void deleteUser();
	
	User getLoggedInUser();
}
