package geography;
import java.awt.Shape;


/**
 * A geographic shape.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public interface GeographicShape // extends Iterable<double[]>
{
  /**
   * Get the ID for this GeographicShape.
   *
   * @return The ID
   */
  public abstract String getID();
  
  /**
   * Get the Shape of this GeographicShape.
   * 
   * @return The Shape
   */
  public abstract Shape getShape();

}
