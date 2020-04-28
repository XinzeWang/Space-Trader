package pineapple.model;

import java.util.Stack;

public class Game {
    //TODO game class not completed implemented
    private Player player;

    //@XYZ previous visited regions
    private  Stack<Region> visitedRegs;
    private Region curReg;
    private Region nextReg;
    //@XYZ
    /* --- Constructions --- */
    public Game(Player player) {
        this.player = player;
        visitedRegs = new Stack<>();
    }

    public Player getPlayer() {
        return player;
    }

    public Stack getVisitedRegs() {
        return visitedRegs;
    }

    public void pushVisitedRegs(Region region) {
        visitedRegs.push(region);
    }

    public Region popVisitedRegs() {
        return visitedRegs.pop();
    }

    public Region peekVisitedRegs() {
        return visitedRegs.peek();
    }

    public Region getCurReg() {
        return curReg;
    }

    public void setCurReg(Region curReg) {
        this.curReg = curReg;
    }

    public Region getNextReg() {
        return nextReg;
    }

    public void setNextReg(Region nextReg) {
        this.nextReg = nextReg;
    }
}
