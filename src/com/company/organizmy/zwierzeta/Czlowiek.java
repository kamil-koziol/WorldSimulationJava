package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.organizmy.Organizm;
import com.company.utils.Direction;

import java.awt.*;

public class Czlowiek extends Zwierze implements IInvincible{

    private Direction direction = Direction.RIGHT;
    private int abilityCooldown = 0;
    private int abilityTurnsRemaining = 0;

    public static int ABILITY_COOLDOWN = 5;
    public static int ABILITY_DURATION = 5;

    public Czlowiek() {
        this(new Point(0,0));
    }
    public Czlowiek(Point position) {
        super(position, 5, 4, Color.black);
    }

    @Override
    public Organizm clone() {
        return new Czlowiek(getPosition());
    }

    @Override
    public void akcja(Swiat swiat) {
        setDoneTurn(true);
        Point p = (Point) getPosition().clone();
        switch (direction) {
            case UP -> {
                p.y--;
            }
            case DOWN -> {
                p.y++;
            }
            case LEFT -> {
                p.x--;
            }
            case RIGHT -> {
                p.x++;
            }
        }
        moveTo(swiat, p);


    }

    public void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case 37 -> {
                direction = Direction.LEFT;
            }

            case 38 -> {
                direction = Direction.UP;
            }

            case 39 -> {
                direction = Direction.RIGHT;
            }

            case 40 -> {
                direction = Direction.DOWN;
            }

            case 32 -> {
                if(canUseAbility()) {
                    useAbility();
                }
            }
        }

        handleAbility();
    }

    public boolean canUseAbility() {
        return abilityCooldown == 0 && abilityTurnsRemaining == 0;
    }
    public void useAbility() {
        abilityTurnsRemaining = ABILITY_DURATION;
    }

    public void handleAbility() {
        if(isAbilityActive()) {
            color = Color.green;
            abilityTurnsRemaining--;
            if(abilityTurnsRemaining == 0) {
                abilityCooldown = ABILITY_COOLDOWN;
            }
        } else {
            color = Color.BLACK;
        }

        if(abilityCooldown > 0) {
            abilityCooldown--;
        }
    }

    public boolean isAbilityActive() {
        return abilityTurnsRemaining > 0;
    }

    @Override
    public boolean isInvincible() {
        return isAbilityActive();
    }

    @Override
    public String toSaveFile() {
        return super.toSaveFile() + " " + abilityCooldown + " " + abilityTurnsRemaining;
    }


    @Override
    public void loadFromFileLine(String[] parameters) {
        super.loadFromFileLine(parameters);
        abilityCooldown = Integer.parseInt(parameters[5]);
        abilityTurnsRemaining = Integer.parseInt(parameters[6]);
    }
}
