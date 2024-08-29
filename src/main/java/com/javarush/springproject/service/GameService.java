package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {

    private final AtomicLong atomicLong = new AtomicLong();
    private final UserRepo userRepository;
    private final QuestService questService;
    private int level = 1;

    @Autowired
    public GameService(UserRepo userRepository, QuestService questService) {
        this.userRepository = userRepository;
        this.questService = questService;
    }

    public void increaseGameAmount() {
        atomicLong.getAndIncrement();
    }

    @Transactional
    public ModelAndView gameStatistics(HttpServletRequest req, ModelAndView modelAndView) {
        modelAndView.addObject("gameQuantity", gameQuantity());
        modelAndView.addObject("characterList", getAllUserCharacters(req.getSession()));
        modelAndView.setViewName("statistic");
        return modelAndView;
    }

    public ModelAndView mainGame(HttpServletRequest req, ModelAndView modelAndView) {
        modelAndView.setViewName("game");
        String nextStage = req.getParameter("nextStage");
        modelAndView.getModel().get("nextStage");
        if (nextStage != null && nextStage.equals("lose")) {
            questService.setAttributeQuest(modelAndView, level);
            level = 1;
            modelAndView.setViewName("gameOver");
        } else if (level == questService.getLengthQuest()) {
            level = 1;
            modelAndView.setViewName("gameWin");
        } else {
            level++;
            questService.setAttributeQuest(modelAndView, level);
        }
        return modelAndView;
    }

    public ModelAndView getGame(ModelAndView modelAndView) {
        questService.setAttributeQuest(modelAndView, level);
        return modelAndView;
    }

    private List<Character> getAllUserCharacters(HttpSession session) {
        String login = (String) session.getAttribute("login");
        User user = userRepository
                .findByLogin(login)
                .findFirst()
                .get();
        return user.getCharacters();
    }

    private long gameQuantity() {
        return atomicLong.get();
    }
}
