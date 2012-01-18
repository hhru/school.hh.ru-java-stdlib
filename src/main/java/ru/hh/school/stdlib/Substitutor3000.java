package ru.hh.school.stdlib;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {
    private Map<String,String> map = Collections.synchronizedMap(new HashMap<String, String>());

    public void put(String key, String value) {
        synchronized (this) {
            map.put(key, value);
        }
    }

    public String get(String key) {
        synchronized (this) {
            String regex = "\\$\\{(\\w+)\\}";
            String value = map.get(key);
            if (value == null)
                return "";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);

            while (m.find()) {
                value = value.replaceFirst(regex, get(m.group(1)));
                m = p.matcher(value);
            }

            if (!value.contains(" "))
                value += " ";

            return value;
        }
    }
}
