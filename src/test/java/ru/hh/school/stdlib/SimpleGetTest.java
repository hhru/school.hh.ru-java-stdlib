package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class SimpleGetTest extends BaseFunctionalTest {
  @Test
  public void simpleGet() throws IOException {
    Socket s = connect();

    Writer out = new PrintWriter(s.getOutputStream());
    out.append("GET k1\n").flush();
    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("", in.readLine());

    out.append("PUT mo monday\n").flush();

    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("GET mo\n").flush();

    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("monday ", in.readLine());

    out.append("PUT tu tuesday\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT wed wednesday\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT days1 ${mo} ${tu} ${wed}\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT th thursday\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT fr friday\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT days2 ${th} ${fr}\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("PUT workdays ${days1} ${days2}\n").flush();
    Assert.assertEquals("OK", in.readLine());
    Assert.assertEquals("connection closed", in.readLine());

    out.append("GET workdays\n").flush();
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("monday tuesday wednesday thursday friday ", in.readLine());

    s.close();
  }

  @Test
  public void testSleep() throws IOException {
      long timePeriod = 10000;
      Socket t = connect();
      Writer out = new PrintWriter(t.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(t.getInputStream()));

      out.append("SET SLEEP " + timePeriod + "\n").flush();
      long start = System.currentTimeMillis();
      out.append("GET k1\n").flush();
      Assert.assertEquals("", in.readLine());
      Assert.assertEquals("VALUE", in.readLine());
      Assert.assertEquals("", in.readLine());
      long end = System.currentTimeMillis();
      Assert.assertTrue(timePeriod < (end - start));
      Assert.assertTrue((end - start) < (timePeriod + 50));
  }
}
