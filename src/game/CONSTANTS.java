package src.game;

import java.awt.*;

public final class CONSTANTS {
  public static final int BARNS      = 5;
  public static final int COUNT      = 15;
  public static final int QUARTER    = 6;
  public static final int MAX_STAIRS = 6;

  public static final boolean DEBUG = true;

  public static final int MIN_PLAYER = DEBUG ? 0 : 1;
  public static final int MAX_PLAYER = 4;

  public static final int NB_REAL_PLAYERS = 0;
  public static final int NB_BOT_PLAYERS  = MAX_PLAYER - NB_REAL_PLAYERS;
  
  public static final float CELL_SIZE = 0.85f;
  
  public static int NB_HORSES = 4;

  @SuppressWarnings("PointlessArithmeticExpression")
  public static final int MAX_LENGTH = 4 * QUARTER - 1 + 6; //BARNS * 4 + (COUNT - BARNS * 2 - 2) * 4 + 4 - 1;

  public static final Color[] colors = {
    new Color(0xffd800), 
    new Color(0x95e5e5), 
    new Color(0xff896a),
    new Color(0xcfff39)
  };

  public static final Color[] darkColors = {
    new Color(0xccb149), 
    new Color(0x2fcdcd), 
    new Color(0xff3000),
    new Color(0x9acd00)
  };

  public static final Color[] pawnColors = {
    new Color(0xa38a00),
    new Color(0x239999),
    new Color(0xaf2000),
    new Color(0x759900)
  };

  public static final Color TEXT_COLOR = new Color(0x818181);

  private CONSTANTS() {}
}
