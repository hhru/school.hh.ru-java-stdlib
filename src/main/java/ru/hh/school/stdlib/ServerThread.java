package ru.hh.school.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    protected Socket clientSocket = null;
    private final static String WRONG_REQUEST ="Wrond request. Please try again.";

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
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
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    private String processInput(String inputLine) {
        StringTokenizer tokenizer = new StringTokenizer(inputLine);
        String command = tokenizer.nextToken();
        if (command.equals("GET")) {

            String key = tokenizer.nextToken();
            return "VALUE\n" + Server.substitutor.get(key);
        }
        else if (command.equals("PUT")) {
            String key = tokenizer.nextToken();
            String value = "";
            while (tokenizer.hasMoreTokens()) {
                value += tokenizer.nextToken();
            }
            Server.substitutor.put(key, value);
            return "OK\nconnection closed";
        }
        else if (command.equals("SET")) {
            if (tokenizer.nextToken().equals("SLEEP")) {
                try {
                    Thread.sleep(Integer.parseInt(tokenizer.nextToken()));
                } catch (InterruptedException e) {
                    return "";
                }
            }
        }
        else if (command.equals("EXIT")) {
            return "EXITING";
        }
        else {
            return WRONG_REQUEST;
        }
        return "";
    }
}