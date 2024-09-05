package com.javarush.springproject.repository;

import com.javarush.springproject.entity.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CharacterRepo extends CrudRepository<Character, Long> {
    @Transactional
    Optional<Character> findByName(String name);
}
