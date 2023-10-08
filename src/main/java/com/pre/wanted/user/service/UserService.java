package com.pre.wanted.user.service;

import com.pre.wanted.user.entity.User;
import com.pre.wanted.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found User: " + id));
    }
}
