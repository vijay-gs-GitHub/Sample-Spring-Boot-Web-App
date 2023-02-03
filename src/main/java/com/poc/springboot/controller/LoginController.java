package com.poc.springboot.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poc.springboot.model.User;
import com.poc.springboot.model.UserResponse;
import com.poc.springboot.service.*;


@Controller
public class LoginController {

	private static Logger logger = LogManager.getLogger(LoginController.class);
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	@Value("${user.name.poc}")
	private String userName;

	@Value("${user.password.poc}")
	private String password;

	@GetMapping("login")
	public String getForm() {
		return "LoginForm";
	}


	@PostMapping("/validateUser")
	@ResponseBody
	public ResponseEntity<UserResponse> validateUser(@ModelAttribute @Valid User user,
			BindingResult result) {

		logger.info("Inside Validate User ... ");
		boolean authorized = true;
		UserResponse response = new UserResponse();
		HttpStatus status = HttpStatus.OK;
		response.setStatus(200);
		response.setMessage("User " + user.getUserName() + " Found !!");
		response.setStatusCode(SUCCESS);

		if(result.hasErrors()){
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(
							Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
							);

			response.setErrorMessages(errors);
			response.setStatusCode(FAILED);
			response.setMessage(null);
		}else{
			if(!user.getUserName().equals(userName)){ 
				logger.info("User not found ");
				authorized = false;
			}
			else {
				logger.info("User found - verifying pass");
				try{
					authorized = UtilServ.isPasswordMatching(user.getPassword(), password);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
		}
        
		logger.info("User - " + user.getUserName() + " - " + ((authorized) ? "Authorized" : "UnAuthorized"));
 
		if(!authorized){
			status = HttpStatus.UNAUTHORIZED;
			response.setStatus(401);
			response.setStatusCode(FAILED);
			response.setMessage("User " + user.getUserName() + " Not Found !!");
		}

		return new ResponseEntity<UserResponse>(response,status);
	}
}
