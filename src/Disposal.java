package fr.constantechard.ludo

public class Disposal {

  int x, y, h, w;

  public Disposal(int a, b){
    x = a;
    y = b;
    w = 30;
    h = 30;
  }

  public void draw(Graphics2D g){
    g.setColor(Color.WHITE);
    g.fillRect(x, y, 15*w, 15*h);
    g.setFont(new Font("SansSerif", Font.PLAIN, 30));
        g.drawString("Player 1", 90, 35);
        g.drawString("Player 2", 370, 35);
        g.drawString("Player 4", 90, 540);
        g.drawString("Player 3", 370, 540);
        g.drawString("Instruction:", 550,300);
        g.drawString("1.Press enter to roll the dice.", 550,350);
        g.drawString("2.Click on coin to move it", 550,400);
  }
}