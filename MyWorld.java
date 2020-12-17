import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The simulation world.
 * 
 * @author Kabir Shah & Oleg Bychenkov
 * @version 1.0
 */
public class MyWorld extends World
{
    private static final int NUMBER_OF_PEOPLE = 300;
    private int xOffset = 0;

    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld()
    {    
        super(1000, 600, 1);
        reset();
        init();
    }
    
    /**
     * Create an infected person and add people.
     */
    public void init() {
        Person infected = new Person();
        infected.infect();
         
        addObject(
            infected,
            getWidth() / 2,
            getHeight() / 2
        );
        
        infected.spread();
        
        for (int i = 0; i < NUMBER_OF_PEOPLE; i++) {
            addObject(
                new Person(),
                Greenfoot.getRandomNumber(getWidth()),
                Greenfoot.getRandomNumber(getHeight())
            );
        }
    }
    
    /**
     * Reset static variables and remove all actors from world.
     */
    public void reset() {
        xOffset = 0;
        Person.numNormal = 0;
        Person.numInfected = 0;
        Person.numImmune = 0;
        Person.numDead = 0;
        removeObjects(getObjects(Person.class));
        removeObjects(getObjects(Virus.class));
    }
    
    /**
     * Show text info in the top and show graph.
     */
    public void act() {
        showText(
            "Normal: " + Person.numNormal + "   " +
            "Infected: " + Person.numInfected + "   " +
            "Immune: " + Person.numImmune + "   " +
            "Dead: " + Person.numDead,
            300, 10
        );
        
        int value = Person.numInfected * 3;
        int yOffset = getHeight() - value;
        getBackground().drawLine(xOffset, yOffset, xOffset, yOffset - 4);
        xOffset++;
        
        if (xOffset > getWidth()) {
            reset();
            init();
        }
    }
}
