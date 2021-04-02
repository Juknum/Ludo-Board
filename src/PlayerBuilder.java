package fr.constant-echard.ludo

import java.awt.Graphics2D;

public class playerBuilder {

  Player[] pl = new player[4];

  int [][] beginx = {
                {1,1,3,3},
			          {10,10,12,12},
			          {10,10,12,12},
			          {1,1,3,3}
  };

  int [][] beginy = {
                {1,3,1,3},
			          {1,3,1,3},
		          	{10,12,10,12},
			          {10,12,10,12}
  };

  public PlayerBuilder(int h, int w){
    for(int i = 0; i<4; i++){
      pl[i] = new Player(h, w);
    }
  }

  public void draw(Graphics2D g){
    for(int i = 0; i<4; i++){
      for(int j = 0; j<4; i++){
        pl[i].pa[j].draw(g, beginx[i][j], beginy[i][j]);
      }
    }
  }
  
}