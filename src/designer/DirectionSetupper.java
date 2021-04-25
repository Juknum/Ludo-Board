package designer;

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
   * @param x
   * @param y
   */
  public DirectionSetupper(float x, float y) {
    this.originX = x;
    this.originY = y;
  }

  /**
   * Set the scale
   * @param scale
   */
  public void setScale(float scale) {
    this.scale = scale;
  }

  /**
   * Set a direction setupper like a previous method but with a specific direction
   * @param x
   * @param y
   * @param dir
   */
  public DirectionSetupper(float x, float y, Direction dir) {
    this.originX = x;
    this.originY = y;
    this.dir = dir;
  }

  /**
   * Set the direction
   * @param dir
   */
  public void setDir(Direction dir) {
    this.dir = dir;
  }

  /**
   * Allow a pawn to move
   * @param horizontal
   * @param vertical
   */
  public void move(float horizontal, float vertical) {
    movex += horizontal;
    movey += vertical;
  }

  /**
   * Make a pawn move by v lenght to the left
   * @param v
   */
  public void left(float v) {
    move(-v, 0);
  }

  /**
   * Make a pawn move by v lenght to the right
   * @param v
   */
  public void right(float v) {
    move(v, 0);
  }

  /**
   * Make a pawn move by v lenght to the bottom
   * @param v
   */
  public void down(float v) {
    move(0, v);
  }

  /**
   * Make a pawn move by v lenght to the top
   * @param v
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
   * @param x
   * @param y
   */
  public void setMove(float x, float y) {
    movex = x;
    movey = y;
  }

  /**
   * Reset the setMove methos
   */
  public void resetMove() {
    setMove(0, 0);
  }

  /**
   * Convert data from previous methods to display them in command prompt
   * @return String
   */
  @Override
  public String toString() {
    return "{ movex: " + movex + ", movey: " + movey + " x: " + getX() + ", y: " + getY() + "}";
  }
}