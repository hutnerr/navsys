package relocator;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Map;

import feature.StreetSegment;
import graph.Label;
import graph.LabelSettingAlgorithm;
import gui.CartographyDocument;
import math.Vector;

/**
 * Constructor.
 * 
 * @author Hunter Baker
 */
public class RouteRelocator
{
  private static double THRESHOLD = 0.10;

  private Map<String, StreetSegment> path;
  private CartographyDocument<StreetSegment> document;
  private boolean isRerouting = false;

  // passed to our ReroutingWorker
  private LabelSettingAlgorithm alg;
  private Map<Integer, Label> allPaths;
  private StreetSegment destination;

  /**
   * Constructor.
   * 
   * @param alg
   *          The algorithm we used
   * @param path
   *          The path we're currently following
   * @param allPaths
   *          The path from the dest to all other nodes
   * @param document
   *          The document we want to update
   * @param destination
   *          The destination we want to reach
   */
  public RouteRelocator(final LabelSettingAlgorithm alg, final Map<String, StreetSegment> path,
      final Map<Integer, Label> allPaths, final CartographyDocument<StreetSegment> document,
      final StreetSegment destination)
  {
    this.alg = alg;
    this.allPaths = allPaths;
    this.document = document;
    this.destination = destination;
    setPath(path);
  }

  /**
   * Called be an observer when our position updates, tries to reroute if necessary.
   * 
   * @param newPosition
   *          the lon/lat position.
   * @return true if rerouted, false otherwise
   */
  public boolean updatePosition(final double[] newPosition)
  {
    // if we're already rerouting, then skip
    if (isRerouting)
    {
      return false;
    }

    double minDist = Double.MAX_VALUE;
    Point2D.Double currentPos = new Point2D.Double(newPosition[0], newPosition[1]);

    // find the closest point i am to any segment on the path
    // iterate over our segments, get its shape, then iterate over the shape
    // taking the min and comparing it to our min
    for (StreetSegment seg : path.values())
    {
      Path2D.Double spath = (Path2D.Double) seg.getGeographicShape().getShape();
      PathIterator it = spath.getPathIterator(null);

      double[] coords = new double[2];
      Point2D.Double lastPoint = null;

      while (!it.isDone())
      {
        int type = it.currentSegment(coords);
        Point2D.Double point = new Point2D.Double(coords[0], coords[1]);

        if (type == PathIterator.SEG_MOVETO)
        {
          lastPoint = point;
        }
        else if (type == PathIterator.SEG_LINETO && lastPoint != null)
        {
          double dist = pointToSegmentDistance(currentPos, lastPoint, point);
          minDist = Math.min(minDist, dist);
          lastPoint = point;
        }
        it.next();
      }
    }

    if (minDist > THRESHOLD)
    {
      // reroute in a background thread
      isRerouting = true;
      new ReroutingWorker(currentPos, this, alg, allPaths, destination).execute();
      return true;
    }
    return false; // didnt reroute
  }

  /**
   * Setter for if we're routing.
   * 
   * @param rerouting
   *          true if is rerouting, false otherwise
   */
  public void setRouting(final boolean rerouting)
  {
    this.isRerouting = rerouting;
  }

  /**
   * Setter for our path, also updates highlighted.
   * 
   * @param path
   *          The path to update.
   */
  public void setPath(final Map<String, StreetSegment> path)
  {
    this.path = path;
    document.setHighlighted(path);
  }

  /**
   * Performs a point to segment distance calculation.
   * 
   * @param p
   * @param a
   * @param b
   * @return the distance
   */
  public double pointToSegmentDistance(final Point2D.Double p, final Point2D.Double a,
      final Point2D.Double b)
  {
    // conmvert so we can use our nice and handy Vector class
    double[] pointP = {p.x, p.y};
    double[] pointA = {a.x, a.y};
    double[] pointB = {b.x, b.y};

    double[] ab = Vector.minus(pointB, pointA); // vec from A to B

    // if segment is a point
    if (ab[0] == 0 && ab[1] == 0)
    {
      return p.distance(a);
    }

    double[] ap = Vector.minus(pointP, pointA); // vec from A to P
    double t = Vector.dot(ap, ab) / Vector.dot(ab, ab); // AP onto AB
    t = Math.max(0, Math.min(1, t)); // keep the point on the segment

    double[] closest = Vector.plus(pointA, Vector.times(t, ab));
    Point2D.Double closestPoint = new Point2D.Double(closest[0], closest[1]);
    return p.distance(closestPoint);
  }

  /**
   * finds the closest street segment to a given position.
   * 
   * @param position
   *          The current position
   * @return The closest segment, or null if none found
   */
  public StreetSegment findClosestSegment(final Point2D.Double position)
  {
    StreetSegment closestSeg = null;
    double minDist = Double.MAX_VALUE;

    for (StreetSegment seg : document)
    {
      Path2D.Double spath = (Path2D.Double) seg.getGeographicShape().getShape();
      PathIterator it = spath.getPathIterator(null);

      double[] coords = new double[2];
      Point2D.Double lastPoint = null;

      while (!it.isDone())
      {
        int type = it.currentSegment(coords);
        Point2D.Double point = new Point2D.Double(coords[0], coords[1]);

        if (type == PathIterator.SEG_MOVETO)
        {
          lastPoint = point;
        }
        else if (type == PathIterator.SEG_LINETO && lastPoint != null)
        {
          double dist = pointToSegmentDistance(position, lastPoint, point);
          if (dist < minDist)
          {
            minDist = dist;
            closestSeg = seg;
          }
          lastPoint = point;
        }
        it.next();
      }
    }
    return closestSeg;
  }
}
