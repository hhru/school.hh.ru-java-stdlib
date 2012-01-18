package ru.hh.school.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class ServerThread implements Runnable{
    private final static String WRONG_REQUEST ="Wrong request. Please try again.";
    private final static String SUCCESS_REQUEST = "OK\nconnection closed";

    private Socket clientSocket;
    private Substitutor3000 substitutor;
    private Server server;
    
    public ServerThread(Socket clientSocket, Substitutor3000 substitutor, Server server) {
        this.clientSocket = clientSocket;
        this.substitutor = substitutor;
        this.server = server;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                outputLine = processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("EXIT"))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processInput(String inputLine) {
        StringTokenizer tokenizer = new StringTokenizer(inputLine);
        String command = tokenizer.nextToken();
        if (command.equals("GET")) {
            sleep();
            String key = tokenizer.nextToken();
            return "VALUE\n" + substitutor.get(key);
        }
        else if (command.equals("PUT")) {
            sleep();
            String key = tokenizer.nextToken();
            String value = "";
            while (tokenizer.hasMoreTokens()) {
                value += tokenizer.nextToken();
            }
            substitutor.put(key, value);
            return SUCCESS_REQUEST;
        }
        else if (command.equals("SET")) {
            if (tokenizer.nextToken().equals("SLEEP")) {
                server.setSleepTime(Integer.parseInt(tokenizer.nextToken()));
                return SUCCESS_REQUEST;
            }
            else
                return WRONG_REQUEST;
        }
        else if (command.equals("EXIT")) {
            return "EXIT";
        }
        else {
            return WRONG_REQUEST;
        }
    }

    private void sleep() {
        if (server.getSleepTime() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(server.getSleepTime());
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}