package com.company.organizmy.zwierzeta;

import com.company.organizmy.Organizm;

import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Point position) {
        super(position, 4, 4, Color.GRAY);
    }

    @Override
    public Organizm clone() {
        return new Owca(getPosition());
    }
}
