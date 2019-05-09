package com.onboard.demo.service.impl;

import com.onboard.demo.model.User;
import com.onboard.demo.repository.UserRepository;
import com.onboard.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> find(String filter) {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return null;
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
