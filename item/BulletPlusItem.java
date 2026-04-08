package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletPlusItem extends AbstractItem {

    public BulletPlusItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, speedY);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        hero.powerUp(3);
        System.out.println("SuperFireSupply active!");
    }

    @Override
    public String getItemTypeName() {
        return "BulletPlus";
    }
}
