import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A person in the simulation.
 * 
 * @author Kabir Shah & Oleg Bychenkov
 * @version 1.0
 */
public class Person extends Actor
{
    private static final int CHANCE_SPREAD = 1;
    private static final int CHANCE_DEATH = 1;
    private static final int CHANCE_DEATH_WITHOUT_HOSPITAL = 90;
    private static final int CHANCE_HOME = 70;
    private static final int CHANCE_MASK = 70;
    private static final int STEPS_INFECTED = 100;
    private static final int VIRUS_PARTICLES = 8;
    private static final int VIRUS_PARTICLES_WITH_MASK = 2;
    private static final int HOSPITAL_CAPACITY = 100;
    
    private String state = "normal";
    private boolean isHome = false;
    private boolean hasMask = false;
    private int stepsInfected = 0;
    
    public static int numNormal = 0;
    public static int numInfected = 0;
    public static int numImmune = 0;
    public static int numDead = 0;
    
    public Person()
    {
        numNormal++;
        
        if (Greenfoot.getRandomNumber(100) < CHANCE_HOME) {
            stayHome();
        }
        
        if (Greenfoot.getRandomNumber(100) < CHANCE_MASK) {
            putOnMask();
        }
        
        faceRandomDirection();
    }
  
    /**
     * Set the infected state, image, and number.
     */
    public void infect() {
        if (isNormal()) {
            state = "infected";
            setImage("ppl3.png");
            numNormal--;
            numInfected++;
        }
    }
    
    /**
     * Set the healed state, image, and number.
     */
    public void heal() {
        if (isInfected()) {
            state = "immune";
            setImage("ppl2.png");
            numInfected--;
            numImmune++;
        }
    }
    
    /**
     * Set the killed state, image, and number.
     */
    public void kill() {
        if (isInfected()) {
            state = "dead";
            setImage("skull.png");
            numInfected--;
            numDead++;
        }
    }
    
    /**
     * Mark the person as staying at home.
     */
    public void stayHome() {
        isHome = true;
    }
    
    /**
     * Mark the person as one with a mask.
     */
    public void putOnMask() {
        hasMask = true;
    }
    
    /**
     * Check if the state is normal.
     */
    public boolean isNormal() {
        return state.equals("normal");
    }
    
    /**
     * Check if the state is infected.
     */
    public boolean isInfected() {
        return state.equals("infected");
    }
    
    /**
     * Check if the state is immune.
     */
    public boolean isImmune() {
        return state.equals("immune");
    }
    
    /**
     * Check if the state is dead.
     */
    public boolean isDead() {
        return state.equals("dead");
    }
    
    /**
     * Face a random angle.
     */
    public void faceRandomDirection() {
        turn(Greenfoot.getRandomNumber(360));
    }
    
    /**
     * Create virus particles according to if the person has a mask.
     */
    public void spread() {
        for (
            int i = 0;
            i < 360;
            i += 360 / (hasMask ? VIRUS_PARTICLES_WITH_MASK : VIRUS_PARTICLES)
        ) {
            getWorld().addObject(
                new Virus(i),
                getX(),
                getY()
            );
        }
    }
    
    /**
     * If infected, spread the virus. At the end of the infected lifetime, kill with the mortality rate
     * depending on the hospital occupancy or heal and become immune. If not dead and not staying at home,
     * then move.
     */
    public void act() 
    {
        if (isInfected()) {
            if (Greenfoot.getRandomNumber(100) <= CHANCE_SPREAD) {
                spread();
            }
            
            if (stepsInfected > STEPS_INFECTED) {
                if (Greenfoot.getRandomNumber(100) <= (numInfected < HOSPITAL_CAPACITY ? CHANCE_DEATH : CHANCE_DEATH_WITHOUT_HOSPITAL)) {
                    kill();
                } else {
                    heal();
                }
            }
            
            stepsInfected++;
        }

        if (!isDead()) {
            if (!isHome) {
                move(3);
            }
            
            if (isAtEdge()) {
                faceRandomDirection();
            }    
        }
    }
}
