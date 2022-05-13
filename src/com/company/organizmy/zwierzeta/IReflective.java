package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;

public interface IReflective {
    boolean reflects(Swiat swiat, Organizm other);

    void afterReflection(Swiat swiat, Organizm other);
}
