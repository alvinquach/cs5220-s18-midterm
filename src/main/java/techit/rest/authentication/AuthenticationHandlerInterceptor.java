package techit.rest.authentication;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import techit.AppConstants;
import techit.model.User.Type;
import techit.rest.error.RestException;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {

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
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith(AppConstants.JWT_PREFIX)) {
			token = token.substring(AppConstants.JWT_PREFIX.length());
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(AppConstants.JWT_SECRET)
						.parseClaimsJws(token)
						.getBody();
				if (claims.get("username") != null && claims.get("type") != null) {
				
					// If types of users allowed were specified, then check against user's type.
					if (!allowedTypes.isEmpty()) {
						Type type = Type.valueOf((String)claims.get("type"));
						if (allowedTypes.contains(type)) {
							return true;
						}
						throw new RestException(401, "User does not have access to this API");
					}
					else {
						return true;
					}
				}
			}
			catch (ExpiredJwtException | SignatureException e) {
				// Do nothing...let it throw the exception at the end.
			}
		}
		throw new RestException(401, "Authorization token is missing or invalid");
		
	}
	
}
