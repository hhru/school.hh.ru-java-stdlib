package ru.hh.school.stdlib.methods;

import java.util.regex.Pattern;

/**
 * Реализует разбор запроса PUT.
 */
public class PutMethod extends RegexpMethodParser {
	private Pattern regExp = Pattern.compile("\\s*PUT\\s+([A-Za-z_\\$]+[A-Za-z_\\$0-9]*)\\s(.*)");

	/**
	 * Возвращает название запроса.
	 * @return Название запроса.
	 */
	public String methodName() {
		return "PUT";
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