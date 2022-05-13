package com.company.organizmy.rosliny;

import com.company.Swiat;
import com.company.organizmy.Organizm;
import com.company.organizmy.zwierzeta.IInvincible;
import com.company.organizmy.zwierzeta.Zwierze;

import java.awt.*;

public class WilczeJagody extends Roslina {
    public WilczeJagody(Point position) {
        super(position, 99, Color.MAGENTA);
    }

    @Override
    public void kolizja(Swiat swiat, Organizm other) {
        if (other instanceof Zwierze zwierze) {
            swiat.usunOrganizm(zwierze);
        }
    }

    @Override
    public Organizm clone() {
        return new WilczeJagody(getPosition());
    }
}
