package com.mygdx.main;

/**
 * Created by FlapJack on 9/2/2017.
 *
 * stamina - power ups
 *
 *
 * agility - improves weapon damage and accuracy
 *      - each point increases damage by 5 and accuracy by 2
 * strength - increases damage mitigation
 *      - point increases defense by 5
 *      - resistance by 2
 * endurance - improves health and stamina
 *      - point increases hp and stamina by 5
 * luck - improves roll percentage and critical strikes
 *      - point increases roll percentages by 5
 */

public class Stat
{
    public int health;
    public int stamina;
    public int armor;

    public int strength;
    public int agility;
    public int endurance;
    public int luck;

    public int level;

    public Stat()
    {
        health = 50;
        stamina = 25;
        armor = 0;

        strength = 0;
        agility = 0;
        endurance = 0;
        luck = 0;

        level = 1;
    }
}
