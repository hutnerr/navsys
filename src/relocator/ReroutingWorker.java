package relocator;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import feature.StreetSegment;
import graph.Label;
import graph.LabelSettingAlgorithm;

/**
 * handles rerouting in a background thread.
 * 
 * @author Hunter Baker
 */
public class ReroutingWorker extends SwingWorker<Map<String, StreetSegment>, Void>
{
  private Point2D.Double currentPosition;
  private RouteRelocator relocator;

  private LabelSettingAlgorithm alg;
  private Map<Integer, Label> allPaths;
  private StreetSegment destination;

  /**
   * Constructor.
   * 
   * @param currentPosition
   *          the current position to use
   * @param relocator
   *          the relocator to use
   * @param alg
   *          to algorithm to use
   * @param allPaths
   *          all paths from dest outward
   * @param destination
   *          our destination
   */
  public ReroutingWorker(final Point2D.Double currentPosition, final RouteRelocator relocator,
      final LabelSettingAlgorithm alg, final Map<Integer, Label> allPaths,
      final StreetSegment destination)
  {
    this.currentPosition = currentPosition;
    this.relocator = relocator;
    this.alg = alg;
    this.allPaths = allPaths;
    this.destination = destination;
  }

  @Override
  protected Map<String, StreetSegment> doInBackground()
  {
    StreetSegment closestSegment = relocator.findClosestSegment(currentPosition);

    if (closestSegment != null)
    {
      int currentIntersection = closestSegment.getTail();
      int destIntersection = destination.getHead();

      // our new path is from the current intersection we're on and the destination
      // intersection we want to reach
      return alg.extractPath(currentIntersection, destIntersection, allPaths);
    }
    return new HashMap<>(); // if we couldn't find a path
  }

  @Override
  protected void done()
  {
    try
    {
      Map<String, StreetSegment> newPath = get();

      if (!newPath.isEmpty())
      {

        relocator.setPath(newPath); // update path
      }
    }
    catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
    }
    finally
    {
      relocator.setRouting(false); // reset our flag
    }
  }
}
