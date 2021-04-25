package panel;

import drawer.DiceDrawer;
import game.Manager;
import static game.CONSTANTS.*;
import game.RollListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class DicePanel extends JLabel {
  private static final long serialVersionUID = 1L;
  private static final int TURNS = 12;
  private static final int DELAY = 55;

  private Timer timer;
  private int diceTurn = 0;
  private final Random random;
  private final Random diceRandom;

  private int lastDiceValue = 0;
  private static DicePanel instance;
  private final ArrayList<RollListener> rollEnd = new ArrayList<>();

  /**
   * Listen to the end of the dice roll
   * @param rollEnd RollListener
   */
  public void addRollEndListener(RollListener rollEnd) {
    this.rollEnd.add(rollEnd);
  }

  /**
   * Get the instance of the Dice Panel
   * @return DicePanel
   */
  public static DicePanel getInstance() {
    if (instance == null) instance = new DicePanel();
    return instance;
  }

  /**
   * Get the last dice value
   * @return int
   */
  public static int getLastDice() {
    return getInstance().lastDiceValue;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    DiceDrawer.draw(g, 30, 50, lastDiceValue);
  }

  /**
   * Starts the roll of the dice
   */
  public void startRoll() {
    timer.start();
    diceTurn = 1;
  }

  /**
   * Hide the dice of the panel
   */
  public void hideDice() {
    lastDiceValue = 0;
    this.repaint();
  }

  /**
   * Setup the dice panel
   */
  private DicePanel() {
    this.setText(null);
    this.setVerticalAlignment(SwingConstants.TOP);
    this.setHorizontalAlignment(SwingConstants.CENTER);

    random     = new Random();
    diceRandom = new Random();

    timer = new Timer(DELAY, e -> {
      diceTurn++;

      if (diceTurn == TURNS) {
        timer.stop();
        diceTurn = 0;

        lastDiceValue = 1 + diceRandom.nextInt(6);
        this.repaint();

        for (RollListener l : rollEnd) l.onRollEnded(lastDiceValue);
      }
      else {
        lastDiceValue = 1 + random.nextInt(6);
        this.repaint();
      }
    });

    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (DEBUG) {
          Manager.AUTO_CLICK = !Manager.AUTO_CLICK;
          System.out.println("AUTO CLICK: " + Manager.AUTO_CLICK);
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {}

      @Override
      public void mouseReleased(MouseEvent e) {}

      @Override
      public void mouseEntered(MouseEvent e) {}

      @Override
      public void mouseExited(MouseEvent e) {}

    });
  }

  /**
   * Set the text below the dice button
   * @param playerIndex int
   * @param playerName String
   */
  public void setPlayer(int playerIndex, String playerName) {
    this.setText(
      "<html>It's the turn of <b style='color: #"+ Integer.toHexString(darkColors[playerIndex].getRGB() & 0xFFFFFF) + "'>" 
      + playerName + 
      "</b></html>");
  }
}
