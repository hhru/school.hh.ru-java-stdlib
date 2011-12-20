package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

public class Substitutor3000Test {
    @Test
    public void replacement() {
        Substitutor3000 sbst = new Substitutor3000();
        sbst.put("k1", "one");
        sbst.put("k2", "two");
        sbst.put("keys", "1: ${k1}, 2: ${k2}");

        Assert.assertEquals("1: one, 2: two", sbst.get("keys"));

        sbst.put("a", "avril");
        sbst.put("keys2", "1: ${a}, 2: ${b}");
        Assert.assertEquals("1: avril, 2: ", sbst.get("keys2"));

        sbst.put("c3po", "human robot");
        sbst.put("r2d2", "wheel robot");
        sbst.put("keys3", "1: ${c3po}, 2: ${r2d2}");
        Assert.assertEquals("1: human robot, 2: wheel robot", sbst.get("keys3"));
    }

    @Test
    public void emptyReplacement() {
        Substitutor3000 sbst = new Substitutor3000();
        sbst.put("k", "bla-${inexistent}-bla");

        Assert.assertEquals("bla--bla", sbst.get("k"));
    }
}
