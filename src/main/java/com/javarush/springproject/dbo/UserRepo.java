package com.javarush.springproject.dbo;

import com.javarush.springproject.entity.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
@EnableJpaRepositories(basePackages = "com.javarush.springproject.dbo")
public interface UserRepo extends CrudRepository<User, Long> {
    Stream<User> findByLogin(String login);
}
