package com.tejas.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejas.binding.LoginForm;
import com.tejas.binding.SignUpForm;
import com.tejas.binding.UnlockForm;
import com.tejas.constants.AppConstants;
import com.tejas.entity.UserDtlsEntity;
import com.tejas.repo.UserDtlsRepo;
import com.tejas.utils.EmailUtils;
import com.tejas.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignUpForm form) {

		// ToDo: Duplicate Mail Checking
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if (user != null) {
			return false;
		}

		// ToDo: Copy data from binding obj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// ToDo: Generate the random pwd and set to object,
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		// ToDo: Set the account status as LOCKED
		entity.setAccStatus(AppConstants.STR_LOCKED);

		// ToDo: Insert Record
		userDtlsRepo.save(entity);

		// ToDo: Send email to unlock the account

		String to = form.getEmail();
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;
		StringBuilder body = new StringBuilder("");
		body.append("<h1>Use Below Temporary Password To Unlock Your Account</h1>");
		body.append("Temporary Password :" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {

			entity.setPwd(form.getNewPwd());

			entity.setAccStatus(AppConstants.STR_UNLOCKED);

			userDtlsRepo.save(entity);

			return true;

		} else {

			return false;
		}

	}

	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {

			return AppConstants.INVALID_CREDENTIALS_MSG;
		}

		if (entity.getAccStatus().equals(AppConstants.STR_LOCKED)) {

			return AppConstants.ACCOUNT_LOCKED_MSG;
		}

		// create session and store user data in session object

		session.setAttribute(AppConstants.SRT_USER_ID, entity.getUserId());

		return AppConstants.STR_SUCCESS;
	}

	@Override
	public boolean forgotPwd(String email) {

		// Check record present in database with given email ;

		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// if record not available return false ;
		if (entity == null) {

			return false;
		}

		// if record available send password to email and return true

		String subject = AppConstants.RECOVER_PZZWD_EMAIL_SUBJECT;

		String body = "Your Pwd :: " + entity.getPwd();

		emailUtils.sendEmail(email, subject, body);

		return true;
	}

}
