package ru.otus.trim.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.dao.QuizDao;
import ru.otus.trim.domain.Answer;
import ru.otus.trim.domain.Question;
import ru.otus.trim.domain.QuizAction;

import java.util.*;

@Service
public class QuizRunServiceByConsole implements QuizRunService {
    final QuizDao quizDao;
    final String locale;
    final MessageSource msg;

    public QuizRunServiceByConsole(QuizConfig cfg, MessageSource msg, QuizDao dao){
        this.quizDao = dao;
        this.locale = cfg.getLocale();
        this.msg = msg;
        //this.quizActionDao = quizActionDao;
    }
    @Override
    public QuizAction runQuiz() {
        QuizAction quizAction = quizDao.getQuiz().createQuizAction();
        runQuiz(quizAction);
        System.out.println(quizAction.isComplete() + " " + quizAction.getRightAnsweredQuestionsCount());
        return quizAction;
    }

    private void runQuiz (QuizAction quizAction){
        quizAction.quiz.getQuestions().forEach(t -> quizAction.setAnswers(t, runQuestion(t)));
    }

    private List<Answer> outQuestion (Question question){
        System.out.println(question.question + " ?");
        int count = 0;
        List<Answer> answers = question.getAnswers();
        for (Answer a : answers){
            System.out.println((++count)+ ". " + a.answerText);
        }
        return answers;
    }

    private String [] readAnswers (){
        System.out.print(msg.getMessage("quiz.enter.question", new String [] {}, Locale.forLanguageTag(locale)) + ": ");
        Scanner scaner = new Scanner(System.in);
        String answer = scaner.nextLine();
        return answer.split(" ");
    }

    private HashSet<Answer> getAnswers (List<Answer> answers, String [] tokens){
        HashSet<Answer> result = new HashSet<>(tokens.length);
        for (String a : tokens){
            int num = Integer.parseInt(a);
            if (num >= 1 && num <= answers.size()) result.add(answers.get(num - 1));
        }
        return result;

    }

    private Set<Answer> runQuestion (Question question){
        List<Answer> answers = outQuestion (question);
        String [] tokens = readAnswers();
        return getAnswers (answers, tokens);
    }
}
