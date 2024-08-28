package com.javarush.springproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "quest")
public class Quest {

    @Id
    private Long id;

    @Column(name = "main_text", length = 512)
    private String mainText;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "wrong_answer")
    private String wrongAnswer;

    @Column(name = "loose_text")
    private String looseText;
}
