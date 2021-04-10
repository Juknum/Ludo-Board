package src.game;

import java.util.Comparator;

public class AvailableActions implements Comparable<AvailableActions>, Comparator<AvailableActions> {
  @Override
  public int compareTo(AvailableActions o) {
    if (this.action == o.action) return 0;
    if (this.action == actions.MOVE) return 1;
    if (this.action == actions.STAIRS_UP) return -1;

    /* OUT OF BARNS:
     * Return if other: move = -1, stairs_up = 1;
     */
    return (o.action == actions.MOVE) ? -1 : 1;
  }

  @Override
  public int compare(AvailableActions o1, AvailableActions o2) {
    return o1.compareTo(o2);
  }

  public enum actions {
    MOVE,
    BARNS_OUT,
    STAIRS_UP
  }

  public actions action;
  public int pawnIndex;

  public AvailableActions(actions action, int pawnIndex) {
    this.action = action;
    this.pawnIndex = pawnIndex;
  }
}
