package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;

public class FreezeItem extends AbstractItem {

    public FreezeItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, speedY);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        System.out.println("Freeze active! Freeze all enemies!");
    }

    @Override
    public String getItemTypeName() {
        return "Freeze";
    }
}
