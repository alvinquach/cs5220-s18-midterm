package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.User;

@Test(groups = "UserDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void getUser()
    {
        assert userDao.getUser( 1L ).getUsername().equalsIgnoreCase( "techit" );
    }

    @Test
    public void getUsers()
    {
        assert userDao.getUsers().size() >= 5;
    }

    @Test
    public void saveUser()
    {
        User user = new User();
        user.setUsername( "Tom" );
        user.setHash(
            "$2a$10$sVTyojWXADG2e2gG4RlOLe9LJxkP7KHhnpPHAvClt5KPdpVJMMN9S" );
        user.setEmail( "tom@localhost" );
        user = userDao.saveUser( user );

        assert user.getId() != null;
    }

}
