package src.player;

import java.awt.*;
import java.util.ArrayList;

import src.game.ActionLog;
import src.game.AvailableActions;
import src.game.Manager;
import src.panel.DicePanel;
import src.panel.MainRightPanel;

import static src.game.CONSTANTS.NB_HORSES;
import static src.game.CONSTANTS.MAX_LENGTH;
import static src.game.AvailableActions.actions.*;

public abstract class Player {
  private final String name;
  private final Color  color;
  private final Horse[] horses = {
    new Horse(), new Horse(),
    new Horse(), new Horse()
  };

  public Horse[] getHorses() {
    return horses;
  }

  public Player(String name, Color color) {
    this.name  = name;
    this.color = color;
  }

  public int horsesIn() {
    int res = 0;
    for (Horse h : horses) if (h.isInBarns()) res++;
    return res;
  }

  public int horsesOut() {
    return NB_HORSES - horsesIn();
  }

  public String getName() {
    return name;
  }

  /**
   * Get the number of horse in the next case the horse will be
   * @param diceValue
   * @return int
   */
  public int upcomingHorses(int myLength) {
    // each player is schifted by 12 cells
    int shift = 13;
    int nbH = 0;
    int myGlobalLength = 0;

    if (name == Manager.playerList.get(0).getName()) myGlobalLength = myLength;
    else if (name == Manager.playerList.get(1).getName()) myGlobalLength = myLength + shift;
    else if (name == Manager.playerList.get(2).getName()) myGlobalLength = myLength + shift*2;
    else if (name == Manager.playerList.get(3).getName()) myGlobalLength = myLength + shift*3;
    
    if (myGlobalLength > MAX_LENGTH + 1) myGlobalLength -= MAX_LENGTH+1;

    System.out.println(myGlobalLength);

    for (int p = 0; p < Manager.playerList.size(); p++) {
      Player player = Manager.playerList.get(p);
      for (int h = 0; h < player.getHorses().length; h++) {
        Horse playerHorse = player.getHorses()[h];
        
        int horseGlobalLength = playerHorse.getLength() + shift * p;
        if (horseGlobalLength > MAX_LENGTH+1) horseGlobalLength -= MAX_LENGTH+1;

        if (!playerHorse.isInBarns() && horseGlobalLength == myGlobalLength) {
          System.out.println("player: " + player.getName() + " horse: " + h + " myGlobalLength: " + horseGlobalLength);
          nbH++;
        }
      }
    }

    return nbH;
  }

  public AvailableActions[] availableActions(int diceValue) {
    ArrayList<AvailableActions> availableActions = new ArrayList<>(horses.length);

    for (int i = 0; i < horses.length; i++) {
      Horse h = horses[i];
      if (h.isInBarns() && diceValue == 6) availableActions.add(new AvailableActions(BARNS_OUT, i));
      else {
        if (h.canStairs()) availableActions.add(new AvailableActions(STAIRS_UP, i));
        else if (!h.isInBarns() && h.getLength() != MAX_LENGTH) {
        
          /* check for others horses in the upcoming cell:
           * if there is 2 horses, the horse can't go to this case and pass (block)
           */
          if (upcomingHorses(h.getLength() + diceValue) < 2) availableActions.add(new AvailableActions(MOVE, i));
        }
      }
    }

    availableActions.sort(new AvailableActions(MOVE, 0));
    return availableActions.toArray(new AvailableActions[0]);
  }

  public void act(AvailableActions action) {
    Horse h = horses[action.pawnIndex];
    String content = "Horse " + (action.pawnIndex + 1) + ": ";
    switch (action.action) {
    case STAIRS_UP:
      h.stairs();
      content += h.isGoalReached() ? "join the house!" : "stairs,  " + h.getStairs() + " left.";
      break;
    case BARNS_OUT:
      h.setInBarns(false);
      content += "out of barn.";
      break;
    case MOVE:
      content += "take " + DicePanel.getLastDice() + " steps.";
      h.move();
      break;
    default:
      break;
    }

    MainRightPanel.addLog(new ActionLog(color, name, content));
    Manager.actionEnded(this);
  }

  public Color getColor() {
    return color;
  }
}