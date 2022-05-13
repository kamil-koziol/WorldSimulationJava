package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;

public interface IDodgeable {
    boolean dodges(Swiat swiat, Organizm other);

    void afterDodge(Swiat swiat, Organizm other);
}
