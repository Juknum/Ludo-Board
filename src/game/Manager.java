package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import panel.BoardPanel;
import panel.DicePanel;
import player.AIPlayer;
import player.Player;
import player.RealPlayer;

import static game.CONSTANTS.*;

public class Manager {
  @SuppressWarnings("PointlessBooleanExpression")
  public static boolean AUTO_CLICK = DEBUG && false;

  private static JButton diceButton;
  private static BoardPanel gamePanel;
  private static int currentPlayerIndex;
  private static final ArrayList<Player> playerList = new ArrayList<>(4);

  public void setDiceButton(JButton button) {
    diceButton = button;
  }

  public static void setGamePanel(BoardPanel gamePanel) {
    Manager.gamePanel = gamePanel;
  }

  public static Manager instance = null;
  public static Manager getInstance() {
    if (instance == null) {
      instance = new Manager();
    }

    return instance;
  }

  private Manager() {}

  public void start(Component c) {
    playerList.clear();
    currentPlayerIndex = 0;

    int nb = -1;

    // get real players:
    do {
      if (DEBUG) nb = NB_REAL_PLAYERS;
      else {
        String cnt = JOptionPane.showInputDialog(c, "How many players?");
        try {
          nb = Integer.parseInt(cnt);
        } catch (NumberFormatException ignored) {}
      }
    } while (nb < MIN_PLAYER || nb > MAX_PLAYER);

    for (int i = 0; i < nb; i++) {
      String name;
      name = DEBUG ? "Player " + (i+1) : JOptionPane.showInputDialog(c, "Enter player " + (i+1) + " name", "John Doe");
      playerList.add(new RealPlayer(name, darkColors[playerList.size()]));
    }

    // get bots:
    do {
      if (DEBUG) nb = NB_BOT_PLAYERS;
      else {
        String cnt = JOptionPane.showInputDialog(c, "How many bots?", MIN_PLAYER);
        try {
          nb = Integer.parseInt(cnt);
        } catch (NumberFormatException ignored) {}
      }
    } while (nb < 0 || nb > (MAX_PLAYER - playerList.size()));

    for (int i = 0; i < nb; i++) {
      playerList.add(new AIPlayer("AI " + (playerList.size() + 1), darkColors[playerList.size()]));
    }

    newRound(currentPlayerIndex);

    DicePanel.getInstance().addRollEndListener(v -> {
      Player currentPlayer = getCurrentPlayer();
      AvailableActions[] availableActions = currentPlayer.availableActions(v);

      if (availableActions.length > 0) {
        if (availableActions.length == 1 || currentPlayer instanceof AIPlayer) currentPlayer.act(availableActions[0]);
      }
      else {
        actionEnded(currentPlayer);
      }
    });

  }

  public static void actionEnded(Player p) {
    gamePanel.repaint();

    if (p == getCurrentPlayer()) {
      if (DicePanel.getLastDice() == 6) {
        diceButton.setEnabled(true);

        if (Manager.getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
      }
      else nextPlayer();
    }
  }

  public static void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
    newRound(currentPlayerIndex);
  }

  public static void newRound(int playerIndex) {
    DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
    diceButton.setEnabled(true);

    if (getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
  }

  private static Player getCurrentPlayer() {
    return playerList.get(currentPlayerIndex);
  }

  public static ArrayList<Player> getPlayerList() {
    return playerList;
  }
  
}
