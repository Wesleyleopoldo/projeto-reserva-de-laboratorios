package com.labssyntech.labsSyntech;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.labssyntech.labsSyntech.exception.InvalidArgumentException;
import com.labssyntech.labsSyntech.utils.UtilsDate;

@SpringBootTest
class LabsSyntechApplicationTests {

	@Test
	void contextLoads() {
		
	}

	@Test
	void testFormatterDate(){
		LocalDate date = LocalDate.now();
		String dateString = UtilsDate.formatterDate(date);
		assertEquals("Sexta-feira", dateString);
	}

	@Test
	void testFormatterDayString() {
		String day = UtilsDate.formatterDayString("sEgUndA-fEiRa");
		assertEquals("Segunda-feira", day, "Dia falha ao formatar ".concat(day));
	}

	@Test
	void testValidationDaysInWeek() {
		List<String> daysList = new ArrayList<>();
		List<String> expectedDaysList = new ArrayList<>();

		daysList.add("Segunda-feira");
		daysList.add("TERça-feIra");
		daysList.add("qUarTa-feira");

		expectedDaysList.add("Segunda-feira");
		expectedDaysList.add("Terça-feira");
		expectedDaysList.add("Quarta-feira");

		List<String> test = UtilsDate.validationDaysInWeek(daysList);

		assertEquals(expectedDaysList, test, "As Listas não são iguais!!!");
	}

	@Test
	void testInvalidArgumentException(){
		List<String> daysList = new ArrayList<>();

		daysList.add("Segunda-feira");
		daysList.add("TERça-feIra");
		daysList.add("qUarTa-feira");
		assertEquals(InvalidArgumentException.class, UtilsDate.validationDaysInWeek(daysList), "Exceção não lançada");
	}
}
