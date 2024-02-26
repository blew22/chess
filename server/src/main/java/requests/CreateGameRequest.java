package requests;

import responses.CreateGameResponse;

public record CreateGameRequest(String gameName, String authToken) {

    public CreateGameRequest setAuthToken(String authToken){
        return new CreateGameRequest(this.gameName, authToken);
    }
}
