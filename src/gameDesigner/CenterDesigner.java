package gameDesigner;

import java.awt.Graphics;

public abstract class CenterDesigner {
  private float scale = 1f;
  private float width = 1f;
  private float height = 1f;

  private DirectionSetupper directioner;

  public CenterDesigner() {
  }

  /**
   * Set the direction (SOUTH, EAST, WEST, NORTH) of a directioner object
   * @param directioner
   */
  public void setDirectionsetupper(DirectionSetupper directioner) {
    this.directioner = directioner;
  }

  /**
   * Set the scale
   * @param scale float
   */
  public void setScale(float scale) {
    this.scale = scale;
  }

  /**
   * Set the width
   * @param width float
   */
  public void setWidth(float width) {
    this.width = width;
  }

  /**
   * Set the height
   * @param height float
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * Set the size thanks to the two previous methods
   * @param width float
   * @param height float
   */
  public void setSize(float width, float height) {
    this.setWidth(width);
    this.setHeight(height);
  }

  /**
   * Set the size and define it as a square
   * @param side
   */
  public void setSide(float side) {
    this.setSize(side, side);
  }

  /**
   * Return the coordinates on X axis 
   * @return float
   */
  public int getX() {
    return Math.round(directioner.getX());
  }

  /**
   * Return the coordinates on Y axis
   * @return float
   */
  public int getY() {
    return Math.round(directioner.getY());
  }

  /**
   * Return the width
   * @return float
   */
  public float getWidthFloat() {
    return scale * width;
  }

  /**
   * Return rounded value of the width
   * @return int
   */
  public int getWidth() {
    return Math.round(getWidthFloat());
  }

  /**
   * Return the height
   * @return float
   */
  public float getHeightFloat() {
    return scale * height;
  }

  /**
   * Return rounded value of the height
   * @return int 
   */
  public int getHeight() {
    return Math.round(getHeightFloat());
  }

  public abstract void draw(Graphics g);
  public abstract void fill(Graphics g);
}