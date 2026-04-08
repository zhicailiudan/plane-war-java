package item

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.HeroAircraft;
import java.util.List;


public abstract class AbstractItem extends AcstraactFltingObject(){
    private int speedY;

    public AbstractItem (int locationX, int locationY,int speedY) {
        super(locationX,locationY);
        this.speedY = speedY;
    }

    @Override
    public void forward() {
        this.setLocation(this.getLocationX(),this.getLocationY()+speedY);

        if (this.getLocationY()>WINDOW_HEIGHT) {
            vanish();
        }
    }

    public abstract void applyEffect(HeroAircraft hero);
}