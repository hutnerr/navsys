package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * The requirements of a Cartographer.
 * 
 * @param <T> The type of the elements
 */
public interface Cartographer<T>
{
  /**
   * Paint the highlighted shapes.
   * 
   * @param model The model containing the elements
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   */
  public abstract void paintHighlights(final CartographyDocument<T> model, 
      final Graphics2D g2, final AffineTransform at);

  /**
   * Paint the shapes.
   * 
   * @param model The model containing the elements
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   */
  public abstract void paintShapes(final CartographyDocument<T> model, 
      final Graphics2D g2, final AffineTransform at);
}
