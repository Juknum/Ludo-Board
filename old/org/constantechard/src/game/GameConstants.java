package old.org.constantechard.src.game;

import java.awt.*;

public final class GameConstants {
  public static final int BARNS       = 6;
  public static final int COUNT       = 15;
  public static final int QUARTER     = BARNS * 2 + 1;
  public static final float CELL_SIZE = 0.85f;

  public static final Color[] colors = {
    new Color(0xffe783),
    new Color(0x95e5e5),
    new Color(0xff896a),
    new Color(0xcfff39)
  };

  public static final Color[] darkColors = {
    new Color(0xffcd00),
    new Color(0x2fcdcd),
    new Color(0xff3000),
    new Color(0x9acd00)
  };

  public static final Color TEXT_COLOR = new Color(0x818181);

  public static final int MAX_STAIRS = BARNS;
  @SuppressWarnings("PointlessArithmeticExpression")
  public static final int MAX_LENGTH = BARNS * 4 + (COUNT - BARNS * 2 - 2) * 4 + 4 - 1;

  public static final boolean DEBUG = true;

  public static final int MIN_PLAYER      = DEBUG ? 0 : 1;
  public static final int MAX_PLAYER      = 4;
  public static final int NB_REAL_PLAYERS = 0;
  public static final int NB_BOT_PLAYERS  = MAX_PLAYER - NB_REAL_PLAYERS;

  public static int NB_HORSES = 4;

  private GameConstants() {}
}