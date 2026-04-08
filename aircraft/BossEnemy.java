package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**Boss敌机
 * 可射击，悬浮于上方，仅左右移动
 */

public class BossEnemy() extends AbstractAircraft {
    public BossEnemy (int locationX,int locationY,int speedX,int speedY,int hp) {
        super(locationX, locationY, peedX, speedY, hp);
    }
}