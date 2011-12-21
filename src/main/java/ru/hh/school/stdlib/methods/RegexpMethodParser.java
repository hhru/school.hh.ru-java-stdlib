package ru.hh.school.stdlib.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбирает строку запроса с помощью регулярного выражения.
 */
public abstract class RegexpMethodParser implements MethodParser {
	/**
	 * Возвращает регулярное выражение для разбора запроса.
	 * @return Регулярное выражение.
	 */
	protected abstract Pattern getPattern();

	/**
	 * Проверяет строку запроса соответствующим регулярным выражением.
	 * @param line Строка с текстом запроса.
	 * @return True в том случае, если регулярное выражение допускает входную
	 * строку запроса.
	 */
	@Override
	public boolean tryParse(String line) {
		return this.getPattern().matcher(line).matches();
	}

	/**
	 * Разбирает строку запроса с помощью регулярного выражения.
	 * @param line Строка с текстом запроса.
	 * @return Массив параметров запроса, полученный в результате
	 * разбора запроса регулярным выражением.
	 */
	@Override
	public String[] parse(String line) {
		Matcher matcher = this.getPattern().matcher(line);
		matcher.find();

		String[] groups = new String[matcher.groupCount()];
		for (int i = 1; i <= matcher.groupCount(); i++) {
			groups[i - 1] = matcher.group(i);
		}

		return groups;
	}
}
