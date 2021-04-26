package designer;

import java.awt.Color;
import java.awt.Graphics;

public class DiceDesigner {
  private static final int SIZE = 83;
  private static final int CI_SIZE = 16;
  private static final int BOR = 1;

  private static final float leftPercent = 1.f / 4.f;
  private static final float rightPercent = 1f - leftPercent;

  private static final Color DICE_BG = Color.white;
  private static final Color DICE_BORDER = Color.black;
  private static final Color DICE_VAL = Color.black;

  /**
   * Draw the square of the dice
   * @param g Graphics
   * @param x int
   * @param y int
   * @param v int
   */
  public static void draw(Graphics g, int x, int y, int v) {
    if (v < 1 || v > 6)
      return;

    g.setColor(DICE_BG);
    g.fillRect(x, y, SIZE, SIZE);

    g.setColor(DICE_BORDER);
    g.drawRect(x, y, SIZE, SIZE);

    g.setColor(DICE_VAL);

    // odd number got black dot in the center
    if (v % 2 == 1) {
      drawPoint(g, x, y, .5f, .5f);
    }

    // 2 dot in diagonal north west
    if (v >= 2) {
      drawPoint(g, x, y, leftPercent, leftPercent);
      drawPoint(g, x, y, rightPercent, rightPercent);
    }

    // 2 dot in diagonal north east
    if (v >= 4) {
      drawPoint(g, x, y, rightPercent, leftPercent);
      drawPoint(g, x, y, leftPercent, rightPercent);
    }

    // 2 dot in the middle
    if (v == 6) {
      drawPoint(g, x, y, leftPercent, .5f);
      drawPoint(g, x, y, rightPercent, .5f);
    }
  }

  /**
   * Draw the points of the dice
   * @param g
   * @param x
   * @param y
   * @param percentX
   * @param percentY
   */
  private static void drawPoint(Graphics g, int x, int y, float percentX, float percentY) {
    
    // border offset
    int offsetX = x + BOR;
    int offsetY = y + BOR;

    // remove border to working side size
    int workingSide = SIZE + 1 - BOR * 2;

    // offset of a certain percentage
    if (percentX > .5f) offsetX += workingSide - (1f - percentX) * workingSide;
    else offsetX += percentX * workingSide;

    if (percentY > .5f) offsetY += workingSide - (1f - percentY) * workingSide;
    else offsetY += percentY * workingSide;

    // remove half of dice circle
    offsetX -= CI_SIZE / 2;
    offsetY -= CI_SIZE / 2;

    g.fillOval(offsetX, offsetY, CI_SIZE, CI_SIZE);

  }

}
