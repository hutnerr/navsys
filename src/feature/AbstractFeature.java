package feature;

import java.awt.geom.AffineTransform;

import geography.GeographicShape;

/**
 * An abstract implementation of the Feature interface.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public abstract class AbstractFeature implements Feature
{
  private static final AffineTransform IDENTITY = new AffineTransform();

  private String id;

  /**
   * Explicit Value Constructor.
   * 
   * @param id
   *          The ID
   */
  protected AbstractFeature(final String id)
  {
    this.id = id;
  }

  /**
   * Get the ID for this feature.
   *
   * @return The ID
   */
  @Override
  public String getID()
  {
    return id;
  }

  /**
   * Get the GeographicShape of this Feature.
   *
   * @return The Shape
   */
  @Override
  public abstract GeographicShape getGeographicShape();

}
