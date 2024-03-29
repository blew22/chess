package server;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.*;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class ServerFacade {

    private String serverUrl;


    public ServerFacade(String url) {serverUrl = url;
    }

    public LoginResponse login(String username, String password) throws ResponseException {
        var path = "/session";
        User user = new User(username, password, null);
        return this.makeRequest("POST", path, user, LoginResponse.class, null);
    }

    public RegisterResponse register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        User user = new User(username, password, email);
        return this.makeRequest("POST", path, user, RegisterResponse.class, null);
    }

    public void logout(String username, String auth) throws ResponseException {
        var path = "/session";
        AuthData authData = new AuthData(username, auth);
        this.makeRequest("DELETE", path, authData, null, auth);
    }

    public CreateGameResponse createGame(String gameName, String auth) throws ResponseException {
        var path = "/game";
        CreateGameRequest createGameRequest = new CreateGameRequest(gameName, auth);
        return this.makeRequest("POST", path, createGameRequest, CreateGameResponse.class, auth);
    }

    public ListGamesResponse listGames(String auth) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResponse.class, auth);
    }

    public JoinGameResponse joinGame(String auth, String color, int gameID) throws ResponseException {
        var path = "/game";
        ChessGame.TeamColor teamColor;

        if(color.equalsIgnoreCase("black")){
            teamColor = ChessGame.TeamColor.BLACK;
        } else if (color.equalsIgnoreCase("white")){
            teamColor = ChessGame.TeamColor.WHITE;
        } else if (color.equalsIgnoreCase("observer")){
            teamColor = null;
        } else {
            throw new ResponseException(400, "invalid team color");
        }

        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, teamColor, gameID);
        return this.makeRequest("PUT", path, joinGameRequest, JoinGameResponse.class, auth);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String auth) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (!Objects.equals(method, "DELETE") && !Objects.equals(method, "GET")) {
                http.setDoOutput(true); // do for all except logout and list games
                writeBody(request, http, auth);
            } else if (auth != null) { // add auth to header for logout and list games
                http.setRequestProperty("Authorization", auth);
            }

            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http, String auth) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            if (auth != null) { // add auth to header for all except register and login
                http.setRequestProperty("Authorization", auth);
            }
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "HTTP failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
