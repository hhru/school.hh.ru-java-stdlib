package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket s;
  private Substitutor3000 sbst;
  public Server(InetSocketAddress addr){
    try{
        s = new ServerSocket(addr.getPort());
        sbst = new Substitutor3000();
    }
    catch(IOException e){
        System.err.println("There's an I/O problem: ");
        e.printStackTrace();
        System.exit(1);
    }
  }

  public void run() throws IOException {
      System.out.printf("Server started on port %d%n", this.getPort());
      while(true){
          Socket  incoming = s.accept();
          Runnable r = new ClientHandler(incoming, sbst);
          Thread t = new Thread(r);
          t.start();
      }
  }

  public int getPort() {
    return s.getLocalPort();
  }
}
