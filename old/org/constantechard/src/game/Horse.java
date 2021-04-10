package old.org.constantechard.src.game;

import old.org.constantechard.src.DicePanel;
import static old.org.constantechard.src.game.GameConstants.MAX_LENGTH;

public class Horse {
  private boolean inBarns = true;
  private int length = 0;
  private int stairs = 1;

  public void move() {
    int v = DicePanel.getLastDice();
    if (v + length > MAX_LENGTH) {
      int diff = MAX_LENGTH - length;
      int left = v - diff;
      length += diff - left;
    }
    else {
      length += v;
    }
  }

  public boolean isInStairs() {
    return length == MAX_LENGTH;
  }

  public boolean canStairs() {
    int diceValue = DicePanel.getLastDice();
    if (!isInStairs()) {
      return false;
    }
    return diceValue == stairs;
  }

  public void stairs() {
    if (!canStairs()) {
      return;
    }
    stairs++;
  }

  public int getStairs() {
    return stairs;
  }

  public int getLength() {
    return length;
  }

  public boolean isInBarns() {
    return inBarns;
  }

  public void setInBarns(boolean inBarns) {
    this.inBarns = inBarns;
    length = 0;
    stairs = 1;
  }

  @Override
  public String toString() {
    return "{ inBarns: " + inBarns + ", length: " + length + ", stairs: " + stairs + "}";
  }
}