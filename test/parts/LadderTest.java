package parts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LadderTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * add:           1 <= id <= 2
   *                1 <= v <= 10
   *                left -> right | left <- right
   *                
   * next           1 <= next foot board <= length
   *                next foot board > length
   *
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test add, next.
   */
  @Test
  public void testLadderCase1() {
    Ladder ladder = new Ladder(20);
    ladder.add(1, 1, 10);
    assertEquals(11, ladder.next(1, 10));
    assertEquals(21, ladder.next(1, 10));
    ladder.add(2, 20, -10);
    assertEquals(10, ladder.next(2, -10));
    assertEquals(0, ladder.next(2, -10));
  }
  
  /**
   * test add, next.
   */
  @Test
  public void testLadderCase2() {
    Ladder ladder = new Ladder(20);
    ladder.add(1, 1, 10);
    assertEquals(11, ladder.next(1, 10));
    ladder.add(2, 20, -10);
    assertEquals(19, ladder.next(1, 10));
    assertEquals(20, ladder.next(2, -10));
  }
  
  /**
   * test isEmpty.
   */
  @Test
  public void testLadderCase3() {
    Ladder ladder = new Ladder(20);
    ladder.add(1, 1, 10);
    assertEquals(11, ladder.next(1, 10));
    ladder.add(2, 20, -10);
    assertEquals(19, ladder.next(1, 10));
    assertEquals(20, ladder.next(2, -10));
    assertFalse(ladder.isEmpty());
  }
  
  /**
   * test getDirection.
   */
  @Test
  public void testLadderCase4() {
    Ladder ladder = new Ladder(20);
    ladder.add(1, 1, 10);
    assertEquals(11, ladder.next(1, 10));
    assertEquals(0, ladder.getDirection());
  }
  
  /**
   * test isPointEmpty.
   * test getNearVelocity.
   */
  @Test
  public void testLadderCase5() {
    Ladder ladder = new Ladder(20);
    ladder.add(1, 1, 10);
    assertFalse(ladder.isPointEmpty(0));
    assertTrue(ladder.isPointEmpty(1));
    assertEquals(20, ladder.next(1, 19));
    assertTrue(ladder.isPointEmpty(0));
    assertFalse(ladder.isPointEmpty(1));
    assertEquals(10, ladder.getNearVelocity());
    ladder.add(2, 20, -10);
    assertFalse(ladder.isPointEmpty(1));
    assertEquals(10, ladder.next(2, -10));
    assertTrue(ladder.isPointEmpty(1));
  }
}
