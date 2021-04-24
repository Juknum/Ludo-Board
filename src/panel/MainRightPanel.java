package panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import game.ActionLog;
import game.Manager;

public class MainRightPanel extends JPanel {
  private static JButton diceButton;

  private static JLabel info;
  private static JScrollPane scrollPane;
  private static final ArrayList<ActionLog> actionLogs = new ArrayList<>();

  public MainRightPanel() {
    this.setLayout(new GridLayout(3, 1));
    this.setMinimumSize(new Dimension(200, 0));
    this.setPreferredSize(new Dimension(200, 0));

    diceButton = new JButton("Roll dice");
    Manager.getInstance().setDiceButton(diceButton);
    this.add(diceButton);
    this.add(DicePanel.getInstance());

    info = new JLabel("");
    info.setHorizontalAlignment(SwingConstants.LEFT);
    info.setVerticalAlignment(SwingConstants.CENTER);

    scrollPane = new JScrollPane(info);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.add(scrollPane);
    MainRightPanel.addLog(new ActionLog(Color.black, "", "<b>Game Information:</b><br><br>"));
    
    diceButton.addActionListener(e -> {
      diceButton.setEnabled(false);
      DicePanel.getInstance().startRoll();
    });
  }

  /******************************************
   * Add the last action to a JscrollPanel in a text form to get logs of the game
   * 
   * @param log
   */
  public static void addLog(ActionLog log) {
    actionLogs.add(log);

    StringBuilder builder = new StringBuilder("<html>");
    for (ActionLog al : actionLogs) {
      builder.append(al);
    }
    builder.append("<html>");

    info.setText(builder.toString());

    JScrollBar vertical = scrollPane.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());
  }
  
}
