package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodItem extends AbstractItem {

    public BloodItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, speedY);
    }

    public BloodItem(int locationX, int locationY) {
        this(locationX, locationY, 5);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        hero.setHp(Math.min(hero.getHp() + 50, hero.getMaxHp()));
    }

    @Override
    public String getItemTypeName() {
        return "Blood";
    }
}
