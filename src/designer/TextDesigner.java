package designer;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("unused")
public class TextDesigner extends CenterDesigner {
  private Graphics g;

  private String text = "";
  private String fontFamily = "Arial";
  private int fontStyle = Font.PLAIN;

  private float fontSize = 20;
  private Font font = new Font(fontFamily, fontStyle, 11);

  /**
   * Set the text we want to display (in any direction)
   * @param directioner
   * @param text
   * @param g
   */
  public TextDesigner(Directioner directioner, String text, Graphics g) {
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

  /**
   * Update the font of the text
   */
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

  /**
   * Return the family of the font used
   * @return Font
   */
  public String getFontFamily() {
    return fontFamily;
  }

  /**
   * Set the font on the text we want to display
   * @param fontFamily
   */
  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
    this.updateFont();
  }

  /**
   * Return the style of font used
   * @return Font
   */
  public int getFontStyle() {
    return fontStyle;
  }

  /**
   * Set the style of font (plain, bold, etc)
   * @param fontStyle
   */
  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
    this.updateFont();
  }

  /**
   * Return the size of the displayed text
   * @return int
   */
  public float getFontSize() {
    return fontSize;
  }

  /**
   * Set the size of the displayed text
   * @param fontSize
   */
  public void setFontSize(float fontSize) {
    this.fontSize = fontSize;
    this.updateFont();
  }

  /**
   * Return the text displayed
   * @return String
   */
  public String getText() {
    return text;
  }

  /**
   * Set the text we want to display (similar to TextDesigner)
   * @param text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Set both font, font style and the size in pixel of the text we want to display
   * @param fontFamily
   * @param fontStyle
   * @param pixelSize
   */
  public void setFont(String fontFamily, int fontStyle, float pixelSize) {
    this.fontFamily = fontFamily;
    this.fontStyle = fontStyle;
    this.fontSize = pixelSize;

    updateFont();
  }

  /**
   * Design the area where the text will be displayed
   * @param g
   */
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
    int y = Math.round(topy + ((float) (r2d.getHeight() + lm.getHeight()) / 4.f)) - (g.getFontMetrics(this.font).getAscent() / 4);

    g.drawString(text, x, y);
    g.setFont(oldFont);
  }

  /**
   * Draw the area designed and display the text
   * @param g
   * @param text
   */
  public void draw(Graphics g, String text) {
    this.setText(text);
    this.draw(g);
  }

  /**
   * Fill the area where the text is displayed
   * @param g
   */
  @Override
  public void fill(Graphics g) {
    this.draw(g);
  }

}