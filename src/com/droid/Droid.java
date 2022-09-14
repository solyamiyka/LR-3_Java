package com.droid;

import java.util.Random;

public class Droid extends BaseDroid{  // похідний клас від BaseDroid

    // конструктори
    public Droid(){}
    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
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

    // функція для виводу інформації про Дроїда
    @Override
    public String toString() {
        return    "\n| Name:  " + name            + "  |" +
                  "\n| " + health + " - HP           " +
                  "\n| " + damage + " - Damage       " +
                  "\n-------------------------------------";
    }

}
