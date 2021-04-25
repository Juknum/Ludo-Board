package player;

import panel.DicePanel;
import static game.CONSTANTS.MAX_LENGTH;

public class Horse {
  private boolean inBarns = true;
  private boolean isStairs = false;
  private boolean goalReached = false;
  private int length = 0;
  private int stairs = 7;

  /**
   * Move a horse forward, following the latest dice value
   */
  public void move(boolean hasEatenSomeone) {
    int diceValue = DicePanel.getLastDice();

    if (length + diceValue <= MAX_LENGTH) length += diceValue;
    else if (hasEatenSomeone && !isStairs) {
      int stepsForStairs = (length + diceValue) - MAX_LENGTH;
      length = MAX_LENGTH;
      isStairs = true;
      stairs -= stepsForStairs;

      // if dice value == 6:
      if (stairs == 1) goalReached = true;
    }

  }

  public boolean isInStairs() {
    return isStairs;
  }

  /**
   * a horse can stairs if:
   * it reached the latest tile,
   * && the substraction of left stairs & the dice value can't go below 0
   * @return Boolean
   */
  public boolean canStairs() {
    int diceValue = DicePanel.getLastDice();

    if (length == MAX_LENGTH) {
      if (stairs - diceValue < 1) return false;
      else {
        isStairs = true;
        return true;
      }
    }
    
    return false;
  }

  /**
   * horse stairs decrease by the dice value, if canStairs()
   */
  public void stairs() {
    if (!canStairs()) return;

    int diceValue = DicePanel.getLastDice();
    stairs -= diceValue;

    // Since stairs = 7, the pawn has finished the race when stairs = 1
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
   * Set the barns status of a horse & reset attributes
   * @param inBarns
   */
  public void setInBarns(boolean inBarns) {
    this.inBarns = inBarns;
    length = 0;
    stairs = 7;
  }
}