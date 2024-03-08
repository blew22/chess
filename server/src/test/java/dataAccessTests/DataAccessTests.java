package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import exception.ResponseException;
import model.GameData;
import model.User;
import org.junit.jupiter.api.*;
import responses.LoginResponse;

import java.sql.Connection;

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

        GameData testGame = new GameData(1, "testUser", null, "testGame", new ChessGame());
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
    public void getUsernameBadAuth(){
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
    public void listGamesSuccess(){

    }




}
