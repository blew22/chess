package clientTests;

import dataAccess.*;
import exception.ResponseException;
import model.User;
import org.junit.jupiter.api.*;
import responses.RegisterResponse;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static String testUserAuth;
    private static AuthDataAccess authDataAccess;
    private static UserDataAccess userDataAccess;
    private static GameDataAccess gameDataAccess;
    private static final User testUser = new User("testUser", "testPass", "em@il");




    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
        try {
            authDataAccess = new SQLAuthDAO();
            userDataAccess = new SQLUserDAO();
            gameDataAccess = new SQLGameDAO();
            authDataAccess.clear();
            userDataAccess.clear();
            gameDataAccess.clear();
        } catch (ResponseException | DataAccessException e){

            fail();
        }


        try {
            RegisterResponse response = serverFacade.register("testUser", "testPass", "em@il");
            testUserAuth = response.getAuthToken();
            serverFacade.createGame("GameTest", testUserAuth);
        } catch (ResponseException e) {
            System.out.print(e.getMessage());
            fail();
        }



    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerSuccess() throws ResponseException {
        assertTrue(userDataAccess.userExists(testUser));
    }

    @Test
    public void registerTwice(){
        assertThrows(ResponseException.class, () -> serverFacade.register("testUser", "testPass", "em@il"));
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        assertTrue(authDataAccess.isLoggedIn(testUserAuth));
        serverFacade.logout("testUser", testUserAuth);
        assertFalse(authDataAccess.isLoggedIn(testUserAuth));
        testUserAuth = serverFacade.login("testUser", "testPass").authToken;
    }

    @Test
    public void logoutBadAuth() throws ResponseException {
        assertTrue(authDataAccess.isLoggedIn(testUserAuth));
        assertThrows(ResponseException.class, () -> serverFacade.logout("testUser", "badAuth"));
    }

    @Test
    public void login() throws ResponseException {
        String newAuth = serverFacade.login("testUser", "testPass").authToken;
        assertTrue(authDataAccess.isLoggedIn(newAuth));
    }

    @Test
    public void loginWithoutRegistering(){
        assertThrows(ResponseException.class, () -> serverFacade.login("newUser", "newPass"));
    }

    @Test
    public void createGameSuccess() throws ResponseException {
        int gameID = serverFacade.createGame("TestGame", testUserAuth).gameID;
        assertTrue(gameDataAccess.gameExists(gameID));
    }

    @Test
    public void createGameBadAuth(){
        assertThrows(ResponseException.class, () -> serverFacade.createGame("Test Game1", "badAuth"));
    }

    @Test
    public void listGamesSuccess() throws ResponseException {
        gameDataAccess.clear();
        serverFacade.createGame("Test1", testUserAuth);
        serverFacade.createGame("Test2", testUserAuth);
        assertEquals(gameDataAccess.listGames(), serverFacade.listGames(testUserAuth));
    }

    @Test
    public void listGamesBadAuth(){
        assertThrows(ResponseException.class, () -> serverFacade.listGames("badAuth"));
    }

    @Test
    public void joinGameSuccess() throws ResponseException {
        gameDataAccess.clear();
        int gameID = serverFacade.createGame("JoinTest", testUserAuth).gameID;
        serverFacade.listGames(testUserAuth);
        serverFacade.joinGame(testUserAuth, "white", gameID);
    }

    @Test
    public void joinGameBadID(){
        assertThrows(ResponseException.class, () -> serverFacade.joinGame(testUserAuth, "black", -1));
    }



}
