package designer;

import java.awt.Graphics;

public class DiscDesigner extends CenterDesigner {

  /**
   * Set the diameter of a circle to the lenght 'd'
   * @param d float
   */
  public void setDiameter(float d) {
    setSide(d);
  }

  /**
   * Return the diameter of a circle
   */
  public float getDiameter() {
    return this.getWidthFloat();
  }

  /**
   * Draw a circle
   * @param g Graphics
   */
  @Override
  public void draw(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    int x = getX();
    int y = getY();

    g.drawOval(x - width / 2, y - height / 2, width, height);
  }

  /**
   * Fill the drawn circle
   * @param g Graphics
   */
  @Override
  public void fill(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    int x = getX();
    int y = getY();

    g.fillOval(x - width / 2, y - height / 2, width, height);
  }
  
}