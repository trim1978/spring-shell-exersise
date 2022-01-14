package ru.otus.trim.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.Student;

import java.util.Locale;
import java.util.Scanner;

@Service
public class StudentRequestServiceByInput implements StudentRequestService{
    private final MessageSource msg;
    private final Locale locale;

    public StudentRequestServiceByInput(QuizConfig cfg, MessageSource msg) {

        this.msg = msg;
        this.locale = Locale.forLanguageTag(cfg.getLocale());
    }

    @Override
    public Student requestStudent() {
        Scanner scaner = new Scanner(System.in);
        System.out.print(msg.getMessage(
                "quiz.enter.firstname",
                null,
                locale
        ) + ": ");

        String firstName = scaner.nextLine();
        System.out.print(msg.getMessage(
                "quiz.enter.lastname",
                null,
                locale
        ) + ": ");

        String lastName = scaner.nextLine();
        System.out.println(msg.getMessage(
                "quiz.enter.welcome",
                new String[] {firstName, lastName},
                locale
        ) + " !");
        return new Student(firstName, lastName);
    }
}
