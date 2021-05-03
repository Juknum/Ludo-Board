package gameDesigner;

public class DirectionSetupper {
  public enum Direction {
    NORTH, EAST, SOUTH, WEST
  };

  private final float originX;
  private final float originY;
  private float movex = 0;
  private float movey = 0;

  private float scale = 1f;

  private DirectionSetupper.Direction dir = Direction.NORTH;

  /**
   * Set a direction setupper
   * @param x float
   * @param y float
   */
  public DirectionSetupper(float x, float y) {
    this.originX = x;
    this.originY = y;
  }

  /**
   * Set the scale
   * @param scale float
   */
  public void setScale(float scale) {
    this.scale = scale;
  }

  /**
   * Set a direction setupper like a previous method but with a specific direction
   * @param x float
   * @param y float
   * @param dir Direction
   */
  public DirectionSetupper(float x, float y, Direction dir) {
    this.originX = x;
    this.originY = y;
    this.dir = dir;
  }

  /**
   * Set the direction
   * @param dir Direction
   */
  public void setDir(Direction dir) {
    this.dir = dir;
  }

  /**
   * Move the "pencil"
   * @param horizontal float
   * @param vertical float
   */
  public void move(float horizontal, float vertical) {
    movex += horizontal;
    movey += vertical;
  }

  /**
   * Make the "pencil" move by v lenght to the left
   * @param v float
   */
  public void left(float v) {
    move(-v, 0);
  }

  /**
   * Make the "pencil" move by v lenght to the right
   * @param v float
   */
  public void right(float v) {
    move(v, 0);
  }

  /**
   * Make the "pencil" move by v lenght to the bottom
   * @param v float
   */
  public void down(float v) {
    move(0, v);
  }

  /**
   * Make the "pencil" move by v lenght to the top
   * @param v float
   */
  public void up(float v) {
    move(0, -v);
  }

  /**
   * Return the way to move on X axis depending on the direction
   * @return float
   */
  public float getMoveX() {
    switch (dir) {
    case NORTH:
      return movex;
    case EAST:
      return -movey;
    case SOUTH:
      return -movex;
    default:
      return movey;
    }
  }

  /**
   * Return the way to move on Y axis depending on direction
   * @return
   */
  public float getMoveY() {
    switch (dir) {
    case NORTH:
      return movey;
    case EAST:
      return movex;
    case SOUTH:
      return -movey;
    default:
      return -movex;
    }
  }

  /**
   * Return the coordinates on X axis
   * @return float
   */
  public float getX() {
    return (originX + getMoveX()) * scale;
  }

  /**
   * Return the coordinates on Y axis
   * @return float
   */
  public float getY() {
    return (originY + getMoveY()) * scale;
  }

  /**
   * Set the values of the moving on X and Y axis
   * @param x float
   * @param y float
   */
  public void setMove(float x, float y) {
    movex = x;
    movey = y;
  }

  /**
   * Reset the "pencil" to (0,0) (center of the frame)
   */
  public void resetMove() {
    setMove(0, 0);
  }
}