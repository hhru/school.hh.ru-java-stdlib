package ru.hh.school.stdlib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {

    private static Map<String,String> map = Collections.synchronizedMap(new HashMap<String, String>());

    public void put(String key, String value) {
        synchronized (this) {
            map.put(key, value);
        }
    }

    public String get(String key) {
        synchronized (this) {
            String value = map.get(key);
            if (value == null)
                return "";
            String regex = "\\$\\{(\\w+)\\}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);

            while (m.find()) {
                value = value.replaceFirst(regex, get(m.group(1)));
                m = p.matcher(value);
            }

            //Adding whitespace after retrieved words
            String regex2 = "\\w+";
            Pattern p2 = Pattern.compile(regex2);
            Matcher m2 = p2.matcher(value);
            if (m2.matches())
                value += " ";

            return value;
        }
    }

}
