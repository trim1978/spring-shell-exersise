package ru.otus.trim.shell;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.trim.events.QuizActionPublisher;
import ru.otus.trim.service.IOService;
import ru.otus.trim.service.OpenedConsoleIOService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Тест команд shell")
@ComponentScan(basePackages = "ru.otus.trim")
@SpringBootTest
class SpringShellExersiseApplicationTests {


	private static final String COMMAND_ENTER = "enter";
	private static final String COMMAND_START = "start";
	private static final String COMMAND_WHO = "who";
	private static final String COMMAND_SET= "set_enough";

	//private static final String COMMAND_ENOUGH = "set_enough";

	@MockBean
	private QuizActionPublisher eventsPublisher;

	@MockBean
	private IOService ioService;

	@Autowired
	private Shell shell;

	@DisplayName("должен вернуть ошибку при старте опроса без представления пользователя")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldReturnExceptionWherTryStartWithoutEnter() {
		Object res = shell.evaluate(() -> COMMAND_START);
		assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
	}

	@DisplayName("должен возвращать < none > при команде who до представления")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldReturnNoneBeforeLoginCommandEvaluated() {
		String res = (String) shell.evaluate(() -> COMMAND_WHO);
		assertThat(res).isEqualTo("< none >");
	}

	@DisplayName("должен возвращать допустимое количество правильных ответов, после их задания")
	@Test
	void shouldReturnEnoughWhenEnoughWasSetted() {
		for (int enough = 0; enough < 10; enough++) {
			final String arg = COMMAND_SET + " " + enough;
			String res = (String) shell.evaluate(() -> arg);
			assertThat(res).isEqualTo(String.format("enough is %d", enough));
		}
	}

	@DisplayName("должен возвращать приветствие для каманды задания студента")
	@Test
	void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
		when(ioService.readString()).thenReturn("Ivan");
		when(ioService.readString()).thenReturn("Ivanov");
		String res1 = (String) shell.evaluate(() -> COMMAND_ENTER);
		assertThat(res1).isEqualTo("Student is ready");
	}
}
