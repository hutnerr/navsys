package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import feature.StreetSegmentObserver;

/**
 * Abstract implementation of a ShortestPathAlgorithm.
 * 
 * @author Hunter Baker
 */
public abstract class AbstractShortestPathAlgorithm implements ShortestPathAlgorithm
{
  private Collection<StreetSegmentObserver> observers;

  /**
   * Constructor.
   */
  public AbstractShortestPathAlgorithm()
  {
    this.observers = new ArrayList<StreetSegmentObserver>();
  }

  @Override
  public void addStreetSegmentObserver(final StreetSegmentObserver observer)
  {
    observers.add(observer);
  }

  @Override
  public void removeStreetSegmentObserver(final StreetSegmentObserver observer)
  {
    observers.remove(observer);
  }

  @Override
  public void notifyStreetSegmentObservers(final List<String> segmentIDs)
  {
    for (StreetSegmentObserver observer : observers)
    {
      observer.notify();
      observer.handleStreetSegments(segmentIDs);
    }
  }
}
