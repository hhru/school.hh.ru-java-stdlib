package ru.hh.school.stdlib.methods;

import java.util.regex.Pattern;

/**
 * Реализует разбор запроса SET SLEEP.
 */
public class SetSleepMethod extends RegexpMethodParser {
	private Pattern regExp = Pattern.compile("\\s*SET\\s+SLEEP\\s+(\\d+)\\s*");

	/**
	 * Возвращает название запроса.
	 * @return Название запроса.
	 */
	public String methodName() {
		return "SET SLEEP";
	}

	/**
	 * Возвращает регулярное выражение для разбора запроса.
	 * @return Регулярное выражение.
	 */
	@Override
	protected Pattern getPattern() {
		return this.regExp;
	}
}
