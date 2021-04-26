package game;

import static game.Constants.TEXT_COLOR;

import java.awt.Color;

public class StatsLogs {
  private Color c = TEXT_COLOR;
  private String prefix = "system";
  private final String content;

  /**
   * Create a new stat log
   * @param c Color
   * @param prefix String of the player name
   * @param content String
   */
  public StatsLogs(Color c, String prefix, String content) {
    this.c = c;
    this.prefix = prefix;
    this.content = content;
  }

  @Override
  public String toString() {
    return "<div><span style='color: #" + Integer.toHexString(c.getRGB() & 0xFFFFFF) + ";'>" + prefix + " </span> "+ content + "</div>";
  }

}
