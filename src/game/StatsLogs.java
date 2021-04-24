package game;

import static game.CONSTANTS.TEXT_COLOR;
import java.awt.*;

public class StatsLogs {
  private Color c = TEXT_COLOR;
  private String prefix = "system";
  private final String content;
  private final String position;

  public StatsLogs(Color c, String prefix, String content, String position) {
    this.c = c;
    this.prefix = prefix;
    this.content = content;
    this.position = position;
  }

  @Override
  public String toString() {
    return "<div> " + position + " <span style='color: #" + Integer.toHexString(c.getRGB() & 0xFFFFFF) + ";'>" + prefix + " </span> "+ content + "</div>";
  }

}
