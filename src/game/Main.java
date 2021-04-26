package game;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import panel.BoardPanel;
import panel.MainRightPanel;

import static game.Constants.DEBUG;

public class Main extends JFrame implements KeyListener {
  private static final long serialVersionUID = 1L;
  private static final int  BOARD_SIDE = 800;
  public static int keyPressedValue = 0;
  public static boolean keyPressed = false;

  /**
   * Setup of the JFrame
   */
  Main() {
    this.setSize(1200, BOARD_SIDE);

    String title = "LudoBoard: Developped by Julien CONSTANT & Noé ECHARD";
    if (DEBUG) title += " (DEBUG MODE)";

    this.setTitle(title);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    BoardPanel board = new BoardPanel(BOARD_SIDE);
    this.getContentPane().add(board, BorderLayout.CENTER);

    MainRightPanel panel = new MainRightPanel();
    this.getContentPane().add(panel, BorderLayout.EAST);

    this.pack();
    this.setVisible(true);
    this.setFocusable(true);
    this.addKeyListener(this);

    Manager.getInstance().start(this);
  }

  @Override
  public void keyPressed(KeyEvent e) {}
  
  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {
    setKeyPressedValue(e.getKeyCode());
  }

  private void setKeyPressedValue(int keyCode) {
    Main.keyPressedValue = keyCode;
    Main.keyPressed = true;
  }

  public static int getKeyPressedValue() {
    return keyPressedValue;
  }

  public static boolean isKeyPressed() {
    return keyPressed;
  }

  public static void resetKeyPressed() {
    Main.keyPressed = false;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });

  }
  
}