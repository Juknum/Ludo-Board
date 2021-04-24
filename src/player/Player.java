package player;

import java.awt.*;
import java.util.ArrayList;

import game.ActionLog;
import game.AvailableActions;
import game.Manager;
import panel.DicePanel;
import panel.MainRightPanel;

import static game.CONSTANTS.NB_HORSES;
import static game.CONSTANTS.MAX_LENGTH;
import static game.CONSTANTS.SHIFT;
import static game.CONSTANTS.DEBUG;
import static game.AvailableActions.actions.*;

public abstract class Player {
  private final String name;
  private final Color  color;
  private final Horse[] horses = {
    new Horse(), new Horse(),
    new Horse(), new Horse()
  };
  
  public boolean hasEatenSomeone = false;
  public int order = 0;

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

  public int horsesInStairs() {
    int res = 0;
    for (Horse h : horses) if (h.isInStairs()) res++;
    return res;
  }

  public int horsesInHome() {
    int res = 0;
    for (Horse h : horses) if (h.isGoalReached()) res++;
    return res;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder() {
    for (int i = 0; i < Manager.playerList.size(); i++) {
      Player p = Manager.playerList.get(i);
      int th = 0;
      int o = 0;

      for (int j = 0; j < p.getHorses().length; j++) {
        Horse h = p.getHorses()[j];
        if (h.isGoalReached()) th++;
      }

      if (th == NB_HORSES && i < Manager.currentPlayerIndex) {
        order++;
      }
    }
  }

  /**
   * Get the number of horse in the next case the horse will be
   * @param diceValue
   * @return int
   */
  public int getUpcomingHorses(int globalLength) {
    int nbHorse = 0;

    for (int p = 0; p < Manager.playerList.size(); p++) {
      Player player = Manager.playerList.get(p);
      
      for (int h = 0; h < NB_HORSES; h++) {
        Horse playerHorse = player.getHorses()[h];

        if (!playerHorse.isInBarns() && getGlobalLengthOf(playerHorse.getLength(), p) == globalLength) nbHorse++;
      }
    }

    return nbHorse;
  }

  /**
   * Check if a horse can remove others horses from the next tile
   * @param globalLength int
   * @return boolean
   */
  public boolean canRemoveHorse(int globalLength) {
    if (getUpcomingHorses(globalLength) != 1) return false;

    switch (globalLength) {
      // safe zone
      case 0: case 8: case 13: case 21: case 26: case 34: case 39: case 47:
        return false;
      default:
        if (color == upcomingPawn(globalLength)) return false;
        else return true;
    }
  }

  /**
   * Remove horses from a given global length (tile)
   * @param globalLength int
   */
  public void removeHorse(int globalLength) {
    int nbHorseRemoved = 0;

    for (int p = 0; p < Manager.playerList.size(); p++) {
      Player player = Manager.playerList.get(p);

      for (int h = 0; h < NB_HORSES; h++) {
        Horse playerHorse = player.getHorses()[h];

        if (getGlobalLengthOf(playerHorse.getLength(), p) == globalLength && !playerHorse.isInStairs()) {
          playerHorse.setInBarns(true);
          nbHorseRemoved++;
        }
      }
    }

    if (nbHorseRemoved > 0) hasEatenSomeone = true;
  }

  /**
   * get the color of the upcoming horse
   * @param globalLength int
   * @return Color
   */
  public Color upcomingPawn(int globalLength) {
    for (int p = 0; p < Manager.playerList.size(); p++) {
      Player player = Manager.playerList.get(p);

      for (int h = 0; h < NB_HORSES; h++) {
        Horse playerHorse = player.getHorses()[h];

        if (getGlobalLengthOf(playerHorse.getLength(), p) == globalLength) return player.getColor();
      }
    }

    return null;
  }

  /**
   * Get global length (0-51 from the yellow start) of a given player horse
   * @param length int
   * @param playerIndex int
   * @return int
   */
  public int getGlobalLengthOf(int length, int playerIndex) {
    int globalLength = length + SHIFT * playerIndex;
    if (globalLength > MAX_LENGTH + 1) globalLength -= MAX_LENGTH + 2;

    return globalLength;
  }

  /**
   * Get global length of the current player horse
   * @param length int
   * @return int
   */
  public int getGlobalLength(int length) {
    int globalLength = 0;
    if (color == Manager.playerList.get(0).getColor()) globalLength = length;
    else if (color == Manager.playerList.get(1).getColor()) globalLength = length + SHIFT;
    else if (color == Manager.playerList.get(2).getColor()) globalLength = length + SHIFT * 2;
    else if (color == Manager.playerList.get(3).getColor()) globalLength = length + SHIFT * 3;

    if (globalLength > MAX_LENGTH + 1) globalLength -= MAX_LENGTH + 2;

    return globalLength;
  }

  public AvailableActions[] availableActions(int diceValue) {
    ArrayList<AvailableActions> availableActions = new ArrayList<>(horses.length);

    System.out.println( name + ":\th1: " + horses[0].canStairs() + "\th2: " + horses[1].canStairs() + "\th3: " + horses[2].canStairs() + "\th4: " + horses[3].canStairs());

    for (int i = 0; i < horses.length; i++) {
      Horse h = horses[i];

      if (h.isInBarns() && diceValue == 6) availableActions.add(new AvailableActions(BARNS_OUT, i));
      else {
        if (h.canStairs() && hasEatenSomeone) availableActions.add(new AvailableActions(STAIRS_UP, i));
        else if (!h.isInBarns() && !h.isInStairs() && h.getLength() < MAX_LENGTH) {
        
          /* check for others horses in the upcoming cell:
          * if there is 2 horses, the horse can't go to this case and pass (block)
          */
          int nextLength = getGlobalLength(h.getLength() + diceValue);

          if (getUpcomingHorses(nextLength) < 2) {
            if (canRemoveHorse(nextLength)) availableActions.add(new AvailableActions(JUMP_HORSE, i));
            else availableActions.add(new AvailableActions(MOVE, i));
          }
        }
      }
    }

    availableActions.sort(new AvailableActions(MOVE, 0));
    return availableActions.toArray(new AvailableActions[0]);
  }

  public void act(AvailableActions action) {
    Horse h = horses[action.pawnIndex];
    String content = "Horse " + (action.pawnIndex + 1) + ": ";

    int diceValue = DicePanel.getLastDice();
    switch (action.action) {
      case STAIRS_UP:
        h.stairs();
        content += h.isGoalReached() ? "join the home!" : "stairs,  " + (h.getStairs()-1) + " left.";
        break;
      case BARNS_OUT:
        h.setInBarns(false);
        content += "out of barn.";
        break;
      case MOVE:
        content += "take " + diceValue + " steps.";
        h.move(hasEatenSomeone);
        break;
      case JUMP_HORSE:
        content += "take " + diceValue + " steps and<br>remove the oposant horse.";
        removeHorse(getGlobalLength(h.getLength() + diceValue));
        h.move(hasEatenSomeone);
        break;
      default:
        break;
    }
    
    if (DEBUG) content += " (#" + getGlobalLength(h.getLength() + diceValue) + ")";

    MainRightPanel.addLog(new ActionLog(color, name, content));
    Manager.actionEnded(this);
  }

  public Color getColor() {
    return color;
  }
}