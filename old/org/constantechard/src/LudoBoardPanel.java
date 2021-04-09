package org.constantechard.src;

import org.constantechard.src.drawer.*;
import org.constantechard.src.game.GameManager;
import org.constantechard.src.game.Horse;
import org.constantechard.src.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static org.constantechard.src.game.GameConstants.*;

public class LudoBoardPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DiscDrawer      discDrawer      = new DiscDrawer();
  private final RectangleDrawer rectangleDrawer = new RectangleDrawer();
  private final PawnDrawer      pawnDrawer       = new PawnDrawer();
  private final Directioner     textDirectioner = new Directioner(COUNT/2.f, COUNT/2.f);

  public LudoBoardPanel(int size) {
    this.resize(size);
    GameManager.setGamePanel(this);
    rectangleDrawer.setSide(BARNS);
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    this.setBackground(Color.WHITE);
    g.setColor(Color.BLACK);

    // grid
    int height = this.getHeight();
    int width  = this.getWidth();
    int spacew = width / COUNT;

    @SuppressWarnings("unused")
    int spaceh = height / COUNT;

    // directioner
    float centerx = COUNT / 2.f;
    float centery = COUNT / 2.f;
    Directioner.Direction dir;
    Directioner moving = new Directioner(centerx, centery);
    moving.setScale(spacew);

    // drawers
    discDrawer.setDirectioner(moving);
    discDrawer.setScale(spacew);
    rectangleDrawer.setDirectioner(moving);
    rectangleDrawer.setScale(spacew);
    pawnDrawer.setDirectioner(moving);
    pawnDrawer.setScale(spacew * .7f);

    // text
    textDirectioner.setScale(spacew);

    for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
      dir = Directioner.Direction.values()[(Directioner.Direction.values().length + colorIndex - 1) % Directioner.Direction.values().length];
      moving.setDir(dir);

      Color color = colors[colorIndex];
      g.setColor(color);

      // center triangle
      Polygon p = new Polygon();
      moving.resetMove();
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      moving.up(.5f);
      moving.left(.5f);
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      moving.right(1f);
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      g.fillPolygon(p);

      // barns
      moving.resetMove();
      rectangleDrawer.setSide(CELL_SIZE);
      for (int ri = 0; ri < BARNS; ri++) {
        moving.up(1);
        g.setColor(colors[colorIndex]);
        rectangleDrawer.fill(g);
      }

      // circles
      g.setColor(darkColors[colorIndex]);
      discDrawer.setDiameter(CELL_SIZE);

      moving.up(1);
      discDrawer.fill(g);
      moving.right(1);
      discDrawer.fill(g);

      // start circle
      discDrawer.setDiameter(CELL_SIZE * .3f);
      g.setColor(Color.white);
      discDrawer.fill(g);
      discDrawer.setDiameter(CELL_SIZE);
      g.setColor(darkColors[colorIndex]);

      for (int di = 0; di < BARNS; di++) {
        moving.down(1);
        discDrawer.fill(g);
      }

      for (int di = 0; di < BARNS; di++) {
        moving.right(1);
        discDrawer.fill(g);
      }
    }

    // text squares central
    TextDrawer textDrawer = new TextDrawer(textDirectioner, "", g);
    textDrawer.setSize(spacew, spacew);
    textDrawer.setFont("Arial Black", Font.PLAIN, .8f * spacew);

    for (int i = 0; i < colors.length; i++) {
      textDirectioner.resetMove();
      textDirectioner.down(1);

      for (int ni = MAX_STAIRS; ni > 0; ni--) {
        g.setColor(TEXT_COLOR);
        textDrawer.draw(g, "" + ni);
        textDirectioner.down(1);
      }

      Graphics2D g2d = (Graphics2D) g;

      // rotates the coordinate by 90° counterclockwise
      AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI / 2.0, spacew * COUNT / 2.f, spacew * COUNT / 2.f);
      g2d.transform(rotate);
    }

    // pawn
    ArrayList<Player> players = GameManager.getPlayerList();
    for (int directionIndex = 0; directionIndex < Directioner.Direction.values().length; directionIndex++) {
      moving.setDir(Directioner.Direction.values()[directionIndex]);

      g.setColor(colors[directionIndex]);

      int horseIndex = 0;
      for (Horse h : players.get(directionIndex).getHorses()) {
        moving.resetMove();

        if (h.isInStairs()) {
          moving.up(MAX_STAIRS - h.getStairs());
        }
        else if (h.isInBarns()) {
          moving.setMove(-(.5f + 1 + BARNS/2.f), -(.5f + 1 + BARNS/2.f));

          // even horse left
          if (horseIndex % 2 == 0) {
            moving.left(1);
          }
          else {
            moving.right(1);
          }

          if (horseIndex < 2) {
            moving.up(1);
          }
          else {
            moving.down(1);
          }
          
        }
        else {
          moving.up(MAX_STAIRS + 1);
          moving.right(1);

          int len = h.getLength();
          int i   = 0;

          while(len/QUARTER > 0) {
            moving.setDir(Directioner.Direction.values()[(i++) % Directioner.Direction.values().length]);
            len /= QUARTER;
          }

          // down
          int tmp = BARNS;
          while(len > 0 && tmp > 0) {
            moving.down(1);
            tmp--;
            len--;
          }

          if (len > 0) {
            tmp = BARNS;
          }
          while (len > 0 && tmp > 0) {
            moving.right(1);
            tmp--;
            len--;
          }

          moving.down(len);

        }

        pawnDrawer.draw(g);
        horseIndex++;
      }
    }
  }

  public void resize(int size) {
    Dimension s = new Dimension(size, size);
    this.setPreferredSize(s);
    this.setMinimumSize(s);
  }
}