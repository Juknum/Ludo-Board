package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import panel.BoardPanel;
import panel.DicePanel;
import panel.MainRightPanel;
import player.AIPlayer;
import player.Player;
import player.RealPlayer;

import static game.CONSTANTS.*;

public class Manager {
  @SuppressWarnings("PointlessBooleanExpression")
  public static boolean AUTO_CLICK = DEBUG && false;

  private static JButton diceButton;
  private static BoardPanel gamePanel;
  public static int currentPlayerIndex = 0;
  public static final ArrayList<Player> playerList = new ArrayList<>(4);

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
        String cnt = JOptionPane.showInputDialog(c, "How many COM?", MIN_PLAYER);
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

  /**
   * Ends the turn of the given player
   * @param p Player
   */
  public static void actionEnded(Player p) {
    gamePanel.repaint();

    if (p == getCurrentPlayer()) {
      if (DicePanel.getLastDice() == 6) {
        diceButton.setEnabled(true);

        if (Manager.getCurrentPlayer() instanceof AIPlayer && !DEBUG) diceButton.doClick();
        else if (Manager.getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
      }
      else nextPlayer();
    }
  }

  /**
   * Switch the currentPLayerIndex to the next player
   */
  public static void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
    newRound(currentPlayerIndex);
  }

  /**
   * Determines which player will start to play following games rules
   */
  public static void startRound() {
    int beginnerDice = 0;

    for (int i = 0; i < playerList.size(); i++) {
      DicePanel.getInstance().setPlayer(i, playerList.get(i).getName());

      Random diceRandom = new Random();
      int diceValue = 1 + diceRandom.nextInt(6);

      MainRightPanel.addLog(new ActionLog(getPlayer(i).getColor(), getPlayerName(i), "throw the dice: " + diceValue));
      
      if (beginnerDice < diceValue) {
        currentPlayerIndex = i;
        beginnerDice = diceValue;
      }
    }

    MainRightPanel.addLog(new ActionLog(getCurrentPlayer().getColor(), getCurrentPlayerName(), "starts to play!<hr>"));

  }

  /**
   * Start a round with the given player index
   * @param playerIndex int
   */
  public static void newRound(int playerIndex) {
    DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
    diceButton.setEnabled(true);

    if (Manager.getCurrentPlayer() instanceof AIPlayer && !DEBUG) diceButton.doClick();
    else if (getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();

    for (int i = 0; i < playerList.size(); i++) {
      Player p = playerList.get(i);

      if (!p.hasEatenSomeone) MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), "Can't go to stairs", " "), i);
      else if (p.horsesInHome() == 4) {
        p.setOrder();
        MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), "have finished", "" + p.getOrder()), i);
      }
      else MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), p.horsesInHome() + " horse(s) in home", " "), i);
    }
    
  }

  /**
   * Get the current playing player
   * @return Player
   */
  private static Player getCurrentPlayer() {
    return playerList.get(currentPlayerIndex);
  }

  /**
   * Get the current playing player name
   * @return String
   */
  private static String getCurrentPlayerName() {
    return getCurrentPlayer().getName();
  }

  /**
   * Get player of the given index
   * @param index
   * @return Player
   */
  public static Player getPlayer(int index) {
    if (index < 0) return null;
    return playerList.get(index % playerList.size());
  }

  /**
   * Get player name of the given index
   * @param index int
   * @return String
   */
  private static String getPlayerName(int index) {
    return getPlayer(index).getName();
  }

  /**
   * Get the actual list of players
   * @return ArrayList<Player>
   */
  public static ArrayList<Player> getPlayerList() {
    return playerList;
  }
  
}
