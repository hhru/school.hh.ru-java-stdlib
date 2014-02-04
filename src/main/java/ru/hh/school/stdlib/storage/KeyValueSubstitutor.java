package ru.hh.school.stdlib.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация системы хранения данных с шаблонизацией.
 */
public class KeyValueSubstitutor implements Substitutor3000 {
	/**
	 * Значение, на которое будут заменяться ключи при возникновении
	 * бесконечной рекурсии.
	 */
	public static final String recursionPlaceholder = "<~>";

	private HashMap<String, String> data = new HashMap<String, String>();
	private Pattern templatePattern = Pattern.compile("\\$\\{\\s*([A-Za-z_\\$]+[A-Za-z_\\$0-9]*)\\s*\\}");
	private HashSet<String> visitedKeys = new HashSet<String>();

	public void put(String key, String value) {
		data.put(key, value);
	}

	public String get(String key) {
		String tpl_value = data.get(key);
		if (tpl_value == null)
			return "";

		if (this.visitedKeys.contains(key))
			return KeyValueSubstitutor.recursionPlaceholder;

		this.visitedKeys.add(key);

		Matcher matcher = this.templatePattern.matcher(tpl_value);
		StringBuffer value = new StringBuffer();

		while (matcher.find()) {
			String keyToReplace = matcher.group(1);
			matcher.appendReplacement(value, this.get(keyToReplace));
		}

		this.visitedKeys.remove(key);
		matcher.appendTail(value);

		return value.toString();
	}
}
