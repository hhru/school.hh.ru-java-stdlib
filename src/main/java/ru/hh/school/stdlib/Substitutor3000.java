package ru.hh.school.stdlib;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {

    private static Map<String,String> map = Collections.synchronizedMap(new HashMap<String, String>());
    private Set<String> keysSet = new HashSet<String>();
    private String ErrorCycRef = "Error. Cycle reference.";

    public String put(String key, String value) {
        synchronized (this) {

            String regex = "\\$\\{(\\w+)\\}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);

            keysSet.add(key);
            String chvalue;
            while (m.find()) {
                chvalue = m.group(1);
                if (get(chvalue).equals(ErrorCycRef))
                    return ErrorCycRef;
            }
            map.put(key, value);
            keysSet.remove(key);
            return "";
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
                //Cheching whether keys don't reference each other
                if (keysSet.contains(m.group(1))) {
                   return ErrorCycRef;
                }
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
