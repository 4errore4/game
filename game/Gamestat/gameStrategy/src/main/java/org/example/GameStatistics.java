package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameStatistics {
    private final List<Integer> days = new ArrayList<>();
    private final List<Integer> playerRice = new ArrayList<>();
    private final List<Integer> playerWater = new ArrayList<>();
    private final List<Integer> playerPeople = new ArrayList<>();
    private final List<Integer> playerHouses = new ArrayList<>();
    private final List<Integer> aiDiscoveredCells = new ArrayList<>();
    private final List<Integer> playerDiscoveredCells = new ArrayList<>();

    private final List<Integer> aiRice = new ArrayList<>();
    private final List<Integer> aiWater = new ArrayList<>();
    private final List<Integer> aiPeople = new ArrayList<>();
    private final List<Integer> aiHouses = new ArrayList<>();

    public void recordDay(int day, Player player, Ai ai, int playerHouses, int aiHouses, int playerDiscoveredCells, int aiDiscoveredCells) {
        days.add(day);

        playerRice.add(player.rice);
        playerWater.add(player.water);
        playerPeople.add(player.people);
        this.playerHouses.add(playerHouses);
        this.playerDiscoveredCells.add(playerDiscoveredCells);

        aiRice.add(ai.rice);
        aiWater.add(ai.water);
        aiPeople.add(ai.people);
        this.aiHouses.add(aiHouses);
        this.aiDiscoveredCells.add(aiDiscoveredCells);
    }

    public List<Integer> getDays() {
        return days;
    }

    public List<Integer> getAiDiscoveredCells() {
        return aiDiscoveredCells;
    }

    public List<Integer> getPlayerDiscoveredCells() {
        return playerDiscoveredCells;
    }

    public List<Integer> getPlayerRice() {
        return playerRice;
    }

    public List<Integer> getPlayerWater() {
        return playerWater;
    }

    public List<Integer> getPlayerPeople() {
        return playerPeople;
    }

    public List<Integer> getPlayerHouses() {
        return playerHouses;
    }

    public List<Integer> getAiRice() {
        return aiRice;
    }

    public List<Integer> getAiWater() {
        return aiWater;
    }

    public List<Integer> getAiPeople() {
        return aiPeople;
    }

    public List<Integer> getAiHouses() {
        return aiHouses;
    }
}
