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
      } else if (args.length == 3) {
        host = args[1];
        port = Integer.parseInt(args[2]);
      } else {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      System.err.printf("Usage: %s [host port]%n", args[0]);
      System.exit(1);
      return;
    }
    InetSocketAddress addr = InetSocketAddress.createUnresolved(host, port);

    Server server = new Server(addr);
    new Thread(server).start();
    //Launching server for 20 minutes
    try {
        Thread.sleep(12000 * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Stopping Server");
    server.stop();
  }
}
