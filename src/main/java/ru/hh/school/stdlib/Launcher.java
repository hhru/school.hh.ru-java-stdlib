package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Launcher {
  public static void main(String[] args) throws IOException {
    String host;
    int port;
    try {
      if (args.length == 0) {
        host = "127.0.0.1";
        port = 9129;
      } else if (args.length == 2) {
        host = args[0];
        port = Integer.parseInt(args[1]);
      } else {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      System.err.printf("Usage: %s [host port]%n", args[0]);
      for (String s: args) System.out.println(s);
      e.printStackTrace();
      System.exit(1);
      return; // попробуйте закомментировать этот return
    }
    InetSocketAddress addr = InetSocketAddress.createUnresolved(host, port);
    new Server(addr).run();
  }
}
