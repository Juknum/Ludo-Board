package designer;

import java.awt.*;

public class RectDesigner extends CenterDesigner {

  /**
   * Draw rectangles (for stairs and barns)
   */
  @Override
  public void draw(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    int x = getX();
    int y = getY();

    g.drawRect(x - width / 2, y - height / 2, width, height);
  }

  /**
   * Fill the rectangles
   */
  @Override
  public void fill(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    int x = getX();
    int y = getY();

    g.fillRect(x - width / 2, y - width / 2, width, height);
  }

}