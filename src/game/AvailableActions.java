package game;

import java.util.Comparator;

public class AvailableActions implements Comparable<AvailableActions>, Comparator<AvailableActions> {
  
  
  /**
   * Compare to the given action
   * @param o AvailableActions
   */
  @Override
  public int compareTo(AvailableActions o) {

    if (this.action == o.action)           return  0; // we don't care if it's the same
    if (this.action == actions.STAIRS_UP)  return -1; // MOST IMPORTANT
    if (this.action == actions.BARNS_OUT)  return -2;
    if (this.action == actions.JUMP_HORSE) return -3;
    if (this.action == actions.MOVE)       return -4;
    if (this.action == actions.CANT_PLAY)  return -100; // LESS IMPORTANT

    else return 1;
  }
  
  /**
   * Compare two action together
   * @param o1 AvailableActions
   * @param o2 AvailableActions
   */
  @Override
  public int compare(AvailableActions o1, AvailableActions o2) {
    return o1.compareTo(o2);
  }

  /**
   * List of all type of actions
   */
  public enum actions {
    MOVE,
    BARNS_OUT,
    STAIRS_UP,
    JUMP_HORSE,
    CANT_PLAY
  }

  public actions action;
  public int pawnIndex;

  public AvailableActions(actions action, int pawnIndex) {
    this.action = action;
    this.pawnIndex = pawnIndex;
  }
}
