package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import exception.ResponseException;
import server.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_BOLD_FAINT;
import static ui.EscapeSequences.SET_TEXT_BOLD;

public class PostLoginMenu {

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

    private static final Scanner scanner = new Scanner(System.in);
    private String input;
    private String auth;
    private ServerFacade server = new ServerFacade();
    private PreLoginMenu preLoginMenu;
    static ChessPiece[][] board = new ChessBoard().getBoard();
    public PostLoginMenu(PreLoginMenu preLoginMenu, ServerFacade server){
        this.preLoginMenu = preLoginMenu;}

    public void run(String username, String authToken) {
        this.auth = authToken;
        String gameName;
        String teamColor;
        int gameID;
        System.out.print(postLoginMenu);
        input = scanner.nextLine().toLowerCase();

        switch (input) {
            case "1":
            case "help":
                System.out.print(postHelpMenu);
                run(username, auth);
                break;
            case "2":
            case "logout":
                try{
                    server.logout(username, auth);
                    System.out.println("Logging out...\n");
                    preLoginMenu.run();
                    break;
                } catch (ResponseException e){
                    System.out.println(e.getMessage());
                    break;
                }

            case "3":
            case "create game":
                System.out.print("Enter a name of the new game: ");
                gameName = scanner.nextLine();
                System.out.println("New game, " + gameName + " created with gameID xxxx.\n");
                run(username, auth);
                break;
            case "4":
            case "list games":
                System.out.print(SET_TEXT_BOLD + "Existing games: " + RESET_TEXT_BOLD_FAINT + "\n");
                System.out.print("""
                        \t1. game1
                        \t2. game2
                        \t3. game3
                        """);
                run(username, auth);
                break;
            case "5":
            case "join game":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("White or Black?\n");
                teamColor = scanner.nextLine();
                System.out.print("Joining game " + gameID + " as " + teamColor + ".\n");
                BoardPrinter.printUI(board);
                run(username, auth);
                break;
            case "6":
            case "join observer":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Joining game " + gameID + ".");
                BoardPrinter.printUI(board);
                run(username, auth);
                break;
            default:
                System.out.print("invalid input.\ntry again.");
                run(username, auth);
        }
    }
}
