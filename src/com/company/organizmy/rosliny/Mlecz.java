package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(Point position) {
        super(position, 0, Color.YELLOW);
    }

    @Override
    public void kolizja(Swiat swiat, Organizm other) {

    }

    @Override
    public void akcja(Swiat swiat) {
        for (int i = 0; i < 3; i++) {
            super.akcja(swiat);
        }
    }

    @Override
    public Organizm clone() {
        return new Mlecz(getPosition());
    }
}
