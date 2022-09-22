package com.droid;

import java.util.Random;

public class Droid {

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

    public int getHit(int damage) {  // функція в якій визначається рандомним чином атака attacker
        // та знімається здоров'я в defender

        Random random = new Random();

        int actualDamage = random.nextInt(damage);
        this.health -= actualDamage;

        if (health < 0) {
            health = 0;
        }

        return actualDamage;
    }
    @Override
    public String toString() {
        return    "\n| Name:  " + name            + "  |" +
                "\n| " + health + " - HP           " +
                "\n| " + damage + " - Damage       " +
                "\n-------------------------------------";
    }
    // конструктори
    public Droid(){}
    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

}
