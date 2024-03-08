package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import exception.ResponseException;
import model.GameData;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import responses.ListGamesResponse;
import responses.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {

    private static AuthDataAccess authDataAccess;
    private static UserDataAccess userDataAccess;
    private static GameDataAccess gameDataAccess;
    private static User testUser;
    private static GameData testGame;

    @BeforeEach
    public void setup() throws ResponseException, DataAccessException {
        authDataAccess = new SQLAuthDAO();
        userDataAccess = new SQLUserDAO();
        gameDataAccess = new SQLGameDAO();

        testUser = new User("testUser", "drowssap", "em@il");
        userDataAccess.registerUser(testUser);

        testGame = new GameData(1, "testUser", null, "testGame", new ChessGame());
        gameDataAccess.createGame(testGame);
    }

    @AfterEach
    public void clear() throws ResponseException {
        authDataAccess.clear();
        gameDataAccess.clear();
        userDataAccess.clear();
    }

    @Test
    public void registerUser() throws ResponseException {
        assertTrue(userDataAccess.userExists(testUser));
    }

    @Test
    public void reregisterUserAttempt() {
        assertThrows(ResponseException.class, () -> userDataAccess.registerUser(testUser));
    }

    @Test
    public void clearTest() throws ResponseException {
        userDataAccess.clear();
        assertFalse(userDataAccess.userExists(testUser));
    }

    @Test
    public void userExists() throws ResponseException {
        assertTrue(userDataAccess.userExists(testUser));
    }

    @Test
    public void userDoesNotExist() throws ResponseException {
        userDataAccess.clear();
        assertFalse(userDataAccess.userExists(testUser));
    }

    @Test
    public void loginSuccess() throws ResponseException {
        User newUser = new User("newUser", "pass", "new@user");
        userDataAccess.registerUser(newUser);
        assertTrue(userDataAccess.userExists(newUser));
    }

    @Test
    public void loginBeforeRegistering() throws ResponseException {
        User newUser = new User("newUser", "pass", "new@user");
        Object result = authDataAccess.loginUser(newUser);
        assertNotNull(result);
    }

    @Test
    public void userIsLoggedIn() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String authToken = result.authToken;
        assertTrue(authDataAccess.isLoggedIn(authToken));
    }

    @Test
    public void userIsNotLoggedIn() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String authToken = result.authToken;
        authDataAccess.logout(authToken);
        assertFalse(authDataAccess.isLoggedIn(authToken));
    }

    @Test
    public void authsClear() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String testAuthToken = result.authToken;
        User newUser = new User("newUser", "pass", "em@il");
        LoginResponse newResult = (LoginResponse) authDataAccess.loginUser(testUser);
        String newAuthToken = newResult.authToken;
        authDataAccess.clear();
        assertFalse(authDataAccess.isLoggedIn(testAuthToken));
        assertFalse(authDataAccess.isLoggedIn(newAuthToken));
    }

    @Test
    public void getUserNameSuccess() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String testAuthToken = result.authToken;
        assertEquals("testUser", authDataAccess.getUsername(testAuthToken));
    }

    @Test
    public void getUsernameBadAuth() {
        assertThrows(ResponseException.class, () -> authDataAccess.getUsername("badAuth"));
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String testAuthToken = result.authToken;
        assertTrue(authDataAccess.isLoggedIn(testAuthToken));
        authDataAccess.logout(testAuthToken);
        assertFalse(authDataAccess.isLoggedIn(testAuthToken));
    }

    @Test
    public void logoutDoesNotAffectOtherUsers() throws ResponseException {
        LoginResponse result = (LoginResponse) authDataAccess.loginUser(testUser);
        String testAuthToken = result.authToken;

        User newUser = new User("newUser", "pass", "em@il");
        LoginResponse newResult = (LoginResponse) authDataAccess.loginUser(testUser);
        String newAuthToken = newResult.authToken;

        assertTrue(authDataAccess.isLoggedIn(testAuthToken));
        authDataAccess.logout(testAuthToken);
        assertFalse(authDataAccess.isLoggedIn(testAuthToken));
        assertTrue(authDataAccess.isLoggedIn(newAuthToken));
    }

    @Test
    public void listGamesSuccess() {
        GameData[] expectedList = new GameData[1];
        expectedList[0] = testGame;
        ListGamesResponse expected = new ListGamesResponse(expectedList);

        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void listZeroGames() throws ResponseException {
        GameData[] expectedList = new GameData[0];
        ListGamesResponse expected = new ListGamesResponse(expectedList);
        gameDataAccess.clear();
        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void gameClearTest() throws ResponseException {
        gameDataAccess.createGame(new GameData("gameName"));
        gameDataAccess.createGame(new GameData("gameName2"));
        gameDataAccess.createGame(new GameData("gameName3"));
        gameDataAccess.clear();
        GameData[] expectedList = new GameData[0];
        ListGamesResponse expected = new ListGamesResponse(expectedList);

        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void createGameSuccess() {
        GameData[] expectedList = new GameData[1];
        expectedList[0] = testGame;
        ListGamesResponse expected = new ListGamesResponse(expectedList);

        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void createDuplicateGameDoesNotOverwrite() throws ResponseException {
        GameData testGameDup = new GameData(2, "testUser", null, "testGame", new ChessGame());
        gameDataAccess.createGame(testGameDup);

        GameData[] expectedList = new GameData[2];
        expectedList[0] = testGame;
        expectedList[1] = testGameDup;

        ListGamesResponse expected = new ListGamesResponse(expectedList);
        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void joinGameSuccess() throws ResponseException {
        User newUser = new User("newUser", "pass", "em@il");
        LoginResponse newResult = (LoginResponse) authDataAccess.loginUser(testUser);
        String newAuthToken = newResult.authToken;

        JoinGameRequest request = new JoinGameRequest(newAuthToken, ChessGame.TeamColor.BLACK, 1);
        gameDataAccess.joinGame(request);

        GameData[] expectedList = new GameData[1];
        testGame = testGame.setBlackUsername("testUser");
        expectedList[0] = testGame;
        ListGamesResponse expected = new ListGamesResponse(expectedList);

        ListGamesResponse result = (ListGamesResponse) gameDataAccess.listGames();
        assertEquals(expected, result);
    }

    @Test
    public void badColorJoinRequest() throws ResponseException {
        LoginResponse newResult = (LoginResponse) authDataAccess.loginUser(testUser);
        String newAuthToken = newResult.authToken;

        JoinGameRequest request = new JoinGameRequest(newAuthToken, ChessGame.TeamColor.WHITE, 1);
        assertThrows(ResponseException.class, () -> gameDataAccess.joinGame(request));
    }

    @Test
    public void gameDoesExist(){
        assertTrue(gameDataAccess.gameExists(1));
    }

    @Test
    public void gameDoesNotExist(){
        assertFalse(gameDataAccess.gameExists(5));
    }
}
