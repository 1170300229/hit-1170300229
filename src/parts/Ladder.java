package parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * a ladder crossing the river, mutable type.
 * @author 1170300229
 *
 */
public class Ladder {
  private final int length;
  private final List<Integer> list = new ArrayList<>();
  private final Map<Integer, Integer> map = new HashMap<>();
  private int direction;
  private int nearVelocity;
  //Abstract Function:
  //      AF(length) = number of foot boards 
  //      AF(list) = the status of every foot boards
  //      AF(map) = the location of every monkey
  //      AF(direction) = the direction of monkeys on the ladder
  //      AF(nearVelocity) = the near velocity
  // Representation invariant:
  //      RI(length) = 1 <= length <= 20;
  //      RI(list) = list.size( ) == length
  //      RI(map) = map.size( ) <= length
  //      RI(direction) = (left -> right) ? 0 : 1
  //      RI(nearVelocity) = 1 <= abs(nearVelocity) <= 10
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a Ladder.
   * @param intputLength - length
   */
  public Ladder(int intputLength) {
    length = intputLength;
    for (int i = 0;i <= length; ++i)
      list.add(-1);
  }
  
  /**
   * add a monkey to the ladder.
   * @param id - the id of monkey
   * @param initPosition - the initial foot board
   * @param v - the velocity of the monkey
   */
  synchronized public void add (int id, int initPosition, int v) {
    list.set(initPosition, id);
    map.put(id, initPosition);
    nearVelocity = v;
    direction = (initPosition == 1 ? 0 : 1);
  }
  
  /**
   * move the monkey to next position.
   * @param id - the id of monkey
   * @param v - the velocity of monkey
   * @return next position
   */
  synchronized public int next(int id, int v) {
    if (!map.containsKey(id))
      throw new RuntimeException("Ladder:: not exist " + id);
    int nowPosition = map.get(id);
    list.set(nowPosition, -1);
    map.remove(id);
    if (v > 0) {
      int n = (nowPosition + v > length ? length : nowPosition + v);
      for (int i = nowPosition + 1;i <= n; ++i)
        if (list.get(i) != -1) {
          list.set(i - 1, id);
          map.put(id, i - 1);
          return i - 1;
        }
      if (nowPosition + v <= length) {
        list.set(nowPosition + v, id);
        map.put(id, nowPosition + v);
      }
      return nowPosition + v;
    }
    else {
      int n = (nowPosition + v < 1 ? 1 : nowPosition + v);
      for (int i = nowPosition - 1;i >= n; --i)
        if (list.get(i) != -1) {
          list.set(i + 1, id);
          map.put(id, i + 1);
          return i + 1;
        }
      if (nowPosition + v >= 1) {
        list.set(nowPosition + v, id);
        map.put(id, nowPosition + v);
      }
      return nowPosition + v;
    }
  }
  
  /**
   * if the ladder is empty.
   * @return if the ladder is empty
   */
  synchronized public boolean isEmpty() {
    return map.keySet().isEmpty();
  }
  
  /**
   * get the direction.
   * @return the direction
   */
  synchronized public int getDirection() {
    return direction;
  }
  
  /**
   * if the end point of the ladder is empty.
   * @param point - 0 means left, 1 means right
   * @return if the end point of the ladder is empty
   */
  synchronized public boolean isPointEmpty(int point) {
    return (point == 0) ? list.get(1) == -1 : list.get(length) == -1;
  }
  
  /**
   * get the near velocity
   * @return the near velocity
   */
  synchronized public int getNearVelocity() {
    return nearVelocity;
  }
  
}
