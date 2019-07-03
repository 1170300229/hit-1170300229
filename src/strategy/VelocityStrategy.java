package strategy;

import java.util.Iterator;
import java.util.List;
import parts.Ladder;

/**
 * get the ladder by near velocity, immutable type.
 * @author 1170300229
 *
 */
public class VelocityStrategy implements Strategy{
  private final List<Ladder> list;
  //Abstract Function:
  //      AF(ladders) = all laders
  // Representation invariant:
  //      RI(ladders) = ladders.size() == n
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a velocity strategy
   * @param inputLadders - ladders
   */
  public VelocityStrategy(List<Ladder> inputLadders) {
    list = inputLadders;
  }
  
  /**
   * get the ladder by min near velocity
   * @param v - velocity
   * @return a ladder, null means wait
   */
  private Ladder getMin(int v) {
    Iterator<Ladder> iterator = list.iterator();
    int miv = -1; Ladder ladder = null;
    for (; iterator.hasNext();) {
      Ladder nowLadder = iterator.next();
      if (nowLadder.isEmpty())
        return nowLadder;
      if (v > 0 && nowLadder.getDirection() == 0 && nowLadder.isPointEmpty(0)) {
        if (miv == -1) {
          miv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        } else if (miv > nowLadder.getNearVelocity()) {
          miv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        }
      } else if (v < 0 && nowLadder.getDirection() == 1 && nowLadder.isPointEmpty(1)) {
        if (miv == -1) {
          miv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        } else if (miv > nowLadder.getNearVelocity()) {
          miv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        }
      }
    }
    return ladder;
  }
  
  
  /*
  @SuppressWarnings("unused")
  private Ladder getMax(int v) {
    Iterator<Ladder> iterator = list.iterator();
    int mxv = -1; Ladder ladder = null;
    for (; iterator.hasNext();) {
      Ladder nowLadder = iterator.next();
      if (nowLadder.isEmpty())
        return nowLadder;
      if (v > 0 && nowLadder.getDirection() == 0 && nowLadder.isPointEmpty(0)) {
        if (mxv == -1) {
          mxv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        } else if (mxv < nowLadder.getNearVelocity()) {
          mxv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        }
      } else if (v < 0 && nowLadder.getDirection() == 1 && nowLadder.isPointEmpty(1)) {
        if (mxv == -1) {
          mxv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        } else if (mxv < nowLadder.getNearVelocity()) {
          mxv = nowLadder.getNearVelocity();
          ladder = nowLadder;
        }
      }
    }
    return ladder;
  }*/
  
  @Override
  public Ladder get(int v) {
    return getMin(v);
    //return getMax(v);
  }

}
