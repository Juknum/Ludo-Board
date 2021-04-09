package org.constantechard.src.game;

import org.constantechard.src.DicePanel;
import org.constantechard.src.LudoBoardPanel;
import org.constantechard.src.RightPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.constantechard.src.game.GameConstants.*;

public final class GameManager {
  @SuppressWarnings("PointlessBooleanExpression")
  public static boolean AUTO_CLICK = DEBUG && false;

  private static JButton diceButton; // = null
  private static LudoBoardPanel gamePanel;
  private static final ArrayList<Player> playerList = new ArrayList<>(4);
  private static int currentPlayerIndex;

  public void setDiceButton(JButton button) {
    diceButton = button;
  }

  public static void setGamePanel(LudoBoardPanel gamePanel) {
    GameManager.gamePanel = gamePanel;
  }

  public static GameManager instance = null;

  public static GameManager getInstance() {
    if (instance == null) {
      instance = new GameManager();
    }

    return instance;
  }

  private GameManager() {}

  public void start(Component c) {
    playerList.clear();
    currentPlayerIndex = 0;

    int nb = -1;

    // nb players
    do {
      if (DEBUG) {
        nb = NB_REAL_PLAYERS;
      }
      else {
        String cnt = JOptionPane.showInputDialog(c, "How many players?");
        try {
          nb = Integer.parseInt(cnt);
        } catch (NumberFormatException ignored) {}
      }
    } while (nb < MIN_PLAYER || nb > MAX_PLAYER);

    for (int i = 0; i < nb; i++) {
      String name;
      if (DEBUG) {
        name = "Player " + (i+1);
      }
      else {
        name = JOptionPane.showInputDialog(c, "Enter player" + (i+1) +  " name", "Alexander");
      }
      playerList.add(new RealPlayer(name, darkColors[playerList.size()]));
    }

    do {
      if (DEBUG) {
        nb = NB_BOT_PLAYERS;
      }
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
        // Autoplay if real player have one action or if it's an AI
        if (availableActions.length == 1 || currentPlayer instanceof AIPlayer) {
          currentPlayer.act(availableActions[0]);
        }
      }
      else {
        RightPanel.addLog(new ActionLog(currentPlayer.getColor(), currentPlayer.getName(), "Can't play"));
        actionEnded(currentPlayer);
      }
    });

  }

  /**
   * Action end listener
   * @param p the player which triggered the listener
   */

   public static void actionEnded(Player p) {
     // always repaint
     gamePanel.repaint();

     // trigger action if current player
     if (p == getCurrentPlayer()) {
       if (DicePanel.getLastDice() == 6) {
         // replay
         diceButton.setEnabled(true);

         if (GameManager.getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) {
           diceButton.doClick();
         }
       }
       else {
         nextPlayer();
       }
     }
   }

   public static void nextPlayer() {
     currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
     newRound(currentPlayerIndex);
   }

   public static void newRound(int playerIndex) {
     //DicePanel.getInstance().hideDice();
     DicePanel.getInstance().setPlayer(playerIndex, playerList.get(playerIndex).getName());
     diceButton.setEnabled(true);

     if (getCurrentPlayer() instanceof AIPlayer && AUTO_CLICK) {
       diceButton.doClick();
     }
   }

   private static Player getCurrentPlayer() {
     return playerList.get(currentPlayerIndex);
   }

   public static ArrayList<Player> getPlayerList() {
     return playerList;
   }

}