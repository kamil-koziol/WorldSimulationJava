package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;
import com.company.organizmy.rosliny.Roslina;

import java.awt.*;

public abstract class Zwierze extends Organizm {

    protected int zasieg_ruchu = 1;

    public Zwierze(Point position, int sila, int inicjatywa, Color color) {
        super(position, sila, inicjatywa, color);
    }

    public abstract Organizm clone();

    @Override
    public void kolizja(Swiat swiat, Organizm other) {
        System.out.println("KOLIZJA: " + this + " z " + other);
        if (other instanceof Zwierze zwierze) {
            if (this.getClass() == other.getClass()) {
                if (canBreed() && zwierze.canBreed()) {
                    breed(swiat, zwierze);
                }
            } else {
                fight(swiat, zwierze, true);
                zwierze.fight(swiat, this, false);
            }
        } else if (other instanceof Roslina roslina) {
            roslina.kolizja(swiat, this);
            swiat.usunOrganizm(roslina);
            setPosition(roslina.getPosition(), swiat);
        }
    }

    public boolean canBreed() {
        return this.getLifetime() != 0;
    }

    public void breed(Swiat swiat, Zwierze zwierze) {
        Point p;
        p = swiat.getRandomFreePointAround(getPosition(), zasieg_ruchu);
        if (p == null) {
            p = swiat.getRandomFreePointAround(zwierze.getPosition(), zasieg_ruchu);
        }

        if (p != null) {
            System.out.println("ROZMNAZANIE: " + this + " z " + zwierze);
            Organizm organizm = this.clone();
            organizm.setPosition(p, swiat);
            swiat.dodajOrganizm(organizm);
        }
    }

    public void fight(Swiat swiat, Organizm other, boolean attacking) {
        if (other instanceof IReflective o) {
            if (o.reflects(swiat, this)) {
                o.afterReflection(swiat, this);
                return;
            }
        }

        if (other instanceof IDodgeable o) {
            if (o.dodges(swiat, this)) {
                o.afterDodge(swiat, this);
                return;
            }
        }

        if(other instanceof IInvincible o) {
            if(o.isInvincible()) {
                return;
            }
        }

        if (getSila() == other.getSila()) {
            if (attacking) {
                swiat.usunOrganizm(other);
                setPosition(other.getPosition(), swiat);
            }
        } else if (getSila() > other.getSila()) {
            swiat.usunOrganizm(other);
            if (attacking) {
                setPosition(other.getPosition(), swiat);
            }
        }
    }

    @Override
    public void akcja(Swiat swiat) {
        setDoneTurn(true);
        Point newPoint = swiat.getRandomPointAround(getPosition(), 1);
        moveTo(swiat, newPoint);
    }

    public void moveTo(Swiat swiat, Point newPoint) {
        if (!swiat.inBounds(newPoint)) {
            return;
        }

        Organizm organizm = swiat.getOrganismAt(newPoint);
        if (organizm == null) {
            setPosition(newPoint, swiat);
        } else {
            kolizja(swiat, organizm);
        }
    }
}
