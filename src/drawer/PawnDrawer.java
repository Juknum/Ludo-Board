package src.drawer;

import java.awt.*;

public class PawnDrawer extends DiscDrawer {
  @Override
  public void draw(Graphics g) {
    this.fill(g);
  }

  @Override
  public void fill(Graphics g) {
    Color c = g.getColor();
    super.fill(g);
    g.setColor(c);
 
    super.draw(g);
  }
}