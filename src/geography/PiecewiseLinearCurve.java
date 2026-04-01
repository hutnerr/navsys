package geography;

import java.awt.Shape;
import java.awt.geom.*;

/**
 * A curve that is piecewise linear (i.e., constructed of a series
 * of line segments).
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class PiecewiseLinearCurve extends AbstractGeographicShape
{
  protected boolean initialized;
  protected Path2D.Double shape;

  /**
   * Explicit Value Constructor.
   * 
   * @param id  The ID of the curve
   */
  public PiecewiseLinearCurve(final String id)
  {
    super(id);
    shape = new Path2D.Double();
    initialized = false;
  }

  /**
   * Explicit Value Constructor.
   * 
   * @param id  The ID of the curve
   * @param shape The initial Shape of the curve
   */
  public PiecewiseLinearCurve(final String id, final Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
    initialized = true;
  }
  
  /**
   * Add a point to this Feature.
   * 
   * @param point  The point to add
   */
  public void add(final double[] point)
  {
    if (!initialized) 
    {
      shape.moveTo(point[0], point[1]);
      initialized = true;
    }
    else shape.lineTo(point[0], point[1]);
  }
  
  /**
   * Append a Shape to this PiecewiseLinearCurve.
   * 
   * @param addition  The Shape to append
   * @param connect true to connect; false not to
   */
  public void append(final Shape addition, final boolean connect)
  {
    shape.append(addition, connect);
  }
  
  /**
   * Get the Shape for this feature.
   *
   * @return    The Shape
   */
  @Override
  public Shape getShape()
  {
    return shape;
  }

}
