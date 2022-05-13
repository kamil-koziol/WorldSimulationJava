package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;
import com.company.organizmy.zwierzeta.Zwierze;

import java.awt.*;

public class Guarana extends Roslina {

    public static int STRENGTH_TO_ADD = 3;

    public Guarana(Point position) {
        super(position, 0, Color.PINK);
    }

    @Override
    public void kolizja(Swiat swiat, Organizm other) {
        if (other instanceof Zwierze zwierze) {
            zwierze.setSila(zwierze.getSila() + STRENGTH_TO_ADD);
        }
    }

    @Override
    public Organizm clone() {
        return new Guarana(getPosition());
    }
}
