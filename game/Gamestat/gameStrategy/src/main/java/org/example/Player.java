package org.example;

import java.awt.*;

public class Player extends User {
    private int row;
    private int col;

    public Player(int riceParts, int riceIncreaseAmount, int rice, int water, int people, GameModel model, View view) {
        super(riceParts, riceIncreaseAmount, rice, water, people, model, view);
        houseIndex = 100;
    }

    public void ChooseCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean DiscoverCell() {
        boolean result = false;
        if (view.GetColor(row, col) == Color.WHITE) {
            if (people >= model.GetValue(row, col) && model.isAdjacentToHouse(row, col, houseIndex)) {
                view.CellIsDiscovered(row, col, Color.BLUE);
                model.SetValue(row, col, 0);
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean BuildHouse() {
        boolean result = false;
        if (view.GetColor(row, col) == Color.BLUE) {
            if (rice >= 1 && water >= 1) {
                view.CellIsMastered(row, col, Color.GREEN);
                model.BuildHouse(row, col, houseIndex);
                increaseRescources();
                result = true;
            }
        }
        return result;
    }
}
