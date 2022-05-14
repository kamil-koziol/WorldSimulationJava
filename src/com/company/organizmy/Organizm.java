package com.company.organizmy;

import com.company.Swiat;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public abstract class Organizm {
    private int lifetime = 0;
    private Point position;
    private int sila;
    private int inicjatywa;
    private int id = 0;
    protected Color color;
    private boolean doneTurn = false;
    private boolean dead = false;

    public Organizm(Point position, int sila, int inicjatywa, Color color) {
        this.position = position;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.color = color;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean canDoTurn() {
        return !hasDoneTurn() && lifetime > 0 && !dead;
    }

    public abstract void kolizja(Swiat swiat, Organizm other);

    public abstract void akcja(Swiat swiat);

    public void rysowanie(Swiat swiat, Graphics g) {
        g.setColor(color);
        g.fillRect(position.x * swiat.getScale(), position.y * swiat.getScale(), swiat.getScale(), swiat.getScale());
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position, Swiat swiat) {
        System.out.println("RUCH: " + this + " do " + position);
        this.position = position;
        swiat.updateBoard();
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                this.getClass().getSimpleName() + "{" +
                        "lifetime=" + lifetime +
                        ", position=" + position +
                        ", sila=" + sila +
                        ", id=" + id +
                        '}';
    }

    public String toSaveFile() {
        String save = getClass().getSimpleName() + " " + lifetime + " " + position.x + " " + position.y + " " + sila;
        return save;
    }

    public boolean hasDoneTurn() {
        return doneTurn;
    }

    public void setDoneTurn(boolean doneTurn) {
        this.doneTurn = doneTurn;
    }

    public abstract Organizm clone();

    public static class OrganizmComparator implements Comparator<Organizm> {
        @Override
        public int compare(Organizm o1, Organizm o2) {
            if (o1.getInicjatywa() == o2.getInicjatywa()) {
                return o2.getLifetime() - o1.getLifetime();
            }

            return o2.getInicjatywa() - o1.getInicjatywa();
        }
    }

    public void loadFromFileLine(String[] parameters) {
        String name = parameters[0];
        lifetime = Integer.parseInt(parameters[1]);
        int x = Integer.parseInt(parameters[2]);
        int y = Integer.parseInt(parameters[3]);
        position.setLocation(new Point(x, y));
        sila = Integer.parseInt(parameters[4]);
    }

    public Organizm clone() {
        try {
            return (Organizm) this.getClass().getEnclosingConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
