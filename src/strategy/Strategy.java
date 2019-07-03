package strategy;

import parts.Ladder;

/**
 * the strategy used to choose a ladder, immutable type.
 * return a ladder,
 * or return null means wait
 * @author 1170300229
 *
 */
public interface Strategy {
  /**
   * get the ladder
   * @param v - velocity
   * @return a ladder, null means wait
   */
  public Ladder get(int v);
}
