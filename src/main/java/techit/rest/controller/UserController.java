package techit.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.model.User;
import techit.model.User.Type;
import techit.model.dao.UserDao;
import techit.rest.authentication.AllowedUserTypes;
import techit.rest.authentication.TokenAuthenticationService;
import techit.rest.error.RestException;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	private TokenAuthenticationService tokenAuthenticationService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @AllowedUserTypes(Type.ADMIN)
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User addUser( @RequestBody User user )
    {
        if( user.getUsername() == null || user.getPassword() == null )
            throw new RestException( 400, "Missing username and/or password." );
        
        user.setHash(passwordEncoder.encode(user.getPassword()));
        return userDao.saveUser( user );
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(HttpServletRequest request, @PathVariable Long id )
    {
    	User requester = tokenAuthenticationService.getUserFromRequest(request);
    	if (requester != null && (requester.getType() == Type.ADMIN || requester.getId().equals(id))) {
    		User result = userDao.getUser( id );
    		if (result != null) {
    			return result;
    		}
    		throw new RestException(404, "User does not exist");
    	}
    	throw new RestException(403, "You do not have access this user");
    }

}
