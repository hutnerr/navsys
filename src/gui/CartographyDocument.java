package gui;

import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * The model (in the sense of the model-view-controller pattern)
 * for a CartographyPanel.
 * 
 * @param <T> The type of element to be rendered
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class CartographyDocument<T> implements Iterable<T>
{
  private Map<String, T> highlighted, elements;
  private Rectangle2D.Double bounds;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param elements A map from IDs to the elements to be rendered
   * @param bounds The spatial bounds
   */
  public CartographyDocument(final Map<String, T> elements, final Rectangle2D.Double bounds)
  {
    this.elements = elements;
    this.bounds = bounds;
    highlighted = new HashMap<String, T>();
  }
  
  /**
   * Get a specific element.
   * 
   * @param id The ID of the element
   * @return The element
   */
  public T getElement(final String id)
  {
    return elements.get(id);
  }

  /**
   * Return an Iterator of the elements to be highlighted.
   * 
   * @return The Iterator
   */
  public Iterator<T> highlighted()
  {
    return highlighted.values().iterator();
  }

  /**
   * Return an Iterator of the elements to be rendered.
   * 
   * @return The Iterator
   */
  public Iterator<T> iterator()
  {
    return elements.values().iterator();
  }
  
  /**
   * Get the spatial bounds.
   * 
   * @return The bounds
   */
  public Rectangle2D.Double getBounds()
  {
    return bounds;
  }
  
  /**
   * Set the elements to be highlighted.
   * 
   * @param highlighted The elements to be highlighted
   */
  public void setHighlighted(final Map<String, T> highlighted)
  {
    this.highlighted = highlighted;
  }
  
}
