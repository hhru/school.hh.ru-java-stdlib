package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class MultiConnection extends BaseFunctionalTest {

    @Test
    public void testMultiConnection() throws IOException {
        long timePeriod = 10000;

        Socket s1 = connect();
        Socket s2 = connect();

        Writer out1 = new PrintWriter(s1.getOutputStream());
        BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        Writer out2 = new PrintWriter(s2.getOutputStream());
        BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));

        out1.append("SET SLEEP " + timePeriod + "\n").flush();
        Assert.assertEquals("OK", in1.readLine());
        Assert.assertEquals("connection closed", in1.readLine());
        long start = System.currentTimeMillis();
        out1.append("GET k1\n").flush();
        out2.append("GET k1\n").flush();
        Assert.assertEquals("VALUE", in1.readLine());
        Assert.assertEquals("", in1.readLine());
        Assert.assertEquals("VALUE", in2.readLine());
        Assert.assertEquals("", in2.readLine());
        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (timePeriod * 2));
        System.out.println(end - start);
    }
}
