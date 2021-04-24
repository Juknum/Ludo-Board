package src.panel;

import src.game.Manager;
import src.player.Horse;
import src.player.Player;

import src.drawer.Directioner;
import src.drawer.DiscDrawer;
import src.drawer.StarDrawer;
import src.drawer.PawnDrawer;
import src.drawer.RectDrawer;
import src.drawer.TextDrawer;

import static src.game.CONSTANTS.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private final DiscDrawer discDrawer = new DiscDrawer();
  private final StarDrawer starDrawer = new StarDrawer();
  private final RectDrawer rectDrawer = new RectDrawer();
  private final PawnDrawer pawnDrawer = new PawnDrawer();
  private final Directioner textDirectioner = new Directioner(COUNT / 2.f, COUNT / 2.f);

  public BoardPanel(int size) {
    this.resize(size);
    Manager.setGamePanel(this);
    rectDrawer.setSide(BARNS);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setBackground(Color.WHITE);
    g.setColor(Color.BLACK);
    
    // grid:
    int height = this.getHeight();
    int width  = this.getWidth();
    int spacew = width / COUNT;
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

    rectDrawer.setDirectioner(moving);
    rectDrawer.setScale(spacew);

    pawnDrawer.setDirectioner(moving);
    pawnDrawer.setScale(spacew * .7f);

    starDrawer.setDirectioner(moving);
    starDrawer.setScale(spacew);

    // text
    textDirectioner.setScale(spacew);

    for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
      dir = Directioner.Direction.values()[(Directioner.Direction.values().length + colorIndex - 1) % Directioner.Direction.values().length];
      moving.setDir(dir);

      Color color = colors[colorIndex];
      g.setColor(color);

      // center triangles
      Polygon p = new Polygon();
      moving.resetMove();
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      moving.up(1.25f);
      moving.left(1.25f);
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      moving.right(2.5f);
      p.addPoint(Math.round(moving.getX()), Math.round(moving.getY()));
      g.fillPolygon(p);

      // barns
      g.setColor(darkColors[colorIndex]);
      moving.resetMove();
      rectDrawer.setSide(BARNS);
      moving.move((.5f + 1 + BARNS / 2.f), -(.5f + 1 + BARNS / 2.f));
      rectDrawer.fill(g);
      rectDrawer.setSide(BARNS * 0.90f);
      g.setColor(colors[colorIndex]);
      rectDrawer.fill(g);

      // stairs
      moving.resetMove();
      rectDrawer.setSide(CELL_SIZE);
      for (int ri = -1; ri < BARNS; ri++) {
        moving.up(1);
        g.setColor(colors[colorIndex]);
        rectDrawer.fill(g);
      }

      // circles
      g.setColor(darkColors[(colorIndex + 3) % darkColors.length]);
      discDrawer.setDiameter(CELL_SIZE);

      moving.up(1);
      discDrawer.fill(g);
      moving.right(1);
      discDrawer.fill(g);

      g.setColor(darkColors[colorIndex]);

      for (int di = -1; di < BARNS; di++) {
        moving.down(1);

        // start circle
        if (di == -1) {
          discDrawer.fill(g);

          discDrawer.setDiameter(CELL_SIZE * .5f);
          g.setColor(Color.white);
          discDrawer.fill(g);

          discDrawer.setDiameter(CELL_SIZE);
          g.setColor(darkColors[colorIndex]);
        }
        else if (di != BARNS - 1) discDrawer.fill(g);
      }

      for (int di = -1; di < BARNS; di++) {
        moving.right(1);
        discDrawer.fill(g);

        if (di == 2) {
          g.setColor(Color.white);
          starDrawer.setSize(16, 8, 16, 8);
          starDrawer.fill(g);
          
          g.setColor(darkColors[colorIndex]);
        }
      }
    }

    // text squares central
    TextDrawer textDrawer = new TextDrawer(textDirectioner, "", g);
    textDrawer.setSize(spacew, spaceh);
    textDrawer.setFont("Arial Black", Font.PLAIN, .8f * spacew);

    for (int i = 0; i < colors.length; i++) {
      textDirectioner.resetMove();
      textDirectioner.down(.95f);
      textDirectioner.right(.01f);

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
    ArrayList<Player> players = Manager.getPlayerList();
    for (int directionIndex = 0; directionIndex < players.size(); directionIndex++) {    
      g.setColor(pawnColors[directionIndex]);

      int horseIndex = 0;
      for (Horse h : players.get(directionIndex).getHorses()) {

        if (h.isInStairs()) {
          moving.setDir(Directioner.Direction.values()[(directionIndex + 3) % 4]);
          moving.resetMove();

          moving.up(MAX_STAIRS + 1 - h.getStairs());
        } 
        else if (h.isInBarns()) {

          moving.setDir(Directioner.Direction.values()[directionIndex % 4]);
          moving.resetMove();
          moving.setMove(-(1.5f + BARNS / 2.f), -(1.5f + BARNS / 2.f));  

          // left horses
          if (horseIndex % 2 == 0) {
            moving.left(1);
          } else {
            moving.right(1);
          }

          if (horseIndex < 2) {
            moving.up(1);
          } else {
            moving.down(1);
          }

        } 
        else {

          moving.setDir(Directioner.Direction.values()[(directionIndex+3) % 4 ]);

          moving.resetMove();
          moving.up(6);
          moving.right(1);

          int len = h.getLength();
          while (len > 0) {
            if (len <= 4) {
              moving.down(1);
            }
            else if (len > 4 && len <= 10) {
              if (len == 5) moving.down(1); // avoid the edge
              moving.right(1);
            }
            else if (len > 10 && len <= 12) {
              moving.down(1);
            }
            else if (len > 12 && len <= 17) {
              moving.left(1);
            }
            else if (len > 17 && len <= 23) {
              if (len == 18) moving.left(1); // avoid the edge
              moving.down(1);
            }
            else if (len > 23 && len <= 25) {
              moving.left(1);
            }
            else if (len > 25 && len <= 30) {
              moving.up(1);
            }
            else if (len > 30 && len <= 36) {
              if (len == 31) moving.up(1); // avoid the edge
              moving.left(1);
            }
            else if (len > 36 && len <= 38) {
              moving.up(1);
            }
            else if (len > 38 && len <= 43) {
              moving.right(1);
            }
            else if (len > 43 && len <= 49) {
              if (len == 44) moving.right(1); // avoid the edge
              moving.up(1);
            }
            else if (len > 49 && len <= 50) {
              moving.right(1);
            }
            else {
              System.out.println("Length > 50");
              break;
            }
            len--;
          }
        }

        pawnDrawer.draw(g);
        horseIndex++;

        //System.out.println("AI: " + (directionIndex + 1) + "\t| pawn: " + horseIndex + "\t| length: " + h.getLength() + "\t| barns: " + h.isInBarns() + "\t| stairs: " + h.getStairs());
        //if (horseIndex == 4) System.out.println("---");

      }
    }

    System.out.println("-- END --");
  }

  public void resize(int size) {
    Dimension s = new Dimension(size, size);
    this.setPreferredSize(s);
    this.setMinimumSize(s);
  }
}
