package com.javarush.springproject.dto;

public enum CharacterClass {
    BARBARIAN,
    PALADIN,
    SORCERER,
    NECROMANCER,
    AMAZON,
    DRUID,
    ASSASSIN;

    public static CharacterClass getClass(String characterTitle) {
        CharacterClass characterClass = null;
        switch (characterTitle) {
            case "Barbarian" -> characterClass = CharacterClass.BARBARIAN;
            case "Paladin" -> characterClass = CharacterClass.PALADIN;
            case "Sorcerer" -> characterClass = CharacterClass.SORCERER;
            case "Necromancer" -> characterClass = CharacterClass.NECROMANCER;
            case "Amazon" -> characterClass = CharacterClass.AMAZON;
            case "Druid" -> characterClass = CharacterClass.DRUID;
            case "Assassin" -> characterClass = CharacterClass.ASSASSIN;
        }
        return characterClass;
    }
}
