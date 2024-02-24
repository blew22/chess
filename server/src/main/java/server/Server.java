package server;

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

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request req, Response res){
        service.clear();
        res.status(200);
        return "";
    }

    private Object registerUser(Request req, Response res){

        return service.registerUser();
    }
}
