package feature;

import geography.*;

import java.util.*;


/**
 * An encapsulation of a Street.
 *
 * In essence, a Street is a collection of StreetSegment objects.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1
 */
public class Street extends AbstractFeature
{
  private static final String SPACE = " ";
  
  private PiecewiseLinearCurve geographicShape;
  //private String category, code, name, prefix, suffix;
  private List<StreetSegment> segments;



  /**
   * Explicit Value Constructor.
   *
   * @param prefix    The prefix (e.g., N, S)
   * @param name      The name   (e.g., Elm)
   * @param category  The category of road (e.g., Ave, Rd, St)
   * @param suffix    The suffix (e.g., NW)
   * @param code      The TIGER road code
   */
  public Street(final String prefix, final String name, 
      final String category, final String suffix,
      final String code)
  {
    super(Street.createCanonicalName(prefix, name, category, suffix));

//    this.prefix   = prefix;
//    this.name     = name;
//    this.category = category;
//    this.suffix   = suffix;
//    this.code     = code;

    segments = new ArrayList<StreetSegment>();
    geographicShape = new PiecewiseLinearCurve(getID());
  }



  /**
   * Add a StreetSegment to this Street.
   *
   * @param segment    The StreetSegment
   */
  public void addSegment(final StreetSegment segment)
  {
    segments.add(segment);
    if (segment.getGeographicShape() != null)
    {
      geographicShape.append(segment.getGeographicShape().getShape(), false);
    }
  }



  /**
   * Put the name of a Street in canonical form.
   *
   * @param prefix    The prefix (e.g., N, S)
   * @param name      The name   (e.g., Elm)
   * @param category  The category of road (e.g., Ave, Rd, St)
   * @param suffix    The suffix (e.g., NW)
   * @return          The canonical form
   */
  public static String createCanonicalName(final String prefix, final String name, 
      final String category, final String suffix)
  {
    String result = null;       

    if (prefix != null && !prefix.isEmpty()) result = prefix.trim() + SPACE + name.trim();
    else result = name;

    if (category != null && !category.isEmpty()) result += SPACE + category.trim();

    if (suffix != null && !suffix.isEmpty()) result += SPACE + suffix.trim();

    return result;       
  }

  /**
   * Get the StreetSegments (in this Street)
   * that contains a particular address.
   *
   * @param address    The address
   * @return           The StreetSegments that contain the given adddress
   */
  public List<StreetSegment> getSegments(final int address)
  {
    List<StreetSegment> result = new ArrayList<StreetSegment>();

    for (StreetSegment segment:segments)
    {
      int lowAddress  = segment.getLowAddress();
      int highAddress = segment.getHighAddress();

      // Check if the address  is in range
      if ((address >= lowAddress) && (address <= highAddress)) result.add(segment);
    }

    return result;
  }

  /**
   * Get the GeographicShape of this Feature.
   *
   * @return    The GeographicShape
   */
  @Override
  public GeographicShape getGeographicShape()
  {
    return geographicShape;
  }

  /**
   * Get the StreetSegment objects in this Street.
   * 
   * @return The segments
   */
  public Iterator<StreetSegment> getSegments()
  {
    return segments.iterator();
  }

  /**
   * Get the number of StreetSegment objects in this Street.
   * 
   * @return The size
   */
  public int getSize()
  {
    return segments.size();
  }

}
