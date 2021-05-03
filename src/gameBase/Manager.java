package gameBase;

import static gameBase.AvailableActions.actions.*;
import static gameBase.Constants.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import gamePanel.BoardPanel;
import gamePanel.DicePanel;
import gamePanel.MainRightPanel;
import gamePlayer.AIPlayer;
import gamePlayer.Player;
import gamePlayer.RealPlayer;

public class Manager {
  @SuppressWarnings("unused")
  public static boolean AUTO_CLICK = DEBUG && false;

  private static JButton diceButton;
  private static BoardPanel gamePanel;
  public static int currentPlayerIndex = 0;
  public static final ArrayList<Player> playerList = new ArrayList<>(NB_REAL_PLAYERS+NB_BOT_PLAYERS);
  public static int playerEnd = 0;

  /**
   * Set the dice button
   * @param button JButton
   */
  public void setDiceButton(JButton button) {
    diceButton = button;
  }

  /**
   * Set the gam panel
   * @param gamePanel BoardPanel
   */
  public static void setGamePanel(BoardPanel gamePanel) {
    Manager.gamePanel = gamePanel;
  }

  public static Manager instance = null;
  /**
   * Get the actual instance
   * @return Manager 
   */
  public static Manager getInstance() {
    if (instance == null) {
      instance = new Manager();
    }

    return instance;
  }

  private Manager() {}

  /**
   * Start the game
   * @param c Component
   */
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
      diceButton.setEnabled(false);
      Player currentPlayer = getCurrentPlayer();
      AvailableActions[] availableActions = currentPlayer.availableActions(v);

      if (currentPlayer instanceof AIPlayer) currentPlayer.act(availableActions[0]);

      if (currentPlayer instanceof RealPlayer) {

        int pawn = 0;
        // get the played pawn if the player can play
        if (currentPlayer.horsesOut() - currentPlayer.horsesInHome() != 0 || (currentPlayer.horsesIn() > 0 && DicePanel.getLastDice() == 6)) {
          do {
            String tmp = JOptionPane.showInputDialog(c, "Enter a horse to play (1..." + NB_HORSES + ")");
            try {
              pawn = Integer.parseInt(tmp);
            } catch (NumberFormatException ignored) {}
          } while (pawn < 1 || pawn > NB_HORSES);

          currentPlayer.act(availableActions[pawn-1]);
        } else currentPlayer.act(new AvailableActions(CANT_PLAY, 0));
      }
    });

  }

  /**
   * Ends the turn of the given player
   * @param p Player
   */
  public static void actionEnded(Player p) {
    gamePanel.repaint();

    if (p.horsesInHome() == NB_HORSES && p.getOrder() == 0) {
      p.setOrder(playerEnd + 1);
      playerEnd++;
    }
    
    if (p == getCurrentPlayer()) {
      if (DicePanel.getLastDice() == 6) {
        diceButton.setEnabled(true);
    
        if (Manager.getCurrentPlayer() instanceof AIPlayer && !DEBUG) diceButton.doClick();
        else if (Manager.getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
      }
      else nextPlayer(1);
    }
  }

  private static void updateStats() {
    // setup stats logs
    for (int i = 0; i < playerList.size(); i++) {
      Player p = playerList.get(i);

      if (!p.hasEatenSomeone) MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), "Can't go to stairs"), i);
      else if (p.horsesInHome() == NB_HORSES) MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), p.getOrderToString()), i);
      else MainRightPanel.setStats(new StatsLogs(p.getColor(), p.getName(), p.horsesInHome() + " horse(s) in home"), i);
    }
  }

  /**
   * Switch the currentPLayerIndex to the next player
   */
  public static void nextPlayer(int num) {
    if (num > NB_BOT_PLAYERS+NB_REAL_PLAYERS) return; // avoid infinite loop when recursive

    updateStats();

    // end of a match:
    if (playerEnd == playerList.size() - 1) return;

    // if the next player already achieved the goal, set the next+i player (recursively)
    if (playerList.get((currentPlayerIndex + num) % playerList.size()).horsesInHome() == NB_HORSES) {
      nextPlayer(num + 1);
    } else currentPlayerIndex = (currentPlayerIndex + num) % playerList.size();

    newRound(currentPlayerIndex);
  }

  /**
   * Determines which player will start to play following games rules
   */
  public static void startRound() {
    gamePanel.repaint();
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
    updateStats();

    // if the player haven't finished (he doesn't have all horses in home)
    DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
    diceButton.setEnabled(true);

    if (Manager.getCurrentPlayer() instanceof AIPlayer && !DEBUG) diceButton.doClick();
    else if (getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) diceButton.doClick();
  }

  /**
   * Get the current playing player
   * @return Player
   */
  public static Player getCurrentPlayer() {
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
