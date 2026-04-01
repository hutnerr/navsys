package feature;

import geography.GeographicShape;

/**
 * A geographic feature on a map.
 *
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public interface Feature
{
  /**
   * Get the ID for this Feature.
   *
   * @return The ID
   */
  public abstract String getID();

  /**
   * Get the GeographicShape of this Feature.
   * 
   * @return The Shape
   */
  public abstract GeographicShape getGeographicShape();
}
