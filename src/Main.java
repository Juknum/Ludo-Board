import static gameBase.Constants.DEBUG;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import gameBase.Manager;
import gamePanel.BoardPanel;
import gamePanel.MainRightPanel;

public class Main extends JFrame {
  private static final long serialVersionUID = 1L;
  private static final int  BOARD_SIDE = 800;

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

    Manager.getInstance().start(this);
  }

  public static void main(String[] args) {
    new Main();
  }
}