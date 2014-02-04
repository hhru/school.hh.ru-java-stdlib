package ru.hh.school.stdlib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {

    private HashMap<String, String> storage;

    public Substitutor3000() {
        storage = new HashMap<String, String>();
    }

    public synchronized void put(String key, String value) {
        storage.put(key, value);
    }

    public synchronized String get(String key, HashSet<String> inProgress) throws InfiniteRecursionException {
        if (inProgress == null)
            inProgress = new HashSet<String>();
        else if (inProgress.contains(key))
            throw new InfiniteRecursionException("Infinite recursion.");
        String out = storage.get(key);
        if (out == null) return "";
        inProgress.add(key);
        Pattern pattern = Pattern.compile("\\p{Sc}\\{(\\S+)\\}");
        Matcher matcher = pattern.matcher(out);
        while (matcher.find()) {
            String part = get(matcher.group(1), inProgress);
            if (part == null)
                part = "";
            out = out.replace(matcher.group(0), part);
        }
        inProgress.remove(key);
        return out;
    }
}
