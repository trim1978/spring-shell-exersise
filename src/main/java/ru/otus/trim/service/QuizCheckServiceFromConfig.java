package ru.otus.trim.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.QuizAction;

@Service
public class QuizCheckServiceFromConfig implements QuizCheckService{
    @Setter
    @Getter
    private int checkCriterion;

    public QuizCheckServiceFromConfig(QuizConfig cfg) {
        this.checkCriterion = cfg.getEnough();
    }

    @Override
    public boolean checkQuizAction(QuizAction quizAction) {
        return quizAction.isComplete() && quizAction.getRightAnsweredQuestionsCount() >= checkCriterion;  }

    @Override
    public void setEnough(int enough) {
        setCheckCriterion(enough);
    }
}
