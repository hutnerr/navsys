package gps;

/**
 * GPSObserver for the observer pattern.
 * 
 * @author Hunter Baker
 */
public interface GPSObserver
{
  /**
   * The observer method to call.
   * 
   * @param sentence
   *          The sentence to handle
   */
  public void handleGPSData(String sentence);
}
