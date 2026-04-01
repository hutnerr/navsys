package feature;

import geography.*;

import gui.*;

import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;


/**
 * A class for reading Street information.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;

  /**
   * Explicit Value COnstructor.
   * 
   * @param is The InputStream to read from
   * @param geographicShapes The "Model" that contains the shape information
   */
  public StreetsReader(final InputStream is, 
      final CartographyDocument<GeographicShape> geographicShapes)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.geographicShapes = geographicShapes;
  }



  /**
   * Read a .str file containing Street information.
   * 
   * @param streets An OUTBOUND collection off all of the Street objects
   * @return A CartographyDocument containing all of the StreetSegment objects
   * @throws IOException If something goes wrong
   */
  public CartographyDocument<StreetSegment> read(final Map<String, Street> streets) 
      throws IOException
  {
    if (streets == null) 
      throw new IllegalArgumentException("The outbound parameter must be initialized!");
    HashMap<String, StreetSegment> segments = new HashMap<String, StreetSegment>();
    

    String line, token;
    while ((line = in.readLine()) != null)
    {
      // NOTE: There are no empty fields.  Some fields may contain
      //       only spaces.  Some may contain trailing spaces.

      StringTokenizer st = new StringTokenizer(line, "\t");

      int from = Integer.parseInt(st.nextToken().trim()); // Tail Node
      int to = Integer.parseInt(st.nextToken().trim()); // Head Node
      token = st.nextToken().trim();
      if (token.equals("NaN")) token = "0.0";
      double length;
      try
      {
        length = Double.parseDouble(token); // Length
      }
      catch (NumberFormatException nfe)
      {
        length = 0.0;
      }

      String code       = st.nextToken().trim(); // TIGER Type Code (e.g., A11)
      String id         = st.nextToken().trim(); // Arc ID
      String prefix     = st.nextToken().trim(); // Prefix
      String name       = st.nextToken().trim(); // Name
      String category   = st.nextToken().trim(); // Category (e.g., Rd, St, Hwy)
      String suffix     = st.nextToken().trim(); // Suffix


      String key    = Street.createCanonicalName(prefix, name, category, suffix);
      Street street = streets.get(key); 
      if (street == null)
      {
        street = new Street(prefix, name, category, suffix, code);
        streets.put(key, street);
      }

      // Starting Address
      token = st.nextToken();
      int temp1;
      try
      {
        temp1 = Integer.parseInt(token.trim());
      }
      catch (NumberFormatException nfe)
      {
        temp1 = 0;
      }

      // Ending Address
      token = st.nextToken();
      int temp2;
      try
      {
        temp2 = Integer.parseInt(token.trim());
      }
      catch (NumberFormatException nfe)
      {
        temp2 = 0;
      }

      int lowAddress  = Math.min(temp1, temp2);
      int highAddress = Math.max(temp1, temp2);


      if (id != null)
      {
        id = id.trim();             
        if (!id.equals(""))
        {
          GeographicShape geographicShape = null;
          if (geographicShapes != null) geographicShape = geographicShapes.getElement(id);
          StreetSegment segment = new StreetSegment(id, code, geographicShape, 
              lowAddress, highAddress, from, to, length);
          street.addSegment(segment);
          segments.put(id, segment);
        }
      }
    }
    Rectangle2D.Double bounds = null;
    if (geographicShapes != null) bounds = geographicShapes.getBounds();
    return new CartographyDocument<StreetSegment>(segments, bounds);
  }
}
