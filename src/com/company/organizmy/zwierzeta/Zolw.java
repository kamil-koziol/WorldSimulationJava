package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze implements IReflective {

    public static int MOVE_CHANCE = 25;
    public static int DEFEND_FROM_STRENGTH = 5;

    public Zolw() {this(new Point(0,0));}
    public Zolw(Point position) {
        super(position, 2, 1, Color.blue);
    }

    @Override
    public void akcja(Swiat swiat) {
        setDoneTurn(true);
        Random r = new Random();
        if (r.nextInt(100) < MOVE_CHANCE) {
            super.akcja(swiat);
        }
    }

    @Override
    public boolean reflects(Swiat swiat, Organizm other) {
        return other.getSila() < DEFEND_FROM_STRENGTH;
    }

    @Override
    public void afterReflection(Swiat swiat, Organizm other) {

    }
}
