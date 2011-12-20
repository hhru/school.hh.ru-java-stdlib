package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.Socket;


public class GetPutTest extends BaseFunctionalTest{
    @Test
    public void getPut() throws IOException {
        Socket s = connect();

        Writer out = new PrintWriter(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT k1 one\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("GET k1\n").flush();
        Assert.assertEquals("VALUE", in.readLine());
        Assert.assertEquals("one", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT keys ${k1} ${k2}\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("GET keys\n").flush();
        Assert.assertEquals("VALUE", in.readLine());
        Assert.assertEquals("one ", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT k2 two\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("GET keys\n").flush();
        Assert.assertEquals("VALUE", in.readLine());
        Assert.assertEquals("one two", in.readLine());

        s.close();
    }

}
