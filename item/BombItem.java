package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;

public class BombItem extends AbstractItem {

    public BombItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, speedY);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        System.out.println("Bomb active! Clear all enemies!");
    }

    @Override
    public String getItemTypeName() {
        return "Bomb";
    }
}
