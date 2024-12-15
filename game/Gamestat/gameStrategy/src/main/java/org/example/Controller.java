package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final View view;
    private final Player player;
    Commander commander;

    public Controller(GameModel model, View view, Player player, Ai ai) {
        this.view = view;
        this.player = player;
        commander = new Commander(ai, model, player, view);
        commander.SetViewUsers(ai, player);
        try {
            commander.SetViewUsers(ai, player);
            initialize();
            logger.info("Controller initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing Controller: {}", e.getMessage(), e);
        }
    }

    private void initialize() {
        GameActionListener actionListener = new GameActionListener();
        try {
            view.SetActionListener(actionListener);
            logger.info("Action listeners set successfully.");
        } catch (Exception e) {
            logger.error("Error setting action listeners: {}", e.getMessage(), e);
        }
    }

    private class GameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            try {
                if (command.contains(",")) {
                    String[] coords = command.split(",");
                    int row = Integer.parseInt(coords[0]);
                    int col = Integer.parseInt(coords[1]);
                    player.ChooseCell(row, col);
                    logger.info("Cell selected: row={}, col={}", row, col);
                } else {
                    switch (command) {
                        case "Discover" -> {
                            if (player.DiscoverCell()) {
                                logger.info("Discover action performed.");
                                commander.EndTurn();
                            }
                        }
                        case "Build" -> {
                            if (player.BuildHouse()) {
                                logger.info("Build action performed.");
                                commander.EndTurn();
                            }
                        }
                        case "WaterRice" -> {
                            if (player.WaterRice()) {
                                logger.info("WaterRice action performed.");
                                commander.EndTurn();
                            }
                        }
                        case "CollectWater" -> {
                            player.CollectWater();
                            logger.info("CollectWater action performed.");
                            commander.EndTurn();
                        }
                        default -> logger.warn("Unknown command received: {}", command);
                    }
                }
            } catch (Exception ex) {
                logger.error("Error processing action: {}", ex.getMessage(), ex);
            }
        }
    }
}
