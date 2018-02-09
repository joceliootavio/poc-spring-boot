package com.ia.dell.springbootsample.mother;

import com.ia.dell.springbootsample.model.User;

public class UserMother {
    public static User create(String name) {
    	User user = new User();
    	user.setName(name);
    	user.setLogin(name);
    	user.setPassword(name+"@123");    	
    	user.setEmail("j@ia.com");
    	user.setAdmin(false);
    	return user;
    }
}
