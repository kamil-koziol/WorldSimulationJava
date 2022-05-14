package com.company;

import com.company.organizmy.Organizm;
import com.company.organizmy.rosliny.*;
import com.company.organizmy.zwierzeta.*;
import com.company.utils.ObjectRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Swiat extends JPanel {
    Organizm[][] board;
    String selectedOrganizm;
    private Dimension dimensions;
    private int scale;
    private int organismsAdded = 0;
    private ArrayList<Organizm> organizmy = new ArrayList<Organizm>();

    public ObjectRegister<Organizm> getOrganizmRegister() {
        return organizmRegister;
    }

    private ObjectRegister<Organizm> organizmRegister;

    public Swiat(Dimension dimensions, int scale) {
        this.dimensions = dimensions;
        this.scale = scale;
        board = new Organizm[dimensions.height][dimensions.width];

        organizmRegister = new ObjectRegister<>();
        organizmRegister.add(BarszczSosnowskiego.class);
        organizmRegister.add(Guarana.class);
        organizmRegister.add(Mlecz.class);
        organizmRegister.add(Trawa.class);
        organizmRegister.add(WilczeJagody.class);

        organizmRegister.add(Antylopa.class);
        organizmRegister.add(Lis.class);
        organizmRegister.add(Owca.class);
        organizmRegister.add(Wilk.class);
        organizmRegister.add(Zolw.class);
        organizmRegister.add(Czlowiek.class);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        rysujSwiat(g);
    }

    public void wykonajTure() {

        Collections.sort(organizmy, new Organizm.OrganizmComparator());


        for (int i = 0; i < organizmy.size(); i++) {
            Organizm organizm = organizmy.get(i);
            if (!organizm.canDoTurn()) {
                continue;
            }

            organizm.akcja(this);
        }

        organizmy.removeIf(organizm -> organizm.isDead());

        for (Organizm organizm : organizmy) {
            organizm.setLifetime(organizm.getLifetime() + 1);
            organizm.setDoneTurn(false);
        }

    }


    public void rysujSwiat(Graphics g) {
        repaint();
        for (Organizm organizm : organizmy) {
            organizm.rysowanie(this, g);
        }
    }


    public void dodajOrganizm(Organizm organizm) {
        if(!organizmRegister.checkClass(organizm.getClass())) {
            return;
        }
        organizm.setId(organismsAdded);
        organismsAdded++;
        organizmy.add(organizm);
        updateBoard();
    }

    public void usunOrganizm(Organizm organizm) {
        if(organizm instanceof IInvincible o) {
            if(o.isInvincible()) {
                return;
            }
        }
        organizm.setDead(true);
        updateBoard();
    }

    public Organizm getOrganismAt(Point point) {
        if (!inBounds(point)) {
            return null;
        }

        return board[point.y][point.x];
    }

    public boolean inBounds(Point point) {
        return point.x >= 0 && point.x < dimensions.width && point.y >= 0 && point.y < dimensions.height;
    }

    public ArrayList<Point> getPointsAround(Point point, int range) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (int y = -range; y <= range; y++) {
            for (int x = -range; x <= range; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Point newPoint = new Point(point.x + x, point.y + y);
                if (inBounds(newPoint)) {
                    points.add(newPoint);
                }
            }
        }

        return points;
    }

    public Point getRandomPointAround(Point point, int range) {
        ArrayList<Point> points = getPointsAround(point, range);
        if (points.isEmpty()) {
            return null;
        }

        Random r = new Random();
        int randomIndex = r.nextInt(points.size());
        return points.get(randomIndex);
    }

    public Point getRandomFreePointAround(Point point, int range) {
        ArrayList<Point> points = getPointsAround(point, range);
        if (points.isEmpty()) {
            return null;
        }

        ArrayList<Point> freePoints = new ArrayList<Point>();
        for (Point p : points) {
            if (getOrganismAt(p) == null) {
                freePoints.add(p);
            }
        }

        if (freePoints.isEmpty()) {
            return null;
        }

        Random r = new Random();
        int randomIndex = r.nextInt(freePoints.size());
        return freePoints.get(randomIndex);
    }

    public void updateBoard() {
        for (int y = 0; y < dimensions.height; y++) {
            for (int x = 0; x < dimensions.width; x++) {
                board[y][x] = null;
            }
        }

        for (Organizm organizm : organizmy) {
            if (!organizm.isDead()) {
                board[organizm.getPosition().y][organizm.getPosition().x] = organizm;
            }
        }
    }

    public int getScale() {
        return scale;
    }

    public ArrayList<Organizm> getAllOrganismsTypes() {
        ArrayList<Organizm> organizmArrayList = new ArrayList<Organizm>();

        Point startingPoint = new Point(0, 0);

        organizmArrayList.add(new BarszczSosnowskiego(startingPoint));
        organizmArrayList.add(new Guarana(startingPoint));
        organizmArrayList.add(new Mlecz(startingPoint));
        organizmArrayList.add(new Trawa(startingPoint));
        organizmArrayList.add(new WilczeJagody(startingPoint));

        organizmArrayList.add(new Antylopa(startingPoint));
        organizmArrayList.add(new Lis(startingPoint));
        organizmArrayList.add(new Owca(startingPoint));
        organizmArrayList.add(new Wilk(startingPoint));
        organizmArrayList.add(new Zolw(startingPoint));
        organizmArrayList.add(new Czlowiek(startingPoint));

        return organizmArrayList;
    }

    public void generateRandomWorld() {

        Random r = new Random();
        for (Organizm organizm : organizmRegister.getInstancesOfAllClasses()) {
            int amountOfAnimalsToAdd = r.nextInt(10);
            for (int i = 0; i < amountOfAnimalsToAdd; i++) {
                if (organizm instanceof BarszczSosnowskiego) {
                    break;
                }
                Point randomPoint = getRandomFreePointAround(new Point(r.nextInt(dimensions.width), r.nextInt(dimensions.height)), dimensions.width + dimensions.height);
                Organizm o = organizm.getEmptyCopy();
                o.setPosition(randomPoint, this);
                dodajOrganizm(o);
                if (organizm instanceof Czlowiek) {
                    break;
                }
            }
        }
    }

    public void handleKeyPress(KeyEvent e) {
        for (Organizm organizm : organizmy) {
            if (organizm instanceof Czlowiek czlowiek) {
                czlowiek.handleKeyPress(e.getKeyCode());
            }
        }

        wykonajTure();
        rysujSwiat(getGraphics());
    }

    public void handleMousePress(MouseEvent e) {
        Point translated = translatePointToWorld(e.getPoint());
        if (!inBounds(translated)) {
            return;
        }
        if (getOrganismAt(translated) == null) {
            if (!selectedOrganizm.equals("")) {
                Organizm organizm = organizmRegister.getInstanceOfName(selectedOrganizm);
                organizm.setPosition(translated, this);
                dodajOrganizm(organizm);
                organizm.rysowanie(this, getGraphics());
            }
        }
    }

    public void handleNewRoundButtonPress(ActionEvent e) {
        wykonajTure();
        rysujSwiat(getGraphics());
    }

    public void handleOrganismSelectorChange(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String petName = (String) cb.getSelectedItem();
        selectedOrganizm = petName;
    }

    public Point translatePointToWorld(Point point) {
        int translatedX = point.x / scale;
        int translatedY = point.y / scale;
        return new Point(translatedX, translatedY);
    }

    public void makeSave(String fileName) {
        fileName += ".save";
        Path p = Paths.get(".", "saves", fileName);
        try {
            FileWriter fileWriter = new FileWriter(p.toString());
            fileWriter.write("Zapis gry\n");
            fileWriter.write("Pola:\n");
            fileWriter.write("Nazwa ogarnizmu / Dlugość Życia / Punkt X / Punkt Y / Siła\n");
            for(Organizm organizm: organizmy) {
                fileWriter.write(organizm.encodeToString(" ") + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            // file exists
        }
    }

    public void loadFromFile(String fileName) {
        File f = new File(fileName);
        try {
            Scanner reader = new Scanner(f);
            organizmy.clear();

            // skip first three lines
            for(int i=0; i<3; i++) {
                reader.nextLine();
            }
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parameters = line.split(" ");
                Organizm organizm = organizmRegister.getInstanceOfName(parameters[0]);
                organizm.decodeFromString(line, " ");
                dodajOrganizm(organizm);

            }

            rysujSwiat(getGraphics());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
