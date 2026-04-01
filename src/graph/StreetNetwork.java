package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.Street;
import feature.StreetSegment;

/**
 * StreetNetwork.
 * 
 * @author Hunter Baker
 */
public class StreetNetwork
{
  private List<Intersection> intersections;

  /**
   * Constructor.
   */
  public StreetNetwork()
  {
    this.intersections = new ArrayList<Intersection>();
  }

  /**
   * Add an intersection.
   * 
   * @param index
   * @param intersection
   */
  public void addIntersection(final int index, final Intersection intersection)
  {
    intersections.add(index, intersection);
  }

  /**
   * Get the intersection.
   * 
   * @param index
   * @return the intersection
   */
  public Intersection getIntersection(final int index)
  {
    return intersections.get(index);
  }

  /**
   * Getter for the size.
   * 
   * @return The size
   */
  public int size()
  {
    return intersections.size();
  }

  /**
   * Create the street network.
   * 
   * @param streets
   * @return The StreetNetwork
   */
  public static StreetNetwork createStreetNetwork(final Map<String, Street> streets)
  {
    StreetNetwork network = new StreetNetwork();

    // so we can track by ids
    Map<Integer, Intersection> intersectionMap = new HashMap<>();

    for (Street street : streets.values())
    {
      // for each segment in a street...
      Iterator<StreetSegment> segmentIterator = street.getSegments();
      while (segmentIterator.hasNext())
      {
        StreetSegment segment = segmentIterator.next();

        // get th e head and tail
        int tailID = segment.getTail();
        int headID = segment.getHead();

        // get the tail intersection
        Intersection tailIntersection = intersectionMap.get(tailID);
        if (tailIntersection == null)
        {
          tailIntersection = new Intersection();
          intersectionMap.put(tailID, tailIntersection);
        }

        // get the head intersection
        Intersection headIntersection = intersectionMap.get(headID);
        if (headIntersection == null)
        {
          headIntersection = new Intersection();
          intersectionMap.put(headID, headIntersection);
        }

        // connect
        tailIntersection.addOutbound(segment);
        headIntersection.addInbound(segment);
      }
    }

    // sort then add intersections ordered by they ids
    List<Integer> sortedIDs = new ArrayList<>(intersectionMap.keySet());
    Collections.sort(sortedIDs);

    for (Integer id : sortedIDs)
    {
      network.addIntersection(network.size(), intersectionMap.get(id));
    }

    return network;
  }

}
