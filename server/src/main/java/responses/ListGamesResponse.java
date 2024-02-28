package responses;

import model.GameData;

import java.util.Arrays;

public record ListGamesResponse(GameData[] games) {
    @Override
    public String toString() {
        return "ListGamesResponse{" +
                "games=" + Arrays.toString(games) +
                '}';
    }
}
