package geography;
import java.awt.Shape;
import java.awt.geom.Path2D;


/**
 * A Polygon.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class Polygon extends PiecewiseLinearCurve
{
  private boolean closed;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param id The ID of the Polygon
   */
  public Polygon(final String id)
  {
    super(id);
    closed = false;
  }

  /**
   * Explicit Value Constructor.
   * 
   * @param id The ID of the Polygon
   * @param shape The initial Shape of the Polygon
   */
  public Polygon(final String id, final Path2D.Double shape)
  {
    super(id, shape);
    closed = false;
  }

  /**
   * Get the Shape associated with this Feature.
   * 
   * @return The Shape
   */
  @Override
  public Shape getShape()
  {
    // Note: This may add an extra point if the Polygon was
    // closed manually. This is hard to check because the Polygon
    // may contain holes and the extra point doesn't hurt anything.
    if (!closed)
    {
      shape.closePath();
      closed = true;
    }
    return shape;
  }
}
