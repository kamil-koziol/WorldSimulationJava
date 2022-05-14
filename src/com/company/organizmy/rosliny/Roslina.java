package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;
import java.util.Random;

public abstract class Roslina extends Organizm {
    static int SEEDING_CHANCE = 5;

    public Roslina(Point position, int sila, Color color) {
        super(position, sila, 0, color);
    }

    public abstract void kolizja(Swiat swiat, Organizm other);

    @Override
    public void akcja(Swiat swiat) {
        setDoneTurn(true);
        Random r = new Random();
        if (r.nextInt(100) < SEEDING_CHANCE) {
            seeding(swiat);
        }
    }

    public void seeding(Swiat swiat) {
        Point point = swiat.getRandomFreePointAround(getPosition(), 1);
        if (point != null) {
            System.out.println("ZASIANO: " + this + " do Punkt{" + point.x + ", " + point.y + "}");
            Organizm organizm = this.getEmptyCopy();
            organizm.setPosition(point, swiat);
            swiat.dodajOrganizm(organizm);
        }
    }
}
