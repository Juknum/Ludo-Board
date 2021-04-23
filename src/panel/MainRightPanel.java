package src.panel;

import javax.swing.*;
import java.awt.*;

import src.game.Manager;

public class MainRightPanel extends JPanel {
  private static final long    serialVersionUID = 1L;
  private static       JButton diceButton;

  public MainRightPanel() {
    this.setLayout(new GridLayout(3, 1));
    this.setMinimumSize(new Dimension(150, 0));
    this.setPreferredSize(new Dimension(150, 0));

    diceButton = new JButton("Roll dice");
    Manager.getInstance().setDiceButton(diceButton);
    this.add(diceButton);
    this.add(DicePanel.getInstance());

    diceButton.addActionListener(e -> {
      diceButton.setEnabled(false);
      DicePanel.getInstance().startRoll();
    });
  }
}
