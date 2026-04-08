package item
import edu.hitsz.aircraft.HeroAircraft;


public class BloddItem extends AbstractItem(){
    public BloodItem(int locationX, int locationY){

        super(locationX, locationY, 1);
    }

    @Override
    public void applyEffect(HeroAircraft hero) {
        hero.addHp(50);
        System.out.println("获得血包，生命值+50");
    }

}