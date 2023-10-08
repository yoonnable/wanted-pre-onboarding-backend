package com.pre.wanted.user.repository;

import com.pre.wanted.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
