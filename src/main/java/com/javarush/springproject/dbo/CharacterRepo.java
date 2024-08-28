package com.javarush.springproject.dbo;

import com.javarush.springproject.entity.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Repository
public interface CharacterRepo extends CrudRepository<Character, Long> {
    @Transactional
    Stream<Character> findByName(String name);
}
