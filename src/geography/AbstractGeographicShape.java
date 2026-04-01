package geography;
import java.awt.Shape;

/**
 * An abstract implementation of the GeographicShape interface.
 *
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public abstract class AbstractGeographicShape implements GeographicShape
{
  private String id;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param id  The ID
   */
  protected AbstractGeographicShape(final String id)
  {
    this.id = id;
  }
  
  
  /**
   * Get the ID for this feature.
   *
   * @return    The ID
   */
  @Override
  public String getID()
  {
    return id;
  }
  
  /**
   * Get the Shape of this feature.
   *
   * @return    The Shape
   */
  @Override
  public abstract Shape getShape();
}
