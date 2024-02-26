package responses;

import model.GameData;

public class CreateGameResponse extends spark.Response{

    final Integer gameID;

    public CreateGameResponse(Integer gameID){this.gameID = gameID;

    }
}