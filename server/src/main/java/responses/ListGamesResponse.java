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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesResponse that = (ListGamesResponse) o;
        return Arrays.equals(games, that.games);
    }
}
