package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import geography.GeographicShape;

/**
 * A Cartographer that renders GeographicShape objects.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class GeographicShapeCartographer implements Cartographer<GeographicShape>
{
  private Color color;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param color The Color to use for elements
   */
  public GeographicShapeCartographer(final Color color)
  {
    this.color = color;
  }
  
  /**
   * Paint the given elements.
   * 
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   * @param i The elements
   */
  private void paintElements(final Graphics2D g2, final AffineTransform at, 
      final Iterator<GeographicShape> i)
  {
    while (i.hasNext())
    {
      GeographicShape geographicShape;
      geographicShape = i.next();
      Shape s = at.createTransformedShape(geographicShape.getShape());
//    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
      g2.draw(s);
    }
  }

  /**
   * Paint the highlighted shapes.
   * 
   * @param model The model containing the elements
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   */
  @Override
  public void paintHighlights(final CartographyDocument<GeographicShape> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    g2.setColor(Color.GREEN);
    paintElements(g2, at, model.highlighted());
  }
  
  /**
   * Paint the shapes.
   * 
   * @param model The model containing the elements
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   */
  @Override
  public void paintShapes(final CartographyDocument<GeographicShape> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    g2.setColor(color);
    paintElements(g2, at, model.iterator());
  }

}
