package org.example;

import java.awt.*;

public class Ai extends User {

    public Ai(int riceParts, int riceIncreaseAmount, int rice, int water, int people, GameModel model, View view) {
        super(riceParts, riceIncreaseAmount, rice, water, people, model, view);
        houseIndex = -100;
    }

    public void ChooseAction() {
        if (!DiscoverCell()) {
            if (!BuildHouse()) {
                if (!WaterRice()) {
                    CollectWater();
                }
            }
        }
    }

    @Override
    public boolean DiscoverCell() {
        for (int i = 0; i < model.GetSize(); i++) {
            for (int j = 0; j < model.GetSize(); j++) {
                if (view.GetColor(i, j) == Color.WHITE && model.isAdjacentToHouse(i, j, houseIndex) && people >= model.GetValue(i, j)) {
                    view.CellIsDiscovered(i, j, Color.YELLOW);
                    model.SetValue(i, j, -1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean BuildHouse() {
        for (int i = 0; i < model.GetSize(); i++) {
            for (int j = 0; j < model.GetSize(); j++) {
                if (view.GetColor(i, j) == Color.YELLOW) {
                    if (rice >= 1 && water >= 1) {
                        view.CellIsMastered(i, j, Color.RED);
                        model.BuildHouse(i, j, houseIndex);
                        increaseRescources();
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
