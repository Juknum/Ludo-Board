package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
  private int key = -1;

  @Override
  public void keyPressed(KeyEvent e) {
    char ch = e.getKeyChar();

    switch (ch) {
      case '&': case '1':
        setKeyIndex(0);
        break;
      case 'é': case '2':
        setKeyIndex(1);
        break;
      case '"': case '3':
        setKeyIndex(2);
        break;
      case '\'': case '4':
        setKeyIndex(3);
        break;
      default:
        setKeyIndex(-1);
        break;
    }
  }

  private final void setKeyIndex(int key) {
    this.key = key;
  }

  public final int getKeyIndex() {
    return key;
  }
}
