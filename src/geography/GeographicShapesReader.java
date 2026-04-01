package geography;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import gui.*;


/**
 * Reads a collection of GeographicShape objects in lon/lat and
 * converts the points to KM.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1
 */
public class GeographicShapesReader
{
  private static final String END = "END";
  
  private BufferedReader in;
  private MapProjection proj;

  private double[] max, min;

  /**
   * Explicit Value Constructor.
   *
   * @param is   The InputStream to read from
   * @param projection The MapProjection to use
   */
  public GeographicShapesReader(final InputStream is, final MapProjection projection)
  {
    in        = new BufferedReader(new InputStreamReader(is));
    proj = projection;     
    max = new double[2];
    max[0] = Double.NEGATIVE_INFINITY;
    max[1] = Double.NEGATIVE_INFINITY;

    min = new double[2];
    min[0] = Double.POSITIVE_INFINITY;
    min[1] = Double.POSITIVE_INFINITY;

  }



  /**
   * Utility method for creating a point on the Earth's
   * surface in degrees lon/lat.
   *
   * @param longitude      The longitude (in degrees and parts)
   * @param latitude       The latitude  (in degrees and parts)
   * @return               The point
   */
  public static double[] createPoint(final double longitude, final double latitude)
  {
    double[]           value;

    value = new double[2];
    value[0] = longitude;
    value[1] = latitude;

    if      (value[0] >  180.0) value[0] =  180.0;
    else if (value[0] < -180.0) value[0] = -180.0;

    if      (value[1]  >   90.0) value[1]  =   90.0;
    else if (value[1]  <  -90.0) value[1]  =  -90.0;


    return value;
  }

  /**
   * Read the CartographyDocument.
   *
   * @return   The CartographyDocument
   */
  public CartographyDocument<GeographicShape> read()
  {
    Map<String, GeographicShape> map = new HashMap<String, GeographicShape>();
    try 
    {
      String line;
      while ((line = in.readLine()) != null)
      {
        StringTokenizer st = new StringTokenizer(line, "\t");

        st.nextToken();
        String type  = st.nextToken();

        // Read the ID if there is one
        String id = null;

        if (st.hasMoreTokens())  st.nextToken();
        if (st.hasMoreTokens())  id = st.nextToken().trim();

        if (type.equalsIgnoreCase("Comment")) 
        {
          readComment();
        }
        else if (type.equalsIgnoreCase("Polygon")) 
        {
          Path2D.Double shape = readPolygon();
          if ((shape != null) && (id != null)) map.put(id,  new Polygon(id, shape));
        }
        else if (type.equalsIgnoreCase("PiecewiseLinearCurve")) 
        {
          Path2D.Double shape  = readPiecewiseLinearCurve();
          if ((shape != null) && (id != null)) map.put(id,  new PiecewiseLinearCurve(id, shape));
        }
      }
    }
    catch (IOException ioe)
    {
      // Try again
    }
    return new CartographyDocument<GeographicShape>(map, 
        new Rectangle2D.Double(min[0], min[1], max[0]-min[0], max[1]-min[1]));
  }




  /**
   * Read a comment.
   */
  private void readComment() throws IOException
  {
    boolean keepReading = true;
    while (keepReading)
    {
      String line = in.readLine();
      if ((line == null) || line.startsWith(END)) keepReading = false;
    }
  }

  /**
   * Read a PiecewiseLinearCurve.
   *
   * @return  The PiecewiseLinearCurve
   */
  private Path2D.Double readPiecewiseLinearCurve() throws IOException
  {
    Path2D.Double curve = null;

    boolean keepReading = true;
    while (keepReading)
    {
      String line = in.readLine();
      if ((line == null) || line.startsWith(END))
      {
        keepReading = false;
      }
      else
      {
        StringTokenizer st = new StringTokenizer(line, "\t ");

        String token = st.nextToken();
        double longitude = Double.parseDouble(token);

        token = st.nextToken();
        double latitude = Double.parseDouble(token);

        // Create the Point
        double[] p = createPoint(longitude, latitude);
        double[] km = proj.forward(p);

        if (km[0] < min[0]) min[0] = km[0];
        if (km[1] < min[1]) min[1] = km[1];
        if (km[0] > max[0]) max[0] = km[0];
        if (km[1] > max[1]) max[1] = km[1];

        if (curve == null)
        {
          curve = new Path2D.Double();
          curve.moveTo(km[0], km[1]);
        }
        else
        {
          curve.lineTo(km[0], km[1]);
        }
      }
    }
    return curve;
  }

  /**
   * Read a Polygon.
   *
   * @return  The Polygon
   */
  private Path2D.Double readPolygon() throws IOException
  {
    Path2D.Double polygon = readPiecewiseLinearCurve();
    polygon.closePath(); // This shouldn't be necessary if the file is correct
    return polygon;
  }

}
