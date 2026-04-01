package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;

/**
 * This is a cartography panel that can handle dynamic position/location updates.
 * 
 * @param <T>
 * 
 * @author Hunter Baker
 */
public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{
  private static final long serialVersionUID = -8327258527421429983L;
  private GPGGASentence gpgga;
  private MapProjection proj;

  /**
   * Constructor.
   * 
   * @param model
   *          The model to use
   * @param cartographer
   *          The cartographer to use
   * @param proj
   *          The projection to use
   */
  public DynamicCartographyPanel(final CartographyDocument<T> model,
      final Cartographer<T> cartographer, final MapProjection proj)
  {
    super(model, cartographer);
    this.gpgga = null;
    this.proj = proj;
  }

  // must parse the NMEA sentence it is passed and store it is an attribute
  @Override
  public void handleGPSData(final String data)
  {
    this.gpgga = GPGGASentence.parseGPGGA(data);
    repaint();
  }

  // this must:
  // 1. add a Rectangle2D.Double object to the first element of the zoomStack that is centered on
  // the current location/position and is 2km wide and 2km high
  // 2. Call the parent paint() method to invoke the street network
  // 3. Project and transform the current position/location
  // 4. Render a filled circle (in red) that is centered on the current location is is 8 pixels wide
  // and 8 pixels high.

  @Override
  public void paint(final Graphics g)
  {
    // 1. add a Rectangle2D.Double object to the first element of the zoomStack that is centered on
    // the current location and is 2km wide and 2km high
    Graphics2D g2 = (Graphics2D) g;

    if (gpgga == null)
    {
      super.paint(g);
      return;
    }

    // 3. Project and transform the current position/location
    double lat = gpgga.getLatitude();
    double lon = gpgga.getLongitude();
    double ll[] = {lon, lat};

    double km[] = proj.forward(ll);
    double x = km[0];
    double y = km[1];

    // subtracting by 1.0 to center since the rectangle origin is top left
    Rectangle2D.Double rect = new Rectangle2D.Double(x - 1.0, y - 1.0, 2.0, 2.0);
    zoomStack.addFirst(rect);

    // 2. Call the parent paint() method to invoke the street network
    super.paint(g);

    // 4. Render a filled circle (in red) that is centered on the current location is in 8 pixels by
    // 8 pixels.
    Rectangle2D.Double viewBounds = zoomStack.getFirst();
    Rectangle screenBounds = g2.getClipBounds();
    if (screenBounds == null)
    {
      screenBounds = getBounds();
    }
    AffineTransform transform = displayTransform.getTransform(screenBounds, viewBounds);

    Point2D.Double worldPoint = new Point2D.Double(x, y);
    Point2D screenPoint = transform.transform(worldPoint, null);

    // Draw an 8x8 pixel red circle at the position
    double sx = screenPoint.getX() - 4.0; // Center - 4 pixels
    double sy = screenPoint.getY() - 4.0; // Center - 4 pixels
    Ellipse2D.Double circle = new Ellipse2D.Double(sx, sy, 8.0, 8.0);

    g2.setColor(Color.RED);
    g2.fill(circle);
    g2.draw(circle);
  }
}
