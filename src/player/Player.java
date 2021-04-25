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

  /**
   * Get the horses of a player
   * @return Horse[]
   */
  public Horse[] getHorses() {
    return horses;
  }

  /**
   * Set player attributes
   */
  public Player(String name, Color color) {
    this.name  = name;
    this.color = color;
  }

  /**
   * Get the number of horses in barns
   * @return int
   */
  public int horsesIn() {
    int res = 0;
    for (Horse h : horses) if (h.isInBarns()) res++;
    return res;
  }

  /**
   * Get the number of horse out of the barns
   * @return int
   */
  public int horsesOut() {
    return NB_HORSES - horsesIn();
  }

  /**
   * Get the player name
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Get the number of horse in stairs
   * @return int
   */
  public int horsesInStairs() {
    int res = 0;
    for (Horse h : horses) if (h.isInStairs()) res++;
    return res;
  }

  /**
   * Get the number of horse in home
   * @return int
   */
  public int horsesInHome() {
    int res = 0;
    for (Horse h : horses) if (h.isGoalReached()) res++;
    return res;
  }

  /**
   * Get the order of a player (1st/2nd/3rd/4th)
   * @return int
   */
  public int getOrder() {
    return order;
  }

  /**
   * Get the stringified version of the order
   * @return String
   */
  public String getOrderToString() {
    switch (getOrder()) {
      case 0:
        return "⠀⠀⠀"; // 3 "⠀" (blank space)
      case 1:
        return "⠀👑⠀";
      case 2:
        return "2nd";
      case 3:
        return "3rd";
      case 4:
        return "4th";
      default:
        return "Err";
    }
  }

  /**
   * Set the order of a player (1st/2nd/3rd/4th)
   */
  public void setOrder(int order) {
    this.order = order;
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

  /**
   * Set all possible actions for a player
   */
  public AvailableActions[] availableActions(int diceValue) {
    ArrayList<AvailableActions> availableActions = new ArrayList<>(horses.length);

    for (int i = 0; i < horses.length; i++) {
      Horse h = horses[i];

      if (h.isInBarns() && diceValue == 6) availableActions.add(new AvailableActions(BARNS_OUT, i));
      else {
        if (h.canStairs() && hasEatenSomeone) availableActions.add(new AvailableActions(STAIRS_UP, i));
        else if (!h.isInBarns() && !h.isInStairs() && h.getLength() < MAX_LENGTH) {

          // Looks for horses in the next cell:
          int nextLength = getGlobalLength(h.getLength() + diceValue);
          if (getUpcomingHorses(nextLength) < 2) { // if there is more than 1 horse, this horse can't go to the cell
            // if there is 1 horse, check if the player could remove that horse
            if (canRemoveHorse(nextLength)) availableActions.add(new AvailableActions(JUMP_HORSE, i));
            else availableActions.add(new AvailableActions(MOVE, i));
          }
        }
      }
    }

    // we sort actions to let the most important one at first position
    availableActions.sort(new AvailableActions(MOVE, 0));
    return availableActions.toArray(new AvailableActions[0]);
  }

  /**
   * Execute the given action
   * @param action AvailableActions
   */
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
    
    MainRightPanel.addLog(new ActionLog(color, name, content));
    Manager.actionEnded(this);
  }

  /**
   * Get the color of a player
   * @return Color
   */
  public Color getColor() {
    return color;
  }
}