import chess.ChessBoard;
import ui.BoardPrinter;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    static chess.ChessPiece[][] board = new ChessBoard().getBoard();

    private static final String preLoginMenu =
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

    private static final String postLoginMenu =
            SET_TEXT_BOLD + "Main Menu:" + RESET_TEXT_BOLD_FAINT + "\n" + """
                    \t1. Help
                    \t2. Logout
                    \t3. Create Game
                    \t4. List Games
                    \t5. Join Game
                    \t6. Join Observer
                    
                    -->\t""";

    private static final String postHelpMenu =
            SET_TEXT_BOLD + "Help Menu:" + RESET_TEXT_BOLD_FAINT + "\n" + """
                    \t1. Help -- displays this information
                    \t2. Logout -- logs out current user
                    \t3. Create Game -- name and create a new chess game
                    \t4. List Games -- list all existing chess games
                    \t5. Join Game -- join an existing game as either white or black player
                    \t6. Join Observer -- join an existing game as a bystander
                    
                    -->\t""";


    public static void main(String[] args) {
        //System.out.print(SET_BG_COLOR_WHITE);
        System.out.println("♕ 240 Chess Client ♕\n");
        //System.out.print("\u001b[15m");

        System.out.print(preLoginMenu);
        preLoginMenu();




    }

    private static void preLoginMenu(){
        String input;
        input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "1":
            case "register":
                System.out.println("registering new user...");
                String username = "";
                String password = "";
                System.out.print("Enter your username: ");
                username = scanner.nextLine();
                System.out.print("Enter your password: ");
                password = scanner.nextLine();
                System.out.print("Welcome, " + username + ".\nYou are now registered and logged in!\n");
                System.out.print(postLoginMenu);
                postLoginMenu();
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
                System.out.print("Welcome back, " + name + ".\nYou're logged in!\n");
                System.out.print(postLoginMenu);
                postLoginMenu();
                break;
            case "3":
            case "quit":
                System.out.print("goodbye.");
                System.exit(0);
                break;
            case "4":
            case "help":
                System.out.print(preHelpMenu);
                preLoginMenu();
                break;
            default:
                System.out.print("invalid input, try again\n\n");
                System.out.print(preLoginMenu);
                preLoginMenu();
        }
    }

    private static void postLoginMenu(){
        String input;
        String gameName;
        String teamColor;
        int gameID;
        input = scanner.nextLine().toLowerCase();
        switch(input){
            case"1":
            case"help":
                System.out.print(postHelpMenu);
                postLoginMenu();
                break;
            case"2":
            case"logout":
                System.out.println("Logging out...\n");
                System.out.print(preLoginMenu);
                preLoginMenu();
                break;
            case"3":
            case"create game":
                System.out.print("Enter a name of the new game: ");
                gameName = scanner.nextLine();
                System.out.println("New game, " + gameName + " created with gameID xxxx.\n");
                System.out.print(postLoginMenu);
                postLoginMenu();
                break;
            case"4":
            case"list games":
                System.out.print(SET_TEXT_BOLD + "Existing games: " + RESET_TEXT_BOLD_FAINT + "\n");
                System.out.print("""
                        \t1. game1
                        \t2. game2
                        \t3. game3
                        """);
                System.out.print(postLoginMenu);
                postLoginMenu();
                break;
            case"5":
            case"join game":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("White or Black?\n");
                teamColor = scanner.nextLine();
                System.out.print("Joining game " + gameID + " as " + teamColor + ".\n");
                BoardPrinter.printUI(board);
                System.out.print("\n" + postLoginMenu);
                postLoginMenu();
                break;
            case"6":
            case"join observer":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Joining game " + gameID + ".");
                BoardPrinter.printUI(board);
                System.out.print("\n" + postLoginMenu);
                postLoginMenu();
                break;
            default:
                System.out.print("invalid input.\ntry again.");
                System.out.print(postLoginMenu);
                postLoginMenu();
        }
    }
}