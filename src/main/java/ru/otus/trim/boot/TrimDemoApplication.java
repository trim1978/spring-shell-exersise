package ru.otus.trim.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.trim.config.QuizConfig;
import ru.otus.trim.domain.QuizAction;
import ru.otus.trim.domain.Student;
import ru.otus.trim.service.QuizCheckService;
import ru.otus.trim.service.QuizRunService;
import ru.otus.trim.service.QuizServiceExample;
import ru.otus.trim.service.StudentRequestService;

import java.util.Locale;

@SpringBootApplication
@ComponentScan(basePackages = "ru.otus.trim")
//@Configuration
public class TrimDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TrimDemoApplication.class, args);
		MessageSource msg = context.getBean(MessageSource.class);
		QuizConfig cfg = context.getBean(QuizConfig.class);

		QuizServiceExample service = context.getBean(QuizServiceExample.class);
		//System.out.println (service.outConfigData());
		//System.out.println ("=== "+Locale.forLanguageTag(cfg.getLocale()));


		StudentRequestService studentRequest = context.getBean(StudentRequestService.class);
		Student student = studentRequest.requestStudent();

		QuizRunService runService = context.getBean(QuizRunService.class);
		QuizAction quizAction = runService.runQuiz();

		QuizCheckService checkService = context.getBean(QuizCheckService.class);
		boolean result = checkService.checkQuizAction (quizAction);

		System.out.println(msg.getMessage(
				"quiz.result",
				new String[] {msg.getMessage(result ?
						"quiz.result.ok" : "quiz.result.fault",
						null,
						Locale.forLanguageTag(service.locale)
				), student.firstName, student.lastName},
				Locale.forLanguageTag(service.locale)
		));
		//Locale.

	}

}
