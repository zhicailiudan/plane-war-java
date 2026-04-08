package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletItem extends AbstractItem {

    public BulletItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, speedY);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        hero.powerUp(1);
        System.out.println("FireSupply active!");
    }

    @Override
    public String getItemTypeName() {
        return "BulletUp";
    }
}
