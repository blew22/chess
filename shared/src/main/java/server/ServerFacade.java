package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.User;
import responses.LoginResponse;
import responses.RegisterResponse;

import java.io.*;
import java.net.*;

public class ServerFacade {

    public ServerFacade() {
    }

    public LoginResponse login(String username, String password) throws ResponseException {
        var path = "/session";
        User user = new User(username, password, null);
        return this.makeRequest("POST", path, user, LoginResponse.class);
    }

    public RegisterResponse register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        User user = new User(username, password, email);
        return this.makeRequest("POST", path, user, RegisterResponse.class);
    }

    public void logout(String username, String auth) throws ResponseException {
        var path = "/session";
        AuthData authData = new AuthData(username, auth);
        this.makeRequest("DELETE", path, authData, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            String serverUrl = "http://localhost:8080";
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if ( method != "DELETE") {
                http.setDoOutput(true); // what does this line do?
                writeBody(request, http);
            } else {
                AuthData authData = (AuthData) request;
                http.setRequestProperty("Authorization", authData.authToken());
            }

            //need to add authToken to the header, not the body
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
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
