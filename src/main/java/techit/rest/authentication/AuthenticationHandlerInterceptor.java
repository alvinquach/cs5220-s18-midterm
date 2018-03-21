package techit.rest.authentication;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import techit.model.User.Type;
import techit.rest.error.RestException;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		// Login endpoint does not need security check.
		if (request.getRequestURL().toString().endsWith("login")) {
			return true;
		}
		
		if (!(handler instanceof HandlerMethod)) {
			throw new RestException(500, "Internal server error.");
		}
		
		// Check if the target method only allows specific user types.
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		AllowedUserTypes allowedUserTypes = handlerMethod.getMethodAnnotation(AllowedUserTypes.class);
		Set<Type> allowedTypes = new HashSet<>();
		if (allowedUserTypes != null) {
			for (Type type : allowedUserTypes.value()) {
				allowedTypes.add(type);
			}
		}
		
		// Validate JWT.
		return tokenAuthenticationService.validateToken(request.getHeader("Authorization"), allowedTypes);
		
	}
	

	
	
}
