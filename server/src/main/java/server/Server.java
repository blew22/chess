package server;

import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/db", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return null;
            }
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
