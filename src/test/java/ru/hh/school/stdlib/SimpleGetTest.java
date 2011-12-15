package ru.hh.school.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

public class SimpleGetTest extends BaseFunctionalTest {
  @Test
  public void simpleGet() throws IOException {
    Socket s = connect();

    Writer out = new PrintWriter(s.getOutputStream());
    out.append("GET k1\n").flush();
    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    
    Assert.assertEquals("VALUE", in.readLine());
    Assert.assertEquals("", in.readLine());
    
    s.close();
  }
}
