package com.javarush.springproject.repository;

import com.javarush.springproject.entity.Quest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepo extends CrudRepository<Quest, Long> {
}
