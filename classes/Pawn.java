package fr.constant-echard.ludo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Pawn{

  int x, y;
  int current;
  int height, width;

  public Pawn(int h, int w){
    current = -1;
    x = -1;
    y = -1;
    height = h;
    width = w;
  }

  public void draw(Graphics2D g, int i, int j, int play){

    if (play == 0){
      g.setColor(Color.RED);
    } else if (play == 1){
      g.setColor(Color.GREEN);
    } else if (play == 2){
      g.setColor(Color.YELLOW);
    } else if (play == 4){
      g.setColor(Color.BLUE);
    }
    
    //TODO dimensionnement dessin pion (taille, forme, couleur, etc)
  }

}