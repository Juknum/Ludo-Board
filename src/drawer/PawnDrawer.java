package drawer;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

public class PawnDrawer extends DiscDrawer {
  private String fontFamily = "Arial";
  private int fontStyle = Font.PLAIN;
  private Font font = new Font(fontFamily, fontStyle, 11);
  private int rotation = 0;
  private int x;
  private int y;
  private int index = 0;

  /**
   * Reset the rotation of the graphic
   */
  public void resetRotation() {
    this.rotation = 0;
  }

  /**
   * Rotate the given graphics by PI/2 counter clockwise
   * @param g Graphics
   */
  public void doRotation(Graphics g) {
    ((Graphics2D) g).rotate(Math.PI/2);
    this.rotation++;
    if (rotation >= 4) rotation = 0;
  }

  /**
   * Update coordinates following the actual rotation
   */
  public void getCoordinates() {
    switch (rotation) {
    case 0:
      x = getX();
      y = getY() - 10;
      break;
    case 1:
      x = getY();
      y = -getX() - 10;
      break;
    case 2:
      x = -getX();
      y = -getY() - 10;
      break;
    case 3:
      x = -getY();
      y = getX() - 10;
      break;
    default:
      break;
    }
  }

  /**
   * Draw the outside of a pawn
   * @param p Graphics
   */
  @Override
  public void draw(Graphics g) {
    this.fill(g);
    Color c = g.getColor();
    g.setColor(c);

    getCoordinates();

    int width = getWidth() / 2;
    int height = getHeight() / 2;

    g.drawOval(x - width / 2, y - height / 2, width, height);

    Polygon p = new Polygon();
    p.addPoint(x, y);
    p.addPoint(x + 10, y + 30);
    p.addPoint(x - 10, y + 30);
    g.drawPolygon(p);
    
    g.setColor(Color.white);
    g.setFont(this.font);

    LineMetrics lm = g.getFontMetrics(this.font).getLineMetrics(""+index, g);
    Rectangle2D r2d = g.getFontMetrics(this.font).getStringBounds(""+index, g);

    int outX = Math.round(x - ((float) r2d.getWidth() / 2.f));
    int outY = Math.round(y + ((float) (r2d.getHeight() + lm.getHeight()) / 4.f)) - (g.getFontMetrics(this.font).getAscent() / 4);

    g.drawString(""+index, outX, outY);

    g.setColor(c);
  }

  public void drawHorse(Graphics g, int index) {
    this.index = index;
    this.draw(g);
  }

  /**
   * Fill the pawn
   * @param g Graphics
   */
  @Override
  public void fill(Graphics g) {
    Color c = g.getColor();
    g.setColor(c);

    getCoordinates();

    int width = getWidth() / 2;
    int height = getHeight() / 2;

    g.fillOval(x - width / 2, y - height / 2, width, height);

    Polygon p = new Polygon();
    p.addPoint(x, y);
    p.addPoint(x + 10, y + 30);
    p.addPoint(x - 10, y + 30);
    g.fillPolygon(p);
  }
}