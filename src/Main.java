
import javax.swing.*;

import game.Manager;
import panel.BoardPanel;
import panel.MainRightPanel;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main extends JFrame {
  private static final long serialVersionUID = 1L;
  private static final int  BOARD_SIDE = 800;

  Main() {
    this.setSize(1200, BOARD_SIDE);
    this.setTitle("LudoBoard: Developped by Julien CONSTANT & Noé ECHARD");
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    BoardPanel board = new BoardPanel(BOARD_SIDE);
    this.getContentPane().add(board, BorderLayout.CENTER);

    MainRightPanel panel = new MainRightPanel();
    this.getContentPane().add(panel, BorderLayout.EAST);

    this.pack();
    this.setVisible(true);

    Main m = this;
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        Dimension newd = new Dimension(0, board.getWidth() + m.getInsets().top);
        m.setMinimumSize(newd);
      }
    });

    Manager.getInstance().start(this);

  }

  public static void main(String[] args) {
    new Main();
  }
}