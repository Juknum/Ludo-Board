package src.player;

import src.panel.DicePanel;
import static src.game.CONSTANTS.MAX_LENGTH;

public class Horse {
  private boolean inBarns = true;
  private boolean goalReached = false;
  private boolean isSafe = false;
  private int length = 0;
  private int stairs = 7;

  /**
   * Move a horse forward, following the latest dice value
   */
  public void move() {
    int l = DicePanel.getLastDice();

    /**
     * INFO: 
     * si le cheval se trouve a 3 case de l'escalier et qu'il fait 4, 
     * il monte sur la 1ère marche ou bien avance de 3 et attend le tour d'après?
     * 
     * > état actuel: il avance de 3 et attend le tour d'après,
     */
    if (length != MAX_LENGTH) length = (length + l > MAX_LENGTH) ? MAX_LENGTH : length + l;

    switch (length) {
      case 0: case 8: case 13: case 21: case 26: case 34: case 39: case 47:
        isSafe = true;
        break;
      default:
        isSafe = false;
        break;
    }
  }

  public boolean isInStairs() {
    return stairs < 7;
  }

  /**
   * a horse can stairs if:
   * it reached the latest tile,
   * && the substraction of left stairs & the dice value can't go below 0
   * @return Boolean
   */
  public boolean canStairs() {
    int diceValue = DicePanel.getLastDice();

    // ATENTION: if the player hasn't eated any horse, he can't place a horse inside his stairs
    if (length == MAX_LENGTH) {
      if (stairs - diceValue <= 0) return false;
      else return true;
    }
    else return false;
  }

  /**
   * horse stairs decrease by the dice value, if canStairs()
   */
  public void stairs() {
    if (!canStairs()) return;

    int diceValue = DicePanel.getLastDice();
    stairs -= diceValue;

    if (stairs == 1) goalReached = true;
  }

  /**
   * Get the number of step to finish the stairs of this horse
   * @return stairs value
   */
  public int getStairs() {
    return stairs;
  }

  /**
   * Get the length (number of tile) that a horse as reached
   * @return length value
   */
  public int getLength() {
    return length;
  }

  /**
   * Get the barns status of a horse
   * @return Boolean
   */
  public boolean isInBarns() {
    return inBarns;
  }

  /**
   * Get the state of a horse, does it have reached the highest step?
   * @return Boolean
   */
  public boolean isGoalReached() {
    return goalReached;
  }

  /**
   * Tell if a horse is in a safe zone
   */
  public boolean isItSafe() {
    return isSafe;
  }

  /**
   * Set the barns status of a horse & reset attributes
   * @param inBarns
   */
  public void setInBarns(boolean inBarns) {
    this.inBarns = inBarns;
    length = 0;
    stairs = 7;
  }
}