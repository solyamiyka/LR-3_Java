package com.droid;

public class BaseDroid { // Базовий клас дроїда, в якому містяться всі значення дроїда

    protected String name;
    protected int health;
    protected int damage;

    public void setName(String name) { this.name = name; }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
    public boolean isAlive() {
        return health > 0;
    }
}
