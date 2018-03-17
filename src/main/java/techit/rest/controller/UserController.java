package techit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser( @PathVariable Long id )
    {
        return userDao.getUser( id );
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public List<User> getUsers()
    {
        return userDao.getUsers();
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public User addUser( @RequestBody User user )
    {
        if( user.getUsername() == null || user.getPassword() == null )
            throw new RestException( 400, "Missing username and/or password." );

        return userDao.saveUser( user );
    }

}
