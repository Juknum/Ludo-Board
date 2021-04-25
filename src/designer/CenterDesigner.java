package designer;

import java.awt.*;

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

  public void setScale(float scale) {
    this.scale = scale;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public void setSize(float width, float height) {
    this.setWidth(width);
    this.setHeight(height);
  }

  public void setSide(float side) {
    this.setSize(side, side);
  }

  public int getX() {
    return Math.round(directioner.getX());
  }

  public int getY() {
    return Math.round(directioner.getY());
  }

  public float getWidthFloat() {
    return scale * width;
  }

  public int getWidth() {
    return Math.round(getWidthFloat());
  }

  public float getHeightFloat() {
    return scale * height;
  }

  public int getHeight() {
    return Math.round(getHeightFloat());
  }

  public abstract void draw(Graphics g);

  public abstract void fill(Graphics g);
}