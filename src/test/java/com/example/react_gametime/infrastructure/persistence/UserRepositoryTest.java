package com.example.react_gametime.infrastructure.persistence;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddAndFindUser() {
        UserEntity user = new UserEntity();
        user.setUsername("Alice");
        user = userRepository.save(user);

        Optional<UserEntity> found = userRepository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getUsername());
    }

    @Test
    void testRemoveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("Bob");
        user = userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<UserEntity> found = userRepository.findById(user.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testFindNonExistentUser() {
        Optional<UserEntity> found = userRepository.findById(999L);
        assertFalse(found.isPresent());
    }
}
