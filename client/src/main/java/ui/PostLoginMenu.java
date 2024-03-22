package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import exception.ResponseException;
import model.GameData;
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
                    \t5. Join Game -- join an existing game as white, black, or observer
                                        
                    -->\t""";
//    	6. Join Observer -- join an existing game as a bystander

    private static final Scanner scanner = new Scanner(System.in);
    private final ServerFacade server = new ServerFacade();
    private final PreLoginMenu preLoginMenu;
    static ChessPiece[][] board = new ChessBoard().getBoard();

    private static final Map<Integer, String> NumToGameName = new HashMap<>();
    private static final Map<Integer, Integer> NumToGameID = new HashMap<>();

    public PostLoginMenu(PreLoginMenu preLoginMenu) {
        this.preLoginMenu = preLoginMenu;
    }

    public void run(String username, String authToken) {
        String gameName;
        String teamColor;
        int gameID;
        System.out.print(postLoginMenu);
        String input = scanner.nextLine().toLowerCase();

        switch (input) {
            case "1":
            case "help":
                System.out.print(postHelpMenu);
                run(username, authToken);
                break;
            case "2":
            case "logout":
                try {
                    server.logout(username, authToken);
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
//                    CreateGameResponse response =
                    server.createGame(gameName, authToken);
//                    gameID = response.gameID;
                    System.out.println("New game, '" + gameName + "' created successfully.\n");
                    run(username, authToken);
                    break;
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    break;
                }

            case "4":
            case "list games":
                try {
                    ListGamesResponse response = server.listGames(authToken);
                    var games = response.games();
                    System.out.print(SET_TEXT_BOLD + "Existing games: " + RESET_TEXT_BOLD_FAINT + "\n");
                    System.out.println("\t" + SET_TEXT_UNDERLINE + "ID\t\tGame Name" + RESET_TEXT_UNDERLINE);
                    int i = 1;
                    for (GameData game : games) {
                        NumToGameName.put(i, game.gameName());
                        NumToGameID.put(i, game.gameID());
                        System.out.println("\t" + i + "\t\t" + game.gameName());
                        i++;
                    }
                    System.out.print("\n");
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    break;
                }

                run(username, authToken);
                break;
            case "5":
            case "join game":
                System.out.print("Enter a game ID number: ");
                int gameNum = scanner.nextInt();
                gameID = NumToGameID.get(gameNum);
                scanner.nextLine();
                System.out.print("Join as 'White', 'Black', or 'observer'?\n");
                teamColor = scanner.nextLine();
                try {
                    server.joinGame(authToken, teamColor, gameID);
                    if (teamColor.equalsIgnoreCase("white") || teamColor.equalsIgnoreCase("black")) {
                        System.out.print("Joining '" + NumToGameName.get(gameNum) + "' as " + teamColor + ".\n");
                    } else {
                        System.out.print("Joining game '" + NumToGameName.get(gameNum) + "' as an observer.\n");
                    }
                    BoardPrinter.printUI(board);
                    run(username, authToken);
                    break;
                } catch (ResponseException e) {
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
                run(username, authToken);
        }
    }
}
