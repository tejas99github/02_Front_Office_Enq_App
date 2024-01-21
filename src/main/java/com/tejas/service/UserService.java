package com.tejas.service;

import com.tejas.binding.LoginForm;
import com.tejas.binding.SignUpForm;
import com.tejas.binding.UnlockForm;

public interface UserService {

	public boolean signUp(SignUpForm form);

	public boolean unlockAccount(UnlockForm form);

	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);

}
 