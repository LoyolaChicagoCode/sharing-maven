package sharing;

/**
 * This class illustrates the race condition arising from concurrent
 * access to shared data.  Overlapping invocations of the inc method
 * lead to the wrong result.  The class can be made thread-safe
 * by making the class fully synchronized.
 */

public class Shared {

  private int value = 0;

  public void inc() {
    try {
      int local = value;
      Thread.sleep(10000);
      value = local + 1;
    }
    catch (InterruptedException e) {
      System.err.println(this +" interrupted while sleeping");
    }
  }

  public int get() {
    return value;
  }

  public void reset() {
    value = 0;
  }
}
