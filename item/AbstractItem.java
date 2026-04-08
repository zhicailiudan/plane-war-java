package edu.hitsz.item;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.HeroAircraft;

public abstract class AbstractItem extends AbstractFlyingObject {

    private int speedY;

    public AbstractItem(int locationX, int locationY, int speedY) {
        super(locationX, locationY, 0, speedY);
        this.speedY = speedY;
    }

    @Override
    public void forward() {
        locationY += speedY;
        if (locationY > WINDOW_HEIGHT) {
            vanish();
        }
    }

    public abstract void applyEffect(HeroAircraft hero);

    public abstract String getItemTypeName();
}
