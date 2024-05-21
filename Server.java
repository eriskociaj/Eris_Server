import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080; // Port number the server will listen on
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        String response = processMathExpression(inputLine);
                        out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port "
                            + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    private static String processMathExpression(String expression) {
        String[] parts = expression.split(" ");
        if (parts.length != 3) {
            return "Invalid input";
        }

        try {
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[2]);
            String operator = parts[1];
            int result;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    return "The sum of " + num1 + "+" + num2 + " is " + result;
                case "-":
                    result = num1 - num2;
                    return "The difference of " + num1 + "-" + num2 + " is " + result;
                case "*":
                    result = num1 * num2;
                    return "The product of " + num1 + "*" + num2 + " is " + result;
                case "/":
                    if (num2 == 0) {
                        return "Error: Division by zero";
                    }
                    result = num1 / num2;
                    return "The quotient of " + num1 + "/" + num2 + " is " + result;
                default:
                    return "Unsupported operation";
            }
        } catch (NumberFormatException e) {
            return "Invalid number format";
        }
    }
}