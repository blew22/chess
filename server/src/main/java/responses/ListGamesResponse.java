package responses;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class ListGamesResponse {

    private final Collection<GameData> gameList;

    public ListGamesResponse(Collection<GameData> gameList){ this.gameList = gameList;
    }

}
