package geography;

import java.awt.*;

/**
 * A Theme is a collection of colors, line styles, line widths, etc...
 * that is used to display descriptive data (a.k.a., thematic data) 
 * on a map
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1
 */
public class Theme
{
  private Color color;
  private Stroke stroke;

  /**
   * Explicit Value Constructor.
   *
   * @param color The Color of the line
   * @param stroke The STroke of the line
   */
  public Theme(final Color color, final Stroke stroke)
  {
    this.color = color;
    this.stroke = stroke;
  }

  /**
   * Get the Color.
   * 
   * @return The Color
   */
  public Color getColor()
  {
    return color;
  }


  /**
   * Get the Stroke.
   * 
   * @return The stroke
   */
  public Stroke getStroke()
  {
    return stroke;
  }
}
