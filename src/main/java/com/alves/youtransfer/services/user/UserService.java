package com.alves.youtransfer.services.user;

import com.alves.youtransfer.models.user.User;
import com.alves.youtransfer.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void changeName(Long id, String name) {
        User user = userRepository.getReferenceById(id);
        user.setName(name);
    }

    @Transactional
    public void changePassword(Long id, String password) {
        User user = userRepository.getReferenceById(id);
        user.setPassword(password);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
