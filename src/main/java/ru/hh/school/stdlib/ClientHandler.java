package ru.hh.school.stdlib;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {
    private Socket incoming;
    private final Substitutor3000 sbst;
    private final Server server;


    public ClientHandler(Socket i, Server srv, Substitutor3000 sb) {
        incoming = i;
        server = srv;
        sbst = sb;
    }

    @Override
    public void run() {
        try {
            try {
                Scanner in = new Scanner(incoming.getInputStream());
                PrintWriter out = new PrintWriter(incoming.getOutputStream());
                String line = in.nextLine().trim();
                String[] req = line.split(" ", 3);
                int len = req.length;
                if (req[0].equals("GET")) {
                    TimeUnit.MILLISECONDS.sleep(server.getSleepTime());
                    if (len >= 2) {
                        try {
                            String temp;
                            temp = sbst.get(req[1], null);
                            out.println("VALUE");
                            out.println(temp);
                        } catch (IOException e) {
                            out.println("Error: " + e.getMessage());
                        }
                    } else out.println("GET syntax: GET key");
                } else if ((req[0].equals("SET")) && (req[1].equals("SLEEP"))) {
                    if (len == 3) {
                        server.setSleepTime(Long.valueOf(req[2]));
                    } else out.println("SET syntax: SET SLEEP sleeptime");
                } else if (req[0].equals("PUT")) {
                    TimeUnit.MILLISECONDS.sleep(server.getSleepTime());
                    if (len == 3) {
                        sbst.put(req[1], req[2]);
                        out.println("OK");
                    } else out.println("PUT syntax: PUT key value");
                } else out.println("Unsupported request! We accept GET, PUT or SET SLEEP here!");
                out.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
