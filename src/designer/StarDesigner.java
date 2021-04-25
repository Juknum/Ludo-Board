package designer;

import java.awt.*;

public class StarDesigner extends CenterDesigner {

  private int radius[] = {16, 8, 16, 8};

  /**
   * Set the different lenght of the star
   * @param a
   * @param b
   * @param c
   * @param d
   */
  public void setSize(int a, int b, int c, int d) {
    this.radius[0] = a;
    this.radius[1] = b;
    this.radius[2] = c;
    this.radius[3] = d;
  }
  
  /**
   * Draw the star 
   * @param g
   */
  @Override
  public void draw(Graphics g) {
    int midX = getX();
    int midY = getY();
    int nPoints = 32;
    int[] X = new int[nPoints];
    int[] Y = new int[nPoints];

    for (double current = 0.0; current < nPoints; current++) {
      int i = (int) current;
      double x = Math.cos(current * ((2 * Math.PI) / 10)) * this.radius[i % 4];
      double y = Math.sin(current * ((2 * Math.PI) / 10)) * this.radius[i % 4];

      X[i] = (int) x + midX;
      Y[i] = (int) y + midY;
    }

    g.drawPolygon(X, Y, nPoints);

  }

  /**
   * Fill the drawn star 
   * @param g
   */
  @Override
  public void fill(Graphics g) {
    int midX = getX();
    int midY = getY();
    int nPoints = 32;
    int[] X = new int[nPoints];
    int[] Y = new int[nPoints];

    for (double current = 0.0; current < nPoints; current++) {
      int i = (int) current;
      double x = Math.cos(current * ((2 * Math.PI) / 10)) * this.radius[i % 4];
      double y = Math.sin(current * ((2 * Math.PI) / 10)) * this.radius[i % 4];

      X[i] = (int) x + midX;
      Y[i] = (int) y + midY;
    }

    g.fillPolygon(X, Y, nPoints);

  }
}
