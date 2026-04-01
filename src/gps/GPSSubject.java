package gps;

/**
 * GPSSubject.
 * 
 * @author Hunter Baker
 */
public interface GPSSubject
{
  /**
   * Add a GPS Observer.
   * 
   * @param observer
   *          the observer to add
   */
  public void addGPSObserver(GPSObserver observer);

  /**
   * Notify observers.
   * 
   * @param sentence
   *          The sentence to send them
   */
  public void notifyGPSObservers(String sentence);

  /**
   * Remove an observer.
   * 
   * @param observer
   *          the observer to remove.
   */
  public void removeGPSObserver(GPSObserver observer);
}
