package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BaseFunctionalTest {
  private static Server server;

  protected BaseFunctionalTest() {
    synchronized (BaseFunctionalTest.class) {
      try {
        if (server == null) {
          server = new Server(new InetSocketAddress("127.0.0.1", 4444));
          new Thread(new Runnable() {
            public void run() {
              //try {
                server.run();
              //} catch (IOException e) {
                //throw new RuntimeException(e);
             // }
            }
          }).start();
          Thread.sleep(100);
          System.out.printf("Server started on port %d%n", server.getPort());
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  protected Socket connect() {
    try {
      return new Socket("127.0.0.1", server.getPort());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
