package com.example.esos.repositories;

import com.example.esos.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new User("sample user 1", "password1");
        user1.setManager(null);
        user2 = new User("sample user 2", "password2");
        user2.setManager(user1);
    }

    @Test
    void testSuccessfulSaving() {
        this.userRepository.save(user1);
        Optional<User> fetchedUser = this.userRepository.findUserByUsername(user1.getUsername());
        assertEquals(user1.getUsername(), fetchedUser.orElse(null).getUsername());
    }

    @Test
    void testSuccessfulSavingManager() {
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        Optional<User> fetchedUser = this.userRepository.findUserByUsername(user2.getUsername());
        assertEquals(user1.getUsername(), fetchedUser.orElse(null).getManager().getUsername());
    }


    @AfterEach
    void tearDown() {


    }
}