package com.tejas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tejas.binding.LoginForm;
import com.tejas.binding.SignUpForm;
import com.tejas.binding.UnlockForm;
import com.tejas.constants.AppConstants;
import com.tejas.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userservice;

	@GetMapping("/signup")
	public String signUpPage(Model model) {

		model.addAttribute("user", new SignUpForm());

		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userservice.signUp(form);

		if (status) {

			model.addAttribute(AppConstants.SUCC_MSG, " Account Created - Check Your Email");

		} else {

			model.addAttribute(AppConstants.ERR_MSG, "Choose Unique Email");

		}

		return "signup";

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();

		unlockFormObj.setEmail(email);

		model.addAttribute(AppConstants.UNLOCK_STR, unlockFormObj);

		return AppConstants.UNLOCK_STR;
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {

			boolean status = userservice.unlockAccount(unlock);

			if (status) {

				model.addAttribute(AppConstants.SUCC_MSG, "Your Account Unlocked Successfully");

			} else {

				model.addAttribute(AppConstants.ERR_MSG, "Given Temporary Pwd is Incorrect Plz Check Your Mail");
			}

		} else {

			model.addAttribute(AppConstants.ERR_MSG, "New Pwd and Confirm Pwd Should Be Same");

		}

		return AppConstants.UNLOCK_STR;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());

		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		String status = userservice.login(loginForm);

		if (status.contains("success")) {

			// redirect req to Dashboard controller method

			return "redirect:/dashboard";
		}

		model.addAttribute(AppConstants.ERR_MSG, status);

		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {

		return "forgotPwd";
	}

	@PostMapping("/forgotPw d")
	public String forgotPwd(@RequestParam("email") String email, Model model) {

		System.out.println(email);

		boolean status = userservice.forgotPwd(email);

		if (status) {

			// Send success message
			model.addAttribute(AppConstants.SUCC_MSG, "Password Sent To Your Email");
		} else {

			// Send error message
			model.addAttribute(AppConstants.ERR_MSG, "Invalid Email");
		}

		return "forgotPwd";
	}

}
