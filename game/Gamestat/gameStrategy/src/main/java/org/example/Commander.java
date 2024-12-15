package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Commander {
    private static final Logger logger = LogManager.getLogger(Commander.class);
    private final GameStatistics statistics = new GameStatistics();
    private final Ai ai;
    private final GameModel model;
    private final Player player;
    private final View view;
    private int playerHouses;
    private int aiHouses;
    private int aiDiscoveredCells;
    private int playerDiscoveredCells;

    public Commander(Ai ai, GameModel model, Player player, View view) {
        this.ai = ai;
        this.model = model;
        this.player = player;
        this.view = view;
        logger.info("Commander initialized.");
    }

    public void EndTurn() {
        logger.info("Ending turn...");
        ai.ChooseAction();
        model.NextDay();
        player.CalculateRice();
        ai.CalculateRice();
        view.UpdateDay(model.GetDay());
        SetViewUsers(ai, player);

        statistics.recordDay(model.GetDay(), player, ai, playerHouses, aiHouses, playerDiscoveredCells, aiDiscoveredCells);

        String result = CheckWinner();
        if (!result.equals("No winner yet.")) {
            logger.info("Winner determined: {}", result);
            view.ShowMessage(result);
            showStatistics();
        }
    }

    public void SetViewUsers(Ai ai, Player player) {
        view.UpdateResources(ai, player);
    }

    public String CheckWinner() {
        playerHouses = 0;
        aiHouses = 0;
        aiDiscoveredCells = 0;
        playerDiscoveredCells = 0;
        int totalCells = model.GetSize() * model.GetSize();
        int winningCells = totalCells / 2;

        for (int i = 0; i < model.GetSize(); i++) {
            for (int j = 0; j < model.GetSize(); j++) {
                if (model.GetValue(i, j) == player.houseIndex) playerHouses++;
                else if (model.GetValue(i, j) == ai.houseIndex) aiHouses++;
                else if(model.GetValue(i, j) == -1) aiDiscoveredCells++;
                else if(model.GetValue(i, j) == 0) playerDiscoveredCells++;
            }
        }

        if (playerHouses >= winningCells) {
            return "Player wins!";
        } else if (aiHouses >= winningCells) {
            return "AI wins!";
        }
        return "No winner yet.";
    }

    private void showStatistics() {
        StatisticsChart chart = new StatisticsChart(statistics);
        chart.DisplayCharts();
    }
}
