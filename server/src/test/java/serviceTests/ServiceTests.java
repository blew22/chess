package serviceTests;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import responses.LoginResponse;
import service.GameService;
import service.Service;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    User existingUser = new User("name_of_user", "drowssap", "em@il");
    static UserService userService = new UserService();
    static Service service = new Service();
    static GameService gameService = new GameService();

    @AfterEach
    public void clearAll() throws ResponseException {
        service.clear("");
    }

    @Test
    public void testRegisterUserSuccess() throws ResponseException {

        AuthData authData = new AuthData(existingUser.username());
        LoginResponse expected = new LoginResponse(authData);

        Object result = userService.registerUser(existingUser);

        assertEquals(expected, result, "register response returned different username than expected");
    }

    @Test
    public void registerExistingUserTest() throws ResponseException {
        UserService userService = new UserService();

        userService.registerUser(existingUser);
        assertThrows(ResponseException.class, () -> userService.registerUser(existingUser));
    }

    @Test
    public void loginSuccessTest() throws ResponseException{
        userService.registerUser(existingUser);
        var result = userService.loginUser(existingUser);

        LoginResponse expected = new LoginResponse(new AuthData(existingUser.username()));

        assertEquals(result, expected);
    }

    @Test
    public void loginBadPassTest() throws ResponseException{
        userService.registerUser(existingUser);
        User badPass = new User(existingUser.username(), "incorrect", existingUser.email());
        assertThrows(ResponseException.class, () -> userService.loginUser(badPass));
    }

    @Test
    public void logoutNoException() throws ResponseException{
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        assertDoesNotThrow(() -> {
            userService.logoutUser(auth);
        });
    }

    @Test
    public void logoutNonexistingUser() throws ResponseException {
        assertFalse(userService.logoutUser(""));
    }

    @Test
    public void createGameSuccess() throws ResponseException {
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        CreateGameRequest gameRequest = new CreateGameRequest("newGame", auth);
        CreateGameResponse result = (CreateGameResponse) gameService.createGame(gameRequest);

        assertNotNull(result.gameID);
    }

    @Test
    public void createGameUnauthorized(){
        CreateGameRequest gameRequest = new CreateGameRequest("game", "badAuth");
        assertThrows(ResponseException.class, () -> gameService.createGame(gameRequest));
    }

    @Test
    public void joinNonexistentGame(){
        JoinGameRequest joinGameRequest = new JoinGameRequest("auth", ChessGame.TeamColor.WHITE, 1234);
        assertThrows(ResponseException.class, () -> gameService.joinGame(joinGameRequest));
    }

    @Test
    public void joinGameSuccess() throws ResponseException {
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        CreateGameRequest gameRequest = new CreateGameRequest("newGame", auth);
        CreateGameResponse result = (CreateGameResponse) gameService.createGame(gameRequest);

        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, ChessGame.TeamColor.WHITE, result.gameID);

        assertDoesNotThrow(() -> {
            gameService.joinGame(joinGameRequest);
        });
    }

    @Test
    public void listGamesSuccess() throws ResponseException {
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        CreateGameRequest gameRequest = new CreateGameRequest("newGame", auth);
        gameService.createGame(gameRequest);

        ListGamesResponse listGamesResponse = (ListGamesResponse) gameService.listGames(auth);
        assertEquals("ListGamesResponse{games=[GameData{whiteUsername='null', blackUsername='null', gameName='newGame'}]}", listGamesResponse.toString());
    }

    @Test
    public void listGamesUnauthorized() throws ResponseException {
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        CreateGameRequest gameRequest = new CreateGameRequest("newGame", auth);
        gameService.createGame(gameRequest);

        assertThrows(ResponseException.class, () -> gameService.listGames("badAuth"));
    }

    @Test
    public void clearTest() throws ResponseException {
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;

        CreateGameRequest gameRequest = new CreateGameRequest("newGame", auth);
        gameService.createGame(gameRequest);

        assertDoesNotThrow(() -> {
            gameService.listGames(auth);
        });
        service.clear("");
        assertThrows(ResponseException.class, () -> gameService.listGames(auth));
    }

    @Test
    public void authorizationCheckTest() throws ResponseException{
        responses.LoginResponse registerResult = (LoginResponse) userService.registerUser(existingUser);
        String auth = registerResult.authToken;
        assertDoesNotThrow(() -> service.checkAuthorization(auth));
    }

    @Test
    public void authorizationCheckTestFail() throws ResponseException{
        userService.registerUser(existingUser);
        assertThrows(ResponseException.class, () -> service.checkAuthorization("badAuth"));
    }





}
