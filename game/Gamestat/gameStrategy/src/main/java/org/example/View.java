package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private static final Logger logger = LogManager.getLogger(View.class);

    private final GameModel model;
    private final int size;
    private JPanel gamePanel;
    private JButton[][] gameCells;
    private JButton discoverButton;
    private JButton buildButton;
    private JButton waterButton;
    private JButton waterRiceButton;
    private JLabel dayLabel;
    private JLabel playerResourcesLabel;
    private JLabel aiResourcesLabel;
    private final FileHandler fileHandler;

    public View(GameModel model, FileHandler fileHandler) {
        this.model = model;
        this.fileHandler = fileHandler;
        size = model.GetSize();
        try {
            Draw();
            setVisible(true);
            logger.info("View initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing View: {}", e.getMessage(), e);
        }
    }

    private void Draw() {
        try {
            setTitle("Game Strategy");
            setSize(800, 600);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    handleWindowClosing();
                }
            });

            initGamePanel();
            JPanel topPanel = initTopPanel();
            JPanel controlPanel = initControlPanel();

            add(gamePanel, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);
            add(controlPanel, BorderLayout.SOUTH);
        } catch (Exception e) {
            logger.error("Error during Draw: {}", e.getMessage(), e);
        }
    }

    private void handleWindowClosing() {
        try {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to save the game before exiting?",
                    "Save Game",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                fileHandler.SaveData();
                logger.info("Game saved successfully before exiting.");
                System.exit(0);
            } else if (choice == JOptionPane.NO_OPTION) {
                logger.info("Game exited without saving.");
                System.exit(0);
            }
        } catch (Exception e) {
            logger.error("Error during window closing: {}", e.getMessage(), e);
        }
    }

    private void initGamePanel() {
        try {
            gamePanel = new JPanel(new GridLayout(size, size));
            gameCells = new JButton[size][size];

            int[][] field = model.GetField();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    gameCells[i][j] = new JButton(String.valueOf(field[i][j]));
                    try {
                        initializeCell(i, j);
                    } catch (Exception ex) {
                        logger.error("Error initializing cell [{}][{}]: {}", i, j, ex.getMessage(), ex);
                    }
                    gamePanel.add(gameCells[i][j]);
                }
            }
            logger.info("Game panel initialized.");
        } catch (Exception e) {
            logger.error("Error initializing game panel: {}", e.getMessage(), e);
        }
    }

    private void initializeCell(int i, int j) {
        int value = model.GetValue(i, j);
        switch (value) {
            case 0 -> CellIsDiscovered(i, j, Color.BLUE);
            case -1 -> CellIsDiscovered(i, j, Color.YELLOW);
            case 100 -> CellIsMastered(i, j, Color.GREEN);
            case -100 -> CellIsMastered(i, j, Color.RED);
            default -> gameCells[i][j].setBackground(Color.WHITE);
        }
        gameCells[i][j].setFocusPainted(false);
        gameCells[i][j].setActionCommand(i + "," + j);
    }

    private JPanel initTopPanel() {
        try {
            JPanel topPanel = new JPanel(new BorderLayout());

            dayLabel = new JLabel("Day: " + model.GetDay(), SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));

            playerResourcesLabel = new JLabel("Player: ");
            playerResourcesLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            aiResourcesLabel = new JLabel("AI: ");
            aiResourcesLabel.setHorizontalAlignment(SwingConstants.LEFT);

            topPanel.add(playerResourcesLabel, BorderLayout.EAST);
            topPanel.add(dayLabel, BorderLayout.CENTER);
            topPanel.add(aiResourcesLabel, BorderLayout.WEST);

            logger.info("Top panel initialized.");
            return topPanel;
        } catch (Exception e) {
            logger.error("Error initializing top panel: {}", e.getMessage(), e);
            return new JPanel();
        }
    }

    private JPanel initControlPanel() {
        try {
            JPanel controlPanel = new JPanel(new FlowLayout());

            discoverButton = new JButton("Discover");
            buildButton = new JButton("Build");
            waterButton = new JButton("Collect Water");
            waterRiceButton = new JButton("Water Rice");

            controlPanel.add(discoverButton);
            controlPanel.add(buildButton);
            controlPanel.add(waterButton);
            controlPanel.add(waterRiceButton);

            logger.info("Control panel initialized.");
            return controlPanel;
        } catch (Exception e) {
            logger.error("Error initializing control panel: {}", e.getMessage(), e);
            return new JPanel();
        }
    }

    public void SetActionListener(ActionListener listener) {
        try {
            discoverButton.setActionCommand("Discover");
            discoverButton.addActionListener(listener);

            buildButton.setActionCommand("Build");
            buildButton.addActionListener(listener);

            waterButton.setActionCommand("CollectWater");
            waterButton.addActionListener(listener);

            waterRiceButton.setActionCommand("WaterRice");
            waterRiceButton.addActionListener(listener);

            for (JButton[] gameCell : gameCells) {
                for (JButton jButton : gameCell) {
                    jButton.addActionListener(listener);
                }
            }
            logger.info("Action listeners set.");
        } catch (Exception e) {
            logger.error("Error setting action listeners: {}", e.getMessage(), e);
        }
    }

    public void CellIsDiscovered(int row, int col, Color color) {
        try {
            gameCells[row][col].setBackground(color);
            gameCells[row][col].setText("Discovered");
        } catch (Exception e) {
            logger.error("Error setting cell as discovered [{}][{}]: {}", row, col, e.getMessage(), e);
        }
    }

    public void CellIsMastered(int row, int col, Color color) {
        try {
            gameCells[row][col].setEnabled(false);
            gameCells[row][col].setBackground(color);
            gameCells[row][col].setText("House");
        } catch (Exception e) {
            logger.error("Error setting cell as mastered [{}][{}]: {}", row, col, e.getMessage(), e);
        }
    }

    public void UpdateDay(int day) {
        try {
            dayLabel.setText("Day: " + day);
            logger.info("Day updated to {}", day);
        } catch (Exception e) {
            logger.error("Error updating day: {}", e.getMessage(), e);
        }
    }

    public void UpdateResources(Ai ai, Player player) {
        try {
            playerResourcesLabel.setText(String.format("Player | Water: %d | RiceParts: %d | Rice: %d | People: %d",
                    player.water, player.riceParts, player.rice, player.people));
            aiResourcesLabel.setText(String.format("AI | Water: %d | RiceParts: %d | Rice: %d | People: %d",
                    ai.water, ai.riceParts, ai.rice, ai.people));
            logger.info("Resources updated.");
        } catch (Exception e) {
            logger.error("Error updating resources: {}", e.getMessage(), e);
        }
    }

    public Color GetColor(int row, int col) {
        try {
            return gameCells[row][col].getBackground();
        } catch (Exception e) {
            logger.error("Error getting color for cell [{}][{}]: {}", row, col, e.getMessage(), e);
            return Color.BLACK;
        }
    }

    public void ShowMessage(String message) {
        try {
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Message shown: {}", message);
        } catch (Exception e) {
            logger.error("Error showing message: {}", e.getMessage(), e);
        }
    }
}
