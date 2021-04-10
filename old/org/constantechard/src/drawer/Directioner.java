package old.org.constantechard.src.drawer;

public class Directioner {
  public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
  };

  private final float originX;
  private final float originY;
  private float movex = 0;
  private float movey = 0;

  private float scale = 1f;

  private Directioner.Direction dir = Direction.NORTH;

  public Directioner(float x, float y) {
    this.originX = x;
    this.originY = y;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public Directioner(float x, float y, Direction dir) {
    this.originX = x;
    this.originY = y;
    this.dir = dir;
  }

  public void setDir(Direction dir) {
    this.dir = dir;
  }

  public void move(float horizontal, float vertical) {
    movex += horizontal;
    movey += vertical;
  }

  public void left(float v) {
    move(-v, 0);
  }

  public void right(float v) {
    move(v, 0);
  }

  public void down(float v) {
    move(0, v);
  }

  public void up(float v) {
    move(0, -v);
  }

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

  public float getX() {
    return (originX + getMoveX()) * scale;
  }

  public float getY() {
    return (originY + getMoveY()) * scale;
  }

  public void setMove(float x, float y) {
    movex = x;
    movey = y;
  }

  public void resetMove() {
    setMove(0, 0);
  }

  @Override
  public String toString() {
    return "{ movex: " + movex + ", movey: " + movey + " x: " + getX() + ", y: " + getY() + "}";
  }
}