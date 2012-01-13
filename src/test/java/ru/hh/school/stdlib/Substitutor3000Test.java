package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

public class Substitutor3000Test {
    @Test
    public void replacement() throws Exception {
        Substitutor3000 sbst = new Substitutor3000();
        sbst.put("k1", "one");
        sbst.put("k2", "two");
        sbst.put("keys", "1: ${k1}, 2: ${k2}");

        Assert.assertEquals("1: one, 2: two", sbst.get("keys", null));
    }

    @Test
    public void emptyReplacement() throws Exception {
        Substitutor3000 sbst = new Substitutor3000();
        sbst.put("k", "bla-${inexistent}-bla");

        Assert.assertEquals("bla--bla", sbst.get("k", null));
    }
}
