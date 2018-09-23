package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    /** fraction of energy to retain when replicating. */
    private double repEnergyRetained = 0.5;
    /** fraction of energy to bestow upon offspring. */
    private double repEnergyGiven = 0.5;




    public Clorus(double e){
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public void move(){
        energy = energy - 0.03;
    }

    public void stay(){
        energy = energy - 0.01;
    }

    public Color color(){
        return color(r, g, b);
    }

    public void attack(Creature c){
        energy = energy + c.energy();
    }

    public Clorus replicate(){
        energy = energy * repEnergyRetained;
        double babyEnergy = energy * repEnergyGiven;
        return new Clorus(babyEnergy);
    }


    /** If there are no empty squares, the Clorus will STAY
     *  (even if there are Plips nearby they could attack).
     Otherwise, if any Plips are seen, the Clorus will ATTACK
     one of them randomly.Otherwise, if the Clorus has energy
     greater than or equal to one, it will REPLICATE to a random
     empty square.Otherwise, the Clorus will MOVE to a random empty
     square.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors){
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0){
            return new Action(Action.ActionType.STAY);
        }
        if(!plips.isEmpty()) {
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }
        if(energy >= 1){
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}

