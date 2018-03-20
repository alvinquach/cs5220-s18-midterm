package techit.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import techit.AppConstants;
import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestBody Map<String, Object> credentials) {
		Object username = credentials.get("username");
		Object password = credentials.get("password");
		if(username != null && username instanceof String && password != null && password instanceof String) {
			User user = userDao.getUser((String)username);
			if (user != null && passwordEncoder.matches((String)password, user.getHash())) {
				Map<String, Object> claims = new HashMap<>();
				claims.put("username", username);
				claims.put("type", user.getType());
				String jwt = Jwts.builder()
						.setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
						.signWith(SignatureAlgorithm.HS512, AppConstants.JWT_SECRET)
						.compact();
				return AppConstants.JWT_PREFIX + jwt;
			}
		}
		throw new RestException(401, "Invalid username or password.");
	}

}
