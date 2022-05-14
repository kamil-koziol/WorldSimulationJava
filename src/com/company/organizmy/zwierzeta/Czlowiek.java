package com.company.organizmy.zwierzeta;

import com.company.Swiat;
import com.company.utils.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Czlowiek extends Zwierze implements IInvincible {

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
    public ArrayList<String> getParameters() {
        ArrayList<String> parameters = super.getParameters();
        parameters.add(String.valueOf(abilityCooldown));
        parameters.add(String.valueOf(abilityTurnsRemaining));
        return parameters;
    }

    @Override
    public void decodeFromString(String line, String delimeter) {
        super.decodeFromString(line, delimeter);
        String[] parameters = line.split(delimeter);
        abilityCooldown = Integer.parseInt(parameters[parameters.length - 2]);
        abilityTurnsRemaining = Integer.parseInt(parameters[parameters.length - 1]);
    }
}
