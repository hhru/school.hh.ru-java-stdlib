package ru.hh.school.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ServerThread implements Runnable{
    private final static String SUCCESS_REQUEST = "OK\nconnection closed";
    private final static String WRONG_REQUEST   = "ERROR\nWrong request. Please try again.";
    private final static String RECURSIVE_ERROR = "ERROR\nRecursive link detected";

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
        String[] input = inputLine.split("\\s");

        if (input[0].equals("GET")) {
            sleep();
            String key = input[1];
            String value;
            try {
                value = substitutor.get(key);
            } catch (RecursiveException e) {
                return RECURSIVE_ERROR;
            }
            return "VALUE\n" + value;
        }
        else if (input[0].equals("PUT")) {
            sleep();
            String key = input[1];
            String value = "";
            for (int i = 2; i < input.length; i++)
                value += input[i];
            substitutor.put(key, value);
            return SUCCESS_REQUEST;
        }
        else if (input[0].equals("SET")) {
            if (input[1].equals("SLEEP")) {
                server.setSleepTime(Integer.parseInt(input[2]));
                return SUCCESS_REQUEST;
            }
            else
                return WRONG_REQUEST;
        }
        else if (input[0].equals("EXIT")) {
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