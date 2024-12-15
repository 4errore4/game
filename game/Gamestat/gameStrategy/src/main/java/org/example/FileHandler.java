package org.example;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);
    View view;
    GameModel model;
    Player p;
    Ai a;
    private int day = 0;
    private int size = 0;
    private int[][] field = null;
    private int[] player = {0, 1, 1, 1, 1};
    private int[] ai = {0, 1, 1, 1, 1};
    private File file;

    public FileHandler() {
        logger.info("Initializing FileHandler...");
        String[] options = {"New Game", "Load Game"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "Game Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 1) {
            logger.info("User chose to load a game.");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose file");

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                LoadData();
                StartGame();
            }
            else {
                logger.warn("No file chosen. Starting a new game.");
            }
        } else {
            StartGame();
            logger.info("User chose to start a new game.");
        }
    }

    private void StartGame() {
        if (size == 0) model = new GameModel(4);
        else model = new GameModel(size, field, day);
        view = new View(model, this);
        p = new Player(player[1], player[0], player[2], player[3], player[4], model, view);
        a = new Ai(ai[1], ai[0], ai[2], ai[3], ai[4], model, view);
        Commander commander = new Commander(a, model, p, view);
        Controller controller = new Controller(model, view, p, a);
    }

    public void LoadData() {
        if (file == null) {
            logger.error("File is null. Unable to load data.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            List<int[]> fieldList = new ArrayList<>();
            boolean readingField = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith("Day:")) {
                    day = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Field:")) {
                    readingField = true; // Начинаем читать поле
                } else if (readingField && !line.isEmpty() && !line.startsWith("Player:")) {
                    String[] values = line.split("\\s+");
                    fieldList.add(Arrays.stream(values).mapToInt(Integer::parseInt).toArray());
                } else if (line.startsWith("Player:")) {
                    readingField = false; // Останавливаем чтение поля
                    player = Arrays.stream(line.substring(7).trim().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                } else if (line.startsWith("AI:")) {
                    ai = Arrays.stream(line.substring(3).trim().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                }
            }

            size = fieldList.size();
            field = fieldList.toArray(new int[size][size]);
            logger.info("Data loaded successfully.");

        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage(), e);
        }
    }

    public void SaveData() {
        if (model == null) {
            logger.error("Cannot save game. Field is null.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
                writer.write("Day: " + model.GetDay());
                writer.newLine();

                writer.write("Field:");
                writer.newLine();
                for (int i = 0; i < model.GetSize(); i++) {
                    for (int j = 0; j < model.GetSize(); j++) {
                        writer.write(model.GetValue(i, j) + " ");
                    }
                    writer.newLine();
                }

                writer.write("Player: " + p.riceIncreaseAmount + " " + p.riceParts + " " + p.rice + " " +
                        p.water + " " + p.people);
                writer.newLine();

                writer.write("AI: " + a.riceIncreaseAmount + " " + a.riceParts + " " + a.rice + " " +
                        a.water + " " + a.people);
                writer.newLine();

                JOptionPane.showMessageDialog(null, "Game saved successfully!");
                logger.info("Game saved to file: {}", saveFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
                logger.error("Error saving game: {}", e.getMessage(), e);
            }
        }
    }
}
