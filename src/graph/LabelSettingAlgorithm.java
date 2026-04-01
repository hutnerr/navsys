package graph;

import java.util.HashMap;
import java.util.Map;

import feature.Intersection;
import feature.StreetSegment;

/**
 * A label-setting algorithm for calculating shortest paths.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @author Hunter Baker ( i added some )
 * @version 1.0
 */
public class LabelSettingAlgorithm extends AbstractShortestPathAlgorithm
{
  private PermanentLabelManager labels;

  /**
   * Explicit Value Constructor.
   * 
   * @param labels
   *          The LabelManager to use
   */
  public LabelSettingAlgorithm(final PermanentLabelManager labels)
  {
    super();
    this.labels = labels;
  }

  /**
   * Find the shortest path from the given origin Intersection to the given destination Intersection
   * on the given StreetNetwork.
   * 
   * @param origin
   * @param destination
   * @param net
   * @return The path
   */
  @Override
  public Map<String, StreetSegment> findPath(final int origin, final int destination,
      final StreetNetwork net)
  {
    Map<String, StreetSegment> result = new HashMap<String, StreetSegment>();
    labels.getLabel(origin).setValue(0.0);
    labels.getLabel(origin).makePermanent();

    int currentID = origin;

    do
    {
      // Adjust the labels of the reachable intersections
      Intersection current = net.getIntersection(currentID);
      for (StreetSegment segment : current.getOutbound())
      {
        labels.adjustHeadValue(segment);
      }

      // Find the intersection to make permanent
      Label smallestLabel = labels.getSmallestLabel();
      currentID = smallestLabel.getID();

      labels.makePermanent(currentID);

    }
    while (currentID != destination);

    currentID = destination;
    while (currentID != origin)
    {
      StreetSegment segment = labels.getLabel(currentID).getPredecessor();
      if (segment != null)
      {
        result.put(segment.getID(), segment);
        currentID = segment.getTail();
      }
      else
      {
        currentID = origin;
      }
    }
    return result;
  }

  /**
   * Finds the shorest path from an "origin" (which should be the dest you want) to all other nodes
   * in the graph.
   *
   * @param origin
   *          The source node to calculate paths from
   * @param net
   *          The street network
   * @return A map of labels containing distance and predecessor information for all nodes
   */
  public Map<Integer, Label> findAllPaths(final int origin, final StreetNetwork net)
  {
    // init labels
    labels.getLabel(origin).setValue(0.0);
    labels.getLabel(origin).makePermanent();

    int currentID = origin;
    boolean hasMoreNodes = true;

    do
    {
      // adjust the labels of the reachable intersections
      Intersection current = net.getIntersection(currentID);
      for (StreetSegment segment : current.getOutbound())
      {
        labels.adjustHeadValue(segment);
      }

      // find the intersection to make permanent
      Label smallestLabel = labels.getSmallestLabel();

      // if no more nodes can be reached
      if (smallestLabel == null || Double.isInfinite(smallestLabel.getValue()))
      {
        hasMoreNodes = false;
      }
      else
      {
        currentID = smallestLabel.getID();
        labels.makePermanent(currentID);
      }
    }
    while (hasMoreNodes);

    // intersection IDs to their labels
    Map<Integer, Label> allPathsInfo = new HashMap<>();

    // add all labels to result
    for (int i = 0; i < net.size(); i++)
    {
      allPathsInfo.put(i, labels.getLabel(i));
    }
    return allPathsInfo;
  }

  /**
   * Extract the path from origin to a destination using labels.
   *
   * @param destination
   *          The destination id
   * @param origin
   *          The origin id
   * @param allPathsInfo
   *          The map of all paths from dest
   * @return The path from origin to destination as a map of street segments
   */
  public Map<String, StreetSegment> extractPath(final int destination, final int origin,
      final Map<Integer, Label> allPathsInfo)
  {
    Map<String, StreetSegment> result = new HashMap<>();

    int currentID = destination;
    while (currentID != origin)
    {
      StreetSegment segment = allPathsInfo.get(currentID).getPredecessor();
      if (segment != null)
      {
        result.put(segment.getID(), segment);
        currentID = segment.getTail();
      }
      else
      {
        break; // no path exists
      }
    }

    return result;
  }

}
