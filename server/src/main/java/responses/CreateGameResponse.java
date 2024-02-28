package responses;

public class CreateGameResponse extends spark.Response{

    final public Integer gameID;

    public CreateGameResponse(Integer gameID){this.gameID = gameID;

    }
}