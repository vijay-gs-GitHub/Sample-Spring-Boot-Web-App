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
		UserResponse response = new UserResponse();
		HttpStatus status = HttpStatus.OK;
		response.setStatus(200);
		response.setMessage("User Found !!");
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

			if(!user.getUserName().equals(userName) && !user.getPassword().equals(password)){
				status = HttpStatus.UNAUTHORIZED;
				response.setStatus(401);
				response.setStatusCode(FAILED);
				response.setMessage("User Not Found !!");
			}  

		}
		return new ResponseEntity<UserResponse>(response,status);
	}



}
