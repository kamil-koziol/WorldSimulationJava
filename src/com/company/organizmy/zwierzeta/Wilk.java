package com.company.organizmy.zwierzeta;

import com.company.organizmy.Organizm;

import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk() {this(new Point(0,0));}
    public Wilk(Point position) {
        super(position, 9, 5, Color.darkGray);
    }
}
