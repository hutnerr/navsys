package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * A ViewTransformation can be used to create AffineTransform objects
 * that transform shapes between view coordinates and other coordinates
 * (e.g. Euclidean coordinates, display coordinates).
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public interface ViewTransformation
{
  /**
   * Get the last reflection that was used.
   * 
   * @return The reflection
   */
  public abstract AffineTransform getLastReflection();
  
  /**
   * Get the last complete transformation that was used.
   * 
   * @return The result of the previous call to getTransform(Rectangle2D, Rectangle2D)
   */
  public abstract AffineTransform getLastTransform();

  /**
   * Get the complete AffineTransform to use.
   * 
   * @param displayBounds The bounds of the display (in display coordinates)
   * @param contentBounds The bounds of the view (i.e., content)
   * @return The Transform to use
   */
  public abstract AffineTransform getTransform(final Rectangle2D displayBounds,
      final Rectangle2D contentBounds);
}
