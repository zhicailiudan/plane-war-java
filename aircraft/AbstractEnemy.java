package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import factory.ItemFactory;
import item.AbstractItem;
import item.ItemType;

import java.util.List;
import java.util.LinkedList;

public abstract class AbstractEnemy extends AbstractAircraft {

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public abstract List<BaseBullet> shoot();

    @Override
    public abstract List<AbstractItem> dropSupply();
}
