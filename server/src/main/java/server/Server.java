package server;

import exception.ResponseException;
import responses.ClearResponse;
import responses.ErrorResponse;
import responses.RegisterResponse;
import spark.*;
import com.google.gson.Gson;
import service.Service;
import dataAccess.DataAccess;
import user.User;


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

    private Object clear(Request req, Response res){
        service.clear();
        res.status(200);
        ClearResponse response = new ClearResponse();
        return new Gson().toJson(response);
    }

    private Object registerUser(Request req, Response res) throws ResponseException { //what are req and res doing? do i need to build my own classes?
        try {
            var user = new Gson().fromJson(req.body(), User.class);
            var response = service.registerUser(user);
            return new Gson().toJson(response);
        } catch(ResponseException e){
            res.status(e.StatusCode());
            ErrorResponse response = new ErrorResponse("Error: already taken");
            return new Gson().toJson(response);
        }
    }
}
