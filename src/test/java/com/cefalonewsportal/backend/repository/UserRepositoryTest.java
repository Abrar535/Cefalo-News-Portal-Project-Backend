package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    public UserRepositoryTest(){

    }
    @Before
    public void setUp(){

        User user = new User("abrar","Abrar Ul Haque","abcd");
        entityManager.persist(user);
        entityManager.flush();
    }
    @Test
    public void testfindByUserName(){
        String userName = "abrar";
        User user = userRepository.findByUserName(userName);
        assertEquals(user.getUserName(),userName);
    }


}
