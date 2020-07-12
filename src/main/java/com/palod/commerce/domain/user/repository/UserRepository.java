package com.palod.commerce.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.palod.commerce.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
