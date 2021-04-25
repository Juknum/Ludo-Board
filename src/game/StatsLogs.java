package game;

import static game.CONSTANTS.TEXT_COLOR;
import java.awt.*;

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
