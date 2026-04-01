package gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.Theme;
import geography.ThemeLibrary;

/**
 * A Cartographer that renders Street features.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class StreetSegmentCartographer implements Cartographer<StreetSegment>
{
  private ThemeLibrary themes;

  /**
   * Default Constructor.
   */
  public StreetSegmentCartographer()
  {
    this.themes = new StreetThemeLibrary();
  }

  /**
   * Paint the highlighted shapes.
   * 
   * @param model
   *          The model containing the elements
   * @param g2
   *          The rendering engine to use
   * @param at
   *          The AffineTransform to use
   */
  @Override
  public void paintHighlights(final CartographyDocument<StreetSegment> model, final Graphics2D g2,
      final AffineTransform at)
  {
    Iterator<StreetSegment> highlighted = model.highlighted();
    while (highlighted.hasNext())
    {
      StreetSegment s = highlighted.next();
      Theme theme = themes.getHighlightTheme();
      g2.setColor(theme.getColor());
      g2.setStroke(theme.getStroke());
      Shape shape = at.createTransformedShape(s.getGeographicShape().getShape());
      // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.draw(shape);

    }
  }

  /**
   * Paint the shapes.
   * 
   * @param model
   *          The model containing the elements
   * @param g2
   *          The rendering engine to use
   * @param at
   *          The AffineTransform to use
   */
  @Override
  public void paintShapes(final CartographyDocument<StreetSegment> model, final Graphics2D g2,
      final AffineTransform at)
  {
    Iterator<StreetSegment> streets = model.iterator();
    while (streets.hasNext())
    {
      StreetSegment segment = streets.next();
      String code = segment.getCode().substring(0, 2);
      Theme theme = themes.getTheme(code);
      g2.setColor(theme.getColor());
      g2.setStroke(theme.getStroke());
      Shape shape = at.createTransformedShape(segment.getGeographicShape().getShape());
      // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.draw(shape);
    }
  }
}
