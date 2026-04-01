package graph;

import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;
import feature.StreetSegmentSubject;

/**
 * ShorestPathAlgorithm.
 * 
 * @author Hunter Baker
 */
public interface ShortestPathAlgorithm extends StreetSegmentSubject
{
  /**
   * Find the path.
   * 
   * @param origin
   *          The origin
   * @param destination
   *          The destination
   * @param net
   *          The network
   * @return The path
   */
  public Map<String, StreetSegment> findPath(int origin, int destination, StreetNetwork net);

  /**
   * Add an observer.
   * 
   * @param observer
   *          The observer to add.
   */
  public void addStreetSegmentObserver(StreetSegmentObserver observer);

  /**
   * Remove an observer.
   * 
   * @param observer
   *          The observer to remove
   */
  public void removeStreetSegmentObserver(StreetSegmentObserver observer);

  /**
   * Notify the observers.
   * 
   * @param segmentIDs
   *          The segments to notify.
   */
  public void notifyStreetSegmentObservers(List<String> segmentIDs);
}
