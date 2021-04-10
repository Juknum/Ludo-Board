package old.org.constantechard.src;

import old.org.constantechard.src.game.ActionLog;
import old.org.constantechard.src.game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RightPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private final JButton diceButton;

  private static JLabel info;
  private static JScrollPane scrollPane;
  private static final ArrayList<ActionLog> actionLogs = new ArrayList<>();

  public RightPanel() {
    this.setLayout(new GridLayout(3, 1));
    this.setMinimumSize(new Dimension(150, 0));
    this.setPreferredSize(new Dimension(150, 0));

    diceButton = new JButton("Roll dice");
    GameManager.getInstance().setDiceButton(diceButton);
    this.add(diceButton);

    this.add(DicePanel.getInstance());

    info = new JLabel("<html><b>Game Information:</b><hr><html>");
    info.setHorizontalAlignment(SwingConstants.LEFT);
    info.setVerticalAlignment(SwingConstants.CENTER);

    scrollPane = new JScrollPane(info);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.add(scrollPane);

    diceButton.addActionListener(e -> {
      diceButton.setEnabled(false);
      DicePanel.getInstance().startRoll();
    });
  }

  public static void addLog(ActionLog log) {
    actionLogs.add(log);

    StringBuilder builder = new StringBuilder("<html>");
    for (ActionLog al : actionLogs) {
      builder.append(al);
    }
    builder.append("</html>");

    info.setText(builder.toString());
    JScrollBar vertical = scrollPane.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());

  }
}