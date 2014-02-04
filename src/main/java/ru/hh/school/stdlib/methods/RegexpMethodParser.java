package ru.hh.school.stdlib.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбирает строку запроса с помощью регулярного выражения.
 */
public class RegexpMethodParser implements MethodParser {

	private final Pattern pattern;

	public RegexpMethodParser(String regexp) {
		this.pattern = Pattern.compile(regexp);
	}

	@Override
	public boolean tryParse(String line) {
		return this.pattern.matcher(line).matches();
	}

	@Override
	public String[] parse(String line) {
		Matcher matcher = this.pattern.matcher(line);
		matcher.find();

		String[] groups = new String[matcher.groupCount()];
		for (int i = 1; i <= matcher.groupCount(); i++) {
			groups[i - 1] = matcher.group(i);
		}

		return groups;
	}
}
