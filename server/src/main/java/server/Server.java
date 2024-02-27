package server;

import com.google.gson.JsonSyntaxException;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.ClearResponse;
import responses.CreateGameResponse;
import responses.ErrorResponse;
import responses.LogoutResponse;
import spark.*;
import com.google.gson.Gson;
import service.Service;
import model.User;


public class Server {

    private final Service service;

    public Server() {
        this.service = new Service();
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logoutUser);
        Spark.post("/game", this::createGame);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);

        Spark.exception(ResponseException.class, this::exceptionHandler);


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    private Object clear(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            service.clear(authToken);
            res.status(200);
            ClearResponse response = new ClearResponse();
            return new Gson().toJson(response);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object registerUser(Request req, Response res) throws ResponseException { //what are req and res doing? do i need to build my own classes?
        try {
            var user = new Gson().fromJson(req.body(), User.class);
            if (user.isValid()) {
                var response = service.registerUser(user);
                return new Gson().toJson(response);
            } else {
                throw new ResponseException(400, "Error: bad request");
            }
        } catch (ResponseException e) {
            res.status(e.StatusCode());
//            res.body(e.getMessage());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object loginUser(Request req, Response res) throws ResponseException {
        try {
            var user = new Gson().fromJson(req.body(), User.class);
            var response = service.loginUser(user);
            return new Gson().toJson(response);
        } catch (ResponseException e){
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object logoutUser(Request req, Response res) throws ResponseException {
        try {
            String authToken = req.headers("Authorization");
            if(service.logoutUser(authToken)) {
                LogoutResponse response = new LogoutResponse();
                return new Gson().toJson(response);
            } else {
                throw new ResponseException(401, "Error: not logged in");
            }
        } catch (JsonSyntaxException e) {
//            System.out.println(req.headers("Authorization"));
            res.status(500);
            return new Gson().toJson("your code is garbage");
        } catch (ResponseException e){
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object createGame(Request req, Response res) {
        CreateGameRequest gameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
        gameRequest = gameRequest.setAuthToken(req.headers("Authorization"));

        try {
            var gameResponse = service.createGame(gameRequest);
            return new Gson().toJson(gameResponse);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object listGames(Request req, Response res){
        String authToken = req.headers("Authorization");
        try {
            var response = service.listGames(authToken);
            return new Gson().toJson(response);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

    private Object joinGame(Request req, Response res) {
        JoinGameRequest joinRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        joinRequest = joinRequest.setAuthToken(req.headers("Authorization"));

        try {
            var joinResponse = service.joinGame(joinRequest);
            return new Gson().toJson(joinResponse);
        } catch (ResponseException e) {
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse(e.getMessage());
            return new Gson().toJson(response);
        }
    }

}
