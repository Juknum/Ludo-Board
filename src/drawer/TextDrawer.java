package drawer;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("unused")
public class TextDrawer extends CenterDrawer {
  private Graphics g;

  private String text = "";
  private String fontFamily = "Arial";
  private int fontStyle = Font.PLAIN;

  private float fontSize = 20;
  private Font font = new Font(fontFamily, fontStyle, 11);

  public TextDrawer(Directioner directioner, String text, Graphics g) {
    this.text = text;
    this.g = g;

    this.setDirectioner(directioner);
    this.setFontSize(20);
  }

  @Override
  public void setScale(float scale) {
    super.setScale(scale);
    updateFont();
  }

  protected final void updateFont() {
    int size = 11;
    Font tmpFont = new Font(fontFamily, fontStyle, size);

    @SuppressWarnings("SpellCheckingInspection")
    Rectangle2D r2d = g.getFontMetrics(tmpFont).getStringBounds("ABCD", g);
    float val = (float) r2d.getHeight();
    float dest = this.fontSize;

    size = Math.round(size * dest / val);

    this.font = new Font(fontFamily, fontStyle, size);
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
    this.updateFont();
  }

  public int getFontStyle() {
    return fontStyle;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
    this.updateFont();
  }

  public float getFontSize() {
    return fontSize;
  }

  public void setFontSize(float fontSize) {
    this.fontSize = fontSize;
    this.updateFont();
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setFont(String fontFamily, int fontStyle, float pixelSize) {
    this.fontFamily = fontFamily;
    this.fontStyle = fontStyle;
    this.fontSize = pixelSize;

    updateFont();
  }

  @Override
  public void draw(Graphics g) {
    float topx = getX();
    float topy = getY();

    Font oldFont = g.getFont();
    Color c = g.getColor();

    // get text size
    g.setFont(this.font);
    LineMetrics lm = g.getFontMetrics(this.font).getLineMetrics(this.text, g);
    Rectangle2D r2d = g.getFontMetrics(this.font).getStringBounds(this.text, g);

    int x = Math.round(topx - ((float) r2d.getWidth() / 2.f));
    int y = Math.round(topy + ((float) (r2d.getHeight() + lm.getHeight()) / 4.f))
        - (g.getFontMetrics(this.font).getAscent() / 4);

    g.drawString(text, x, y);
    g.setFont(oldFont);
  }

  public void draw(Graphics g, String text) {
    this.setText(text);
    this.draw(g);
  }

  @Override
  public void fill(Graphics g) {
    this.draw(g);
  }

}