package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze implements IDodgeable {

    public Antylopa() {
        this(new Point(0,0));
    }
    public Antylopa(Point position) {
        super(position, 4, 4, Color.CYAN);
        zasieg_ruchu = 2;
    }

    @Override
    public boolean dodges(Swiat swiat, Organizm other) {
        Random r = new Random();
        return r.nextInt(2) == 0;
    }

    @Override
    public void afterDodge(Swiat swiat, Organizm other) {
        Point freePoint = swiat.getRandomFreePointAround(getPosition(), zasieg_ruchu);
        if (freePoint != null) {
            moveTo(swiat, freePoint);
        }
    }
}
