package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;

import java.awt.*;

public class Trawa extends Roslina {
    public Trawa() {
        this(new Point(0,0));
    }
    public Trawa(Point position) {
        super(position, 0, Color.green);
    }

    @Override
    public void kolizja(Swiat swiat, Organizm other) {
    }

    @Override
    public Organizm clone() {
        return new Trawa(getPosition());
    }
}
