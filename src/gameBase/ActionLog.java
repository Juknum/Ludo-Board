package gameBase;

import static gameBase.Constants.TEXT_COLOR;

import java.awt.Color;

public class ActionLog {
  private Color c = TEXT_COLOR;
  private String prefix = "system";
  private final String content;

  /**
   * Change the content of a log
   * @param content String
   */
  public ActionLog(String content) {
    this.content = content;
  }

  /**
   * Create a new action log
   * @param c Color
   * @param prefix String
   * @param content String
   */
  public ActionLog(Color c, String prefix, String content) {
    this.c = c;
    this.prefix = prefix;
    this.content = content;
  }
  
  @Override
  public String toString() {
    return "<div><span style='color: #" + Integer.toHexString(c.getRGB() & 0xFFFFFF) + ";'>" + prefix + " </span> " + content + "</div>";
  }
}
