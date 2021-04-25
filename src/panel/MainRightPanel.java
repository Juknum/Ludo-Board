package panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import game.ActionLog;
import game.StatsLogs;
import game.Manager;

import static game.CONSTANTS.*;

public class MainRightPanel extends JPanel {
  private static JButton diceButton;

  private static JLabel info;
  private static JLabel stats;
  private static JScrollPane scrollPane;
  private static JScrollPane scrollPaneStats;
  private static final ArrayList<ActionLog> actionLogs = new ArrayList<>();
  private static final ArrayList<StatsLogs> statsLogs = new ArrayList<>(NB_BOT_PLAYERS+NB_REAL_PLAYERS);

  /**
   * Set up of the right panel (dice button, dice, stats panel, logs panel)
   */
  public MainRightPanel() {
    this.setLayout(new GridLayout(4, 1));
    this.setMinimumSize(new Dimension(200, 0));
    this.setPreferredSize(new Dimension(200, 0));

    diceButton = new JButton("Roll dice");
    Manager.getInstance().setDiceButton(diceButton);
    this.add(diceButton);
    this.add(DicePanel.getInstance());

    stats = new JLabel("");
    stats.setHorizontalAlignment(SwingConstants.LEFT);
    stats.setVerticalAlignment(SwingConstants.TOP);

    scrollPaneStats = new JScrollPane(stats);

    this.add(scrollPaneStats);

    info = new JLabel("");
    info.setHorizontalAlignment(SwingConstants.LEFT);
    info.setVerticalAlignment(SwingConstants.TOP);

    scrollPane = new JScrollPane(info);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.add(scrollPane);

    MainRightPanel.addLog(new ActionLog(Color.black, "", "<h3>Game Information:</h3>"));
    MainRightPanel.addStats(new StatsLogs(Color.black, "", ""));

    diceButton.addActionListener(e -> {
      diceButton.setEnabled(false);
      DicePanel.getInstance().startRoll();
    });
  }

  /**
   * Add the latest action to a JscrollPanel in a text form to get logs of the gameplay
   * @param log ActionLog
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

  /**
   * Add stats to the stats logs
   */
  public static void addStats(StatsLogs log) {
    for (int i = 0; i < MAX_PLAYER; i++) {
      statsLogs.add(log);
    }
  }

  /**
   * Set a statistics of the statistics panel (0-3)
   * @param log StatsLogs
   * @param index int
   */
  public static void setStats(StatsLogs log, int index) {
    if (index > 3) return;

    statsLogs.set(index, log);

    StringBuilder builder = new StringBuilder("<html><h3>Statistics:</h3>");
    for (StatsLogs al : statsLogs) {
      builder.append(al);
    }
    builder.append("<html>");

    stats.setText(builder.toString());
  }
  
}
