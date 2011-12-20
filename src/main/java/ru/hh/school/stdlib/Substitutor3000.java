package ru.hh.school.stdlib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substitutor3000 {

    private long sleepTime;
    private HashMap<String, String> storage;
    private HashSet<String> inProgress = new HashSet<String>();
    private final String recursionPanicString = "Panic!!! Infinite recursion!!!";

    public Substitutor3000() {
        storage = new HashMap<String, String>();
    }

    public synchronized void put(String key, String value) {
    storage.put(key, value);
  }

  public String get(String key) {
    if (inProgress.contains(key)) {
        inProgress.clear();
        return recursionPanicString;
    }
    String out = storage.get(key);
    if(out==null) return "";
    inProgress.add(key);
    Pattern pattern = Pattern.compile("\\p{Sc}\\{(\\S+)\\}");
      Matcher matcher = pattern.matcher(out);
      while (matcher.find()){
          String part = get(matcher.group(1));
          if(part==null){
             part="";
          }
          else if (part.equals(recursionPanicString)) return part;
          out = out.replace(matcher.group(0),part);
      }
      inProgress.remove(key);
      return out;
  }
    public long getSleepTime() {
        return sleepTime;
    }
    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
