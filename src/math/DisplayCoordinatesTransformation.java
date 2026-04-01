package math;

import java.awt.geom.*;

/**
 * A ViewTransformation that can be used to create AffineTransform objects
 * that transform shapes between view coordinates and display coordinates.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class DisplayCoordinatesTransformation implements ViewTransformation
{
  AffineTransform aroundX, at;
  
  /**
   * Default Constructor.
   */
  public DisplayCoordinatesTransformation()
  {
    aroundX = new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, 0.0);
  }
  
  /**
   * Get the reflection that was used.
   * 
   * @return The reflection
   */
  @Override
  public AffineTransform getLastReflection()
  {
    return aroundX;
  }
  
  /**
   * Get the last complete transformation that was used.
   * 
   * @return The result of the previous call to getTransform(Rectangle2D, Rectangle2D)
   */
  public AffineTransform getLastTransform()
  {
    return at;
  }

  /**
   * Get the complete AffineTransform.
   * 
   * @param displayBounds The bounds of the display (in display coordinates)
   * @param contentBounds The bounds of the view (i.e., content)
   * @return
   */
  @Override
  public AffineTransform getTransform(final Rectangle2D displayBounds,
      final Rectangle2D contentBounds)
  {
    
    double contentHeight = contentBounds.getHeight();
    double contentWidth = contentBounds.getWidth();
    double contentX = contentBounds.getX();
    double contentY = contentBounds.getY();
    AffineTransform translation = 
        AffineTransform.getTranslateInstance(-contentX, -(contentHeight+contentY));
    
    double displayWidth = displayBounds.getWidth();
    double displayHeight = displayBounds.getHeight();
    double scale = Math.min(displayWidth/contentWidth, displayHeight/contentHeight);
    AffineTransform scaling = AffineTransform.getScaleInstance(scale, scale);
    
    at = translation;
    at.preConcatenate(aroundX);
    at.preConcatenate(scaling);
    
    return at;
  }

}
