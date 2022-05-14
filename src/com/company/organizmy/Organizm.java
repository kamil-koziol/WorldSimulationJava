package com.company.organizmy;

import com.company.Swiat;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Organizm implements Encodable{
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
                        "id=" + id +
                        '}';
    }

    public boolean hasDoneTurn() {
        return doneTurn;
    }

    public void setDoneTurn(boolean doneTurn) {
        this.doneTurn = doneTurn;
    }

    @Override
    public ArrayList<String> getParameters() {
        ArrayList<String> parameters = new ArrayList<String>(
                Arrays.asList(getClass().getSimpleName(),
                        String.valueOf(lifetime),
                        String.valueOf(position.x),
                        String.valueOf(position.y),
                        String.valueOf(sila)
                )
        );
        return parameters;
    }

    @Override
    public String encodeToString(String delimeter) {
        return String.join(delimeter, getParameters());
    }

    @Override
    public void decodeFromString(String line, String delimeter) {
        String[] parameters = line.split(delimeter);
        String name = parameters[0];
        lifetime = Integer.parseInt(parameters[1]);
        int x = Integer.parseInt(parameters[2]);
        int y = Integer.parseInt(parameters[3]);
        position.setLocation(new Point(x, y));
        sila = Integer.parseInt(parameters[4]);
    }

    public static class OrganizmComparator implements Comparator<Organizm> {
        @Override
        public int compare(Organizm o1, Organizm o2) {
            if (o1.getInicjatywa() == o2.getInicjatywa()) {
                return o2.getLifetime() - o1.getLifetime();
            }

            return o2.getInicjatywa() - o1.getInicjatywa();
        }
    }

    public Organizm getEmptyCopy() {
        try {
            Organizm organizm = (Organizm) this.getClass().getConstructor().newInstance();
            return organizm;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
