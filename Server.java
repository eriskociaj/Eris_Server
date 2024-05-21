import java.io.*;
import java.net.ServerSocket; // Imports the ServerSocket class for creating a server that can listen for and accept client connections
import java.net.Socket; // Imports the Socket class for creating client sockets and server sockets to communicate over the network


public class Server {
    public static void main(String[] args) {
        int port = 8080; // Define the port number the server will listen on
        
        // Try-with-resources to automatically manage the server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("\n\nServer started. Listening on port " + port);

            // Infinite loop to keep the server running
            while (true) {
                // Accept a connection from a client
                try (Socket clientSocket = serverSocket.accept();
                     // Create a PrintWriter to send data to the client
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     // Create a BufferedReader to read data from the client
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String inputLine;
                    // Read data from the client until the connection is closed
                    while ((inputLine = in.readLine()) != null) {
                        // Process the received data and calculate the result
                        String response = processMathExpression(inputLine);
                        // Send the response back to the client
                        out.println(response);
                    }
                } catch (IOException e) {
                    // Handle exceptions related to IO and connections
                    System.out.println("\n\nException caught when trying to listen on port "
                            + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to opening the server socket
            System.out.println("\n\nCould not listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    // Method to process the math expression received from the client
    private static String processMathExpression(String expression) {
        // Split the received expression into parts based on spaces
        String[] parts = expression.split(" ");
        // Check if the expression is in the correct format (number operator number)
        if (parts.length != 3) {
            return "Invalid input";
        }

        try {
            // Parse the numbers from the expression
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[2]);
            // Get the operator from the expression
            String operator = parts[1];
            int result;

            // Switch based on the operator to perform the calculation
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
                    // Check for division by zero
                    if (num2 == 0) {
                        return "Error: Division by zero";
                    }
                    result = num1 / num2;
                    return "The quotient of " + num1 + "/" + num2 + " is " + result;
                default:
                    // Handle unsupported operators
                    return "Unsupported operation";
            }
        } catch (NumberFormatException e) {
            // Handle incorrect number formats
            return "Invalid number format";
        }
    }
}