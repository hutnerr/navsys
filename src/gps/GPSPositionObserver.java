package gps;

import geography.GeographicShapesReader;
import geography.MapProjection;
import relocator.RouteRelocator;

/**
 * Observes and updates at new GPS positions.
 * 
 * @author Hunter Baker
 */
public class GPSPositionObserver implements GPSObserver
{
  private RouteRelocator relocator;
  private MapProjection projection;

  /**
   * Constructor.
   * 
   * @param relocator
   *          The relocator to pass our new position to
   * @param projection
   *          The projection to use
   */
  public GPSPositionObserver(final RouteRelocator relocator, final MapProjection projection)
  {
    this.relocator = relocator;
    this.projection = projection;
  }

  @Override
  public void handleGPSData(final String sentence)
  {
    if (sentence.startsWith("$GPGGA")) // we only want GPGGA sentences
    {
      GPGGASentence gpgga = GPGGASentence.parseGPGGA(sentence);

      if (gpgga == null)
      {
        System.out.println("Failed to parse GPGGA sentence: " + sentence);
        return;
      }

      // get the latitude and longitude
      double lat = gpgga.getLatitude();
      double lon = gpgga.getLongitude();

      // convert to projected coordinates, position is now in km
      double[] p = GeographicShapesReader.createPoint(lon, lat); // thank u bernstein
      double[] position = projection.forward(p);

      // send our new position to the relocator
      boolean rerouted = relocator.updatePosition(position);

      // returns true if we relocated
      if (rerouted)
      {
        System.out.println("Route recalculated");
      }
    }
  }
}
