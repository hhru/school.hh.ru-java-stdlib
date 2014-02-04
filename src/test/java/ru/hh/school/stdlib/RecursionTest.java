package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class RecursionTest extends BaseFunctionalTest {
    //This one tests protection from infinite recursion
    @Test
    public void recursivePut() throws IOException {
        Socket s = connect();
        Writer out = new PrintWriter(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT keys3 ${l1} ${l2}\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT l2 two\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT l1 ${k4} ${keys3}\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("GET keys3\n").flush();
        Assert.assertEquals("Error: Infinite recursion.", in.readLine());

        s.close();
    }
}
