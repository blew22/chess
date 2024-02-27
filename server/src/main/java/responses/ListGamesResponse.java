package responses;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record ListGamesResponse(GameData[] gameList) {}
