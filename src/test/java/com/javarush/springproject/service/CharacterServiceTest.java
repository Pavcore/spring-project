package com.javarush.springproject.service;

import com.javarush.springproject.entity.Character;
import com.javarush.springproject.repository.CharacterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Mock
    private CharacterRepo characterRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }

    @Test
    void updateCharacterName() {
    }

    @Test
    void deleteCharacter() {
    }

    @Test
    void outputCharacterList() {
    }

    @Test
    void testGetCharacterWhenCharacterNotNull() {
        String name = "randomName";

        when(characterRepository.findByName(name)).thenReturn(Optional.of(new Character()));

        characterService.getCharacter(name);

        verify(characterRepository, times(1)).findByName(name);
    }

    @Test
    void testGetCharacterWhenCharacterIsNull() {
        String name = "randomName";

        when(characterRepository.findByName(name)).thenReturn(Optional.empty());

        characterService.getCharacter(name);

        verify(characterRepository, times(1)).findByName(name);

        assertNull(characterRepository.findByName(name).orElse(null));
    }

    @Test
    void testSaveGameStatistic() {
        Character character = new Character();

        characterService.saveGameStatistic(character);

        verify(characterRepository, times(1)).save(character);
    }
}