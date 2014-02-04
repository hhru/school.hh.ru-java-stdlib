package ru.hh.school.stdlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {
    private Map<String,String> map = Collections.synchronizedMap(new HashMap<String, String>());
    private ArrayList<String> keys = new ArrayList<String>();

    public synchronized void put(String key, String value) {
        map.put(key, value);
    }

    public synchronized String get(String key) throws RecursiveException {
        String regex = "\\$\\{(\\w+)\\}";
        String value = map.get(key);
        if (value == null)
            return "";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);

        if (keys.contains(value))
            throw new RecursiveException();
        keys.add(value);
        while (m.find()) {
            value = value.replaceFirst(regex, get(m.group(1)));
            m = p.matcher(value);
        }
        keys.remove(value);
        if (!value.contains(" "))
            value += " ";

        return value;
    }
}
