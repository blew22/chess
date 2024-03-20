import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
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


    public static void main(String[] args) {
        //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client ♕\n");
        System.out.print(preLoginMenu);
        preLoginMenu();


        /*chess.ChessPiece[][] board = new ChessBoard().getBoard();
        BoardPrinter.printUI(board);*/

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
        input = scanner.nextLine().toLowerCase();
        switch(input){
            case"1":
            case"help":

        }
    }
}