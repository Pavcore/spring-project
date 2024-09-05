package com.javarush.springproject.repository;

import com.javarush.springproject.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    @Transactional
    Optional<User> findByLogin(String login);
}
