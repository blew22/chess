package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import exception.ResponseException;
import model.GameData;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import server.ServerFacade;

import java.util.*;

import static ui.EscapeSequences.*;

public class PostLoginMenu {

    private static final String postLoginMenu =
            SET_TEXT_BOLD + "Main Menu:" + RESET_TEXT_BOLD_FAINT + "\n" + """
                    \t1. Help
                    \t2. Logout
                    \t3. Create Game
                    \t4. List Games
                    \t5. Join Game
                                        
                    -->\t""";
//                        	6. Join Observer


    private static final String postHelpMenu =
            SET_TEXT_BOLD + "Help Menu:" + RESET_TEXT_BOLD_FAINT + "\n" + """
                    \t1. Help -- displays this information
                    \t2. Logout -- logs out current user
                    \t3. Create Game -- name and create a new chess game
                    \t4. List Games -- list all existing chess games
                    \t5. Join Game -- join an existing game as either white or black player
                                        
                    -->\t""";
//    	6. Join Observer -- join an existing game as a bystander

    private static final Scanner scanner = new Scanner(System.in);
    private String input;
    private String auth;
    private ServerFacade server = new ServerFacade();
    private PreLoginMenu preLoginMenu;
    static ChessPiece[][] board = new ChessBoard().getBoard();

    private static Map<Integer, String> IDtoGameName = new HashMap<>();

    public PostLoginMenu(PreLoginMenu preLoginMenu, ServerFacade server) {
        this.preLoginMenu = preLoginMenu;
    }

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
                try {
                    server.logout(username, auth);
                    System.out.println("Logging out...\n");
                    preLoginMenu.run();
                    break;
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    break;
                }

            case "3":
            case "create game":
                System.out.print("Enter a name for the new game: ");
                gameName = scanner.nextLine();
                try {
                    CreateGameResponse response = (CreateGameResponse) server.createGame(gameName, auth);
                    gameID = response.gameID;
                    System.out.println("New game, " + gameName + " created with gameID " + gameID + " .\n");
                    run(username, auth);
                    break;
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    break;
                }

            case "4":
            case "list games":
                try {
                    ListGamesResponse response = (ListGamesResponse) server.listGames(auth);
                    var games = response.games();
                    System.out.print(SET_TEXT_BOLD + "Existing games: " + RESET_TEXT_BOLD_FAINT + "\n");
                    System.out.println("\t" + SET_TEXT_UNDERLINE + "ID\t\tGame Name" + RESET_TEXT_UNDERLINE);
                    for (GameData game : games) {
                        IDtoGameName.put(game.gameID(), game.gameName());
                        System.out.println("\t" + game.gameID() + "\t" + game.gameName());
                    }
                    System.out.print("\n");
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    break;
                }

                run(username, auth);
                break;
            case "5":
            case "join game":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Join as 'White', 'Black', or 'observer'?\n");
                teamColor = scanner.nextLine();
                try {
                    server.joinGame(auth, teamColor, gameID);
                    if (teamColor.equalsIgnoreCase("white") || teamColor.equalsIgnoreCase("black")) {
                        System.out.print("Joining '" + IDtoGameName.get(gameID) + "' as " + teamColor + ".\n");
                    } else {
                        System.out.print("Joining game '" + IDtoGameName.get(gameID) + "' as an observer.\n");
                    }
                    BoardPrinter.printUI(board);
                    run(username, auth);
                    break;
                } catch (ResponseException e){
                    System.out.print(e.getMessage());
                    break;
                }
            /*case "6":
            case "join observer":
                System.out.print("Enter a game ID number: ");
                gameID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Joining game " + gameID + ".");
                BoardPrinter.printUI(board);
                run(username, auth);
                break;*/
            default:
                System.out.print("invalid input.\ntry again.");
                run(username, auth);
        }
    }
}
