package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class GameModel {
    private static final Logger logger = LogManager.getLogger(GameModel.class);

    private final int size;
    private int[][] field;
    private int day;
    private Random rand;

    public GameModel(int size) {
        this.size = size;
        this.day = 1;
        InitGameField();

        try {
            InitGameField();
            logger.info("GameModel initialized with size {}", size);
        } catch (Exception e) {
            logger.error("Error initializing GameModel: {}", e.getMessage(), e);
        }

    }

    public GameModel(int size, int[][] field, int day) {
        this.size = size;
        this.field = field;
        this.day = day;

        logger.info("GameModel loaded from file with size {}, day {}", size, day);
    }

    private void InitGameField() {
        rand = new Random();
        field = new int[size][size];

        try {
            InitializeCells();
            InitializeStartCells();
            logger.info("Game field initialized.");
        } catch (Exception e) {
            logger.error("Error initializing game field: {}", e.getMessage(), e);
        }
    }

    private void InitializeStartCells() {
        try{
        if (size > 1) {
            field[0][1] = rand.nextBoolean() ? 1 : 2;
            if (field[0][1] == 1) field[1][0] = rand.nextBoolean() ? 1 : 2;
            else field[1][0] = 1;
            field[size - 1][size - 2] = rand.nextBoolean() ? 1 : 2;
            if (field[size - 1][size - 2] == 1) field[size - 2][size - 1] = rand.nextBoolean() ? 1 : 2;
            else field[size - 2][size - 1] = 1;
        }
        field[0][0] = -100;
        field[size - 1][size - 1] = 100;

        logger.info("Start cells initialized.");}
         catch (Exception e) {
            logger.error("Error initializing start cells: {}", e.getMessage(), e);
        }

    }

    private void InitializeCells() {
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    field[i][j] = rand.nextInt(3) + 1;
                }
            }
            logger.info("Cells initialized with random values.");
        } catch (Exception e) {
            logger.error("Error initializing cells: {}", e.getMessage(), e);
        }
    }

    public int[][] GetField() {
        return field;
    }

    public int GetSize() {
        return size;
    }

    public int GetValue(int row, int col) {
        try {
            return field[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Invalid cell access: row={}, col={}", row, col, e);
            throw e;
        }
    }

    public void BuildHouse(int row, int col, int value) {
        try {
            field[row][col] = value;
            logger.info("House built at row={}, col={}, value={}", row, col, value);
        } catch (Exception e) {
            logger.error("Error building house: {}", e.getMessage(), e);
        }
    }

    public boolean isAdjacentToHouse(int row, int col, int houseIndex) {
        try {
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (i >= 0 && i < size && j >= 0 && j < size && field[i][j] == houseIndex) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking adjacency: {}", e.getMessage(), e);
            return false;
        }
    }

    public void NextDay() {
        day++;
    }

    public void SetValue(int row, int col, int value) {
        try {
            field[row][col] = value;
            logger.info("Value set at row={}, col={}, value={}", row, col, value);
        } catch (Exception e) {
            logger.error("Error setting value: {}", e.getMessage(), e);
        }
    }

    public int GetDay() {
        return day;
    }
}
