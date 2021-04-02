package fr.constant-echard.ludo;

import java.awt.Graphics2D;

public class Player {

  int height, width, status, coin;
  Pawn[] pa = new pawn[4];

  public Player(int height, int width){
    status = -1;
    coin = 0;
    for(int i = 0; i<4; i++){
      pa[i] = new Pawn(height, width);
    }
  }
}