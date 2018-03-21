package techit.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.rest.authentication.TokenAuthenticationService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestBody Map<String, Object> credentials) {
		return tokenAuthenticationService.generateToken(credentials);
	}

}
