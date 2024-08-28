package com.javarush.springproject.service;

import com.javarush.springproject.dbo.CharacterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    private final CharacterRepo characterRepository;
    private final GameService gameService;

    @Autowired
    public IndexService(CharacterRepo characterRepository, GameService gameService) {
        this.characterRepository = characterRepository;
        this.gameService = gameService;
    }


}
