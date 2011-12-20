package ru.hh.school.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

public class RecursiveGet extends BaseFunctionalTest {
    //This is not a test for infinite recursion protection.
    @Test
    public void recursiveGet() throws IOException {
        Socket s = connect();
        Writer out = new PrintWriter(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT keys2 ${k5} ${k2}\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT k4 two\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("PUT k5 k1 ${k4}\n").flush();
        Assert.assertEquals("OK", in.readLine());

        s = connect();
        out = new PrintWriter(s.getOutputStream());
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.append("GET keys2\n").flush();
        Assert.assertEquals("VALUE", in.readLine());
        Assert.assertEquals("k1 two two", in.readLine());

        s.close();
    }
}
