package com.javarush.springproject.service;

import com.javarush.springproject.dbo.QuestRepo;
import com.javarush.springproject.entity.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class QuestService {

    private final QuestRepo questRepository;

    @Autowired
    public QuestService(QuestRepo questRepository) {
        this.questRepository = questRepository;
    }

    public void setAttributeQuest(ModelAndView modelAndView, long level) {
        Quest quest = questRepository.findById(level).get();
        modelAndView.addObject("stage", quest.getMainText());
        modelAndView.addObject("firstAnswer", quest.getCorrectAnswer());
        modelAndView.addObject("secondAnswer", quest.getWrongAnswer());
        modelAndView.addObject("loseAttribute", quest.getLooseText());
    }

    public Long getLengthQuest() {
        return questRepository.count();
    }
}
