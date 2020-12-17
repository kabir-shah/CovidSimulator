import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Virus here.
 * 
 * @author Kabir Shah & Oleg Bychenkov
 * @version 1.0
 */
public class Virus extends Actor
{
    private static final int STEPS_ACTIVE = 50;
    private int stepsActive = 0;
    
    public Virus(int rotation) {
        setRotation(rotation);
    }
    
    /**
     * Face a random angle.
     */
    public void faceRandomDirection() {
        turn(Greenfoot.getRandomNumber(360));
    }
    
    /**
     * Infect anyone that is touching the particle and remove the virus after it has gone past its lifespan.
     */
    public void act() 
    {    
        List<Person> people =
            (List<Person>) getIntersectingObjects(Person.class);
        
        for (Person person : people) {
            person.infect();
        }
        
        move(1);
        
        if (isAtEdge()) {
            faceRandomDirection();
        }
        
        if (stepsActive > STEPS_ACTIVE) {
            getWorld().removeObject(this);
        }
        
        stepsActive++;
    }    
}
