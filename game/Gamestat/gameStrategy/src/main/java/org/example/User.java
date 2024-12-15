package org.example;

public abstract class User {
    protected int riceParts;
    protected int riceIncreaseAmount;
    protected int rice;
    protected int water;
    protected int people;
    protected int houseIndex;
    protected GameModel model;
    protected View view;

    public User(int riceParts, int riceIncreaseAmount, int rice, int water, int people, GameModel model, View view) {
        this.riceParts = riceParts;
        this.riceIncreaseAmount = riceIncreaseAmount;
        this.rice = rice;
        this.water = water;
        this.people = people;
        this.model = model;
        this.view = view;
    }

    public abstract boolean DiscoverCell();

    public abstract boolean BuildHouse();

    public void CollectWater() {
        water++;
    }

    public void CalculateRice() {
        riceParts += riceIncreaseAmount;
        rice += riceParts / 10;
        riceParts = riceParts % 10;
    }

    public boolean WaterRice() {
        if (water > 0) {
            rice++;
            water--;
            return true;
        }
        return false;
    }

    protected void increaseRescources() {
        riceIncreaseAmount++;
        rice--;
        water--;
        people++;
    }
}
