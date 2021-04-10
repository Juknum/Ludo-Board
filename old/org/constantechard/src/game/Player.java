package old.org.constantechard.src.game;

import old.org.constantechard.src.DicePanel;
import old.org.constantechard.src.RightPanel;

import java.awt.*;
import java.util.ArrayList;

import static old.org.constantechard.src.game.GameConstants.NB_HORSES;
import static old.org.constantechard.src.game.AvailableActions.actions.*;

public abstract class Player {
  private final String name;
  private final Color color;
  private final Horse[] horses = {
    new Horse(),
    new Horse(),
    new Horse(),
    new Horse()
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
    for (Horse h : horses) {
      if (h.isInBarns()) {
        res++;
      }
    }

    return res;
  }

  public int horsesOut() {
    return NB_HORSES - horsesIn();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("Player: '" + name + "' : " + horsesIn() + " horse(s) in stable, " + horsesOut() + " horses outs.");

    for (Horse h : horses) {
      builder.append("\r\n");
      builder.append(h.toString());
    }

    return builder.toString();
  }

  public String getName() {
    return name;
  }

  public AvailableActions[] availableActions(int diceValue) {
    ArrayList<AvailableActions> availableActions = new ArrayList<>(horses.length);

    for (int i = 0; i < horses.length; i++) {
      Horse h = horses[i];
      if (h.isInBarns()) {
        if (diceValue == 6) {
          availableActions.add(new AvailableActions(BARNS_OUT, i));
        }
      }
      else {
        if (h.canStairs()) {
          availableActions.add(new AvailableActions(STAIRS_UP, i));
        }
        else {
          availableActions.add(new AvailableActions(MOVE, i));
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
        content += "stairs " + h.getStairs();
        break;
      case BARNS_OUT:
        h.setInBarns(false);
        content += "out of barn";
        break;
      case MOVE:
        content += "move forward of " + DicePanel.getLastDice();
        h.move();
        break;
      default:
        break;
    }

    RightPanel.addLog(new ActionLog(color, name, content));
    GameManager.actionEnded(this);
  }

  public Color getColor() {
    return color;
  }
}