package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Lis extends Zwierze {
    public Lis(Point position) {
        super(position, 3, 7, Color.ORANGE);
    }

    @Override
    public void akcja(Swiat swiat) {
        setDoneTurn(true);
        ArrayList<Point> points = swiat.getPointsAround(getPosition(), 1);
        ArrayList<Point> availablePoints = new ArrayList<Point>();
        for (Point point : points) {
            Organizm organizm = swiat.getOrganismAt(point);
            if (organizm instanceof Zwierze) {
                if (organizm.getSila() < this.getSila()) {
                    availablePoints.add(point);
                }
            } else {
                availablePoints.add(point);
            }
        }

        if (!availablePoints.isEmpty()) {
            Random r = new Random();
            Point p = availablePoints.get(r.nextInt(availablePoints.size()));
            moveTo(swiat, p);
        }
    }

    @Override
    public Organizm clone() {
        return new Lis(getPosition());
    }
}
