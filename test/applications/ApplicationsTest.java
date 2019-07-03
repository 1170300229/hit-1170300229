package applications;

import org.junit.Test;

public class ApplicationsTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * just test main
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test main.
   */
  @Test
  public void testApplicationsCase1() {
    String[] strings = new String[10];
    Applications.main(strings);
  }
}
