package ui;

import exception.ResponseException;
import model.User;
import responses.LoginResponse;
import server.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_BOLD_FAINT;
import static ui.EscapeSequences.SET_TEXT_BOLD;

public class PreLoginMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private ServerFacade server = null;

    public static final String preLoginMenu =
            SET_TEXT_BOLD + "Let's get started:" + RESET_TEXT_BOLD_FAINT + "\n" + """
                    \t1. Register
                    \t2. Login
                    \t3. Quit
                    \t4. Help
                    
                    -->\t""";

    private static final String preHelpMenu =
            SET_TEXT_BOLD + "Help Menu:" + RESET_TEXT_BOLD_FAINT + "\\n" + """
                                
                                            \t1. Register -- for new users
                                            \t2. Login -- for returning users
                                            \t3. Quit -- exits the program
                                            \t4. Help -- displays this information
                                            
                                            -->\t""";

    private final PostLoginMenu postLoginMenu = new PostLoginMenu(this, server);

    public PreLoginMenu(ServerFacade server){this.server = server;}
    public void run() {
        System.out.print(preLoginMenu);
        String input = scanner.nextLine();

        switch (input) {
            case "1":
            case "register":
                System.out.println("registering new user...");
                String username = "";
                String password = "";
                String email = "";
                System.out.print("Enter your username: ");
                username = scanner.nextLine();
                System.out.print("Enter your password: ");
                password = scanner.nextLine();
                System.out.print("Enter your email: ");
                email = scanner.nextLine();
                System.out.print("Welcome, " + username + ".\nYou are now registered and logged in!\n");
                postLoginMenu.run();
                break;
            case "2":
            case "login":
                String name = "";
                String pass = "";
                name = "";
                pass = "";
                System.out.print("Enter your username: ");
                name = scanner.nextLine();
                System.out.print("Enter your password: ");
                pass = scanner.nextLine();
                try{
                    LoginResponse response = (LoginResponse) server.login(name, pass);
                } catch (ResponseException e){
                    System.out.println(e.getMessage() + "\n");
                }


                System.out.print("Welcome back, " + name + ".\nYou're logged in!\n");
                postLoginMenu.run();
                break;
            case "3":
            case "quit":
                System.out.print("goodbye.");
                System.exit(0);
                break;
            case "4":
            case "help":
                System.out.print(preHelpMenu);
                run();
                break;
            default:
                System.out.print("invalid input, try again\n\n");
                run();
        }
    }
}
