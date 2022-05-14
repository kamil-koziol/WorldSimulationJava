package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;
import com.company.organizmy.zwierzeta.IInvincible;
import com.company.organizmy.zwierzeta.Zwierze;

import java.awt.*;
import java.util.ArrayList;

public class BarszczSosnowskiego extends Roslina {

    public static int KILLING_RANGE = 1;

    public BarszczSosnowskiego() {
        this(new Point(0,0));
    }
    public BarszczSosnowskiego(Point position) {
        super(position, 10, Color.RED);
    }

    @Override
    public void akcja(Swiat swiat) {
        super.akcja(swiat);

        ArrayList<Point> points = swiat.getPointsAround(getPosition(), KILLING_RANGE);
        for (Point point : points) {
            Organizm organizm = swiat.getOrganismAt(point);

            if (organizm instanceof Zwierze) {
                swiat.usunOrganizm(organizm);
            }
        }
    }

    @Override
    public void kolizja(Swiat swiat, Organizm other) {
        if (other instanceof Zwierze zwierze) {
            swiat.usunOrganizm(zwierze);
        }
    }

    @Override
    public Organizm clone() {
        return new BarszczSosnowskiego(getPosition());
    }
}
