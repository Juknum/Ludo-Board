package src.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import src.panel.BoardPanel;
import src.panel.DicePanel;
import src.panel.MainRightPanel;
import src.player.AIPlayer;
import src.player.Player;
import src.player.RealPlayer;

import static src.game.CONSTANTS.*;

public class Manager {
  @SuppressWarnings("PointlessBooleanExpression")
  public static boolean AUTO_CLICK = DEBUG && false;

  private static JButton diceButton;
  private static BoardPanel gamePanel;
  private static int currentPlayerIndex = 0;
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

    startRound();
    newRound(currentPlayerIndex);

    DicePanel.getInstance().addRollEndListener(v -> {
      Player currentPlayer = getCurrentPlayer();
      AvailableActions[] availableActions = currentPlayer.availableActions(v);

      if (availableActions.length > 0) {
        if (availableActions.length == 1 || currentPlayer instanceof AIPlayer) currentPlayer.act(availableActions[0]);
      }
      else {
        MainRightPanel.addLog(new ActionLog(currentPlayer.getColor(), currentPlayer.getName(), "Can't play!"));
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

  public static void startRound() {
    int beginnerDice = 0;

    for (int i = 0; i < playerList.size(); i++) {
      DicePanel.getInstance().setPlayer(i, playerList.get(i).getName());

      Random diceRandom = new Random();
      int diceValue = 1 + diceRandom.nextInt(6);
      System.out.println(diceValue + " (" + i + ") ? " + beginnerDice + " (" + currentPlayerIndex + ")");

      MainRightPanel.addLog(new ActionLog(getPlayer(i).getColor(), getPlayerName(i), "throw the dice: " + diceValue));
      
      if (beginnerDice < diceValue) {
        currentPlayerIndex = i;
        beginnerDice = diceValue;
      }
    }

    MainRightPanel.addLog(new ActionLog(getCurrentPlayer().getColor(), getCurrentPlayerName(), "starts to play!<hr>"));

  }

  public static void newRound(int playerIndex) {
    DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
    diceButton.setEnabled(true);

    if (getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
  }

  private static Player getCurrentPlayer() {
    return playerList.get(currentPlayerIndex);
  }

  private static String getCurrentPlayerName() {
    return playerList.get(currentPlayerIndex).getName();
  }

  private static Player getPlayer(int index) {
    if (index < 0) return null;
    return playerList.get(index % playerList.size());
  }

  private static String getPlayerName(int index) {
    return getPlayer(index).getName();
  }

  public static ArrayList<Player> getPlayerList() {
    return playerList;
  }
  
}
