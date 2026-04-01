package feature;

import geography.*;

/**
 * An encapsulation of a StreetSegment (e.g., a portion of a street
 * between two intersections).
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1
 */
public class StreetSegment extends AbstractFeature
{
  private double length;
  private int tail, head;
  private int highAddress, lowAddress;
  private GeographicShape geographicShape;
  private String code;

  /**
   * Explicit Value Constructor.
   *
   * @param id The ID of this segment
   * @param code The TIGER code
   * @param geographicShape The GeographicShape
   * @param lowAddress  The smallest (potential) address on this segment
   * @param highAddress The largest (potential) address on this segment
   * @param tail The ID of the tail Intersection
   * @param head The ID of the head Intersection
   * @param length The length (in KM)
   */
  public StreetSegment(final String id, final String code, final GeographicShape geographicShape,
      final int lowAddress, final int highAddress,
      final int tail, final int head, final double length)
  {
    super(id);
    this.code = code;
    this.geographicShape = geographicShape;
    this.lowAddress  = lowAddress;
    this.highAddress = highAddress;
    this.tail = tail;
    this.head = head;
    this.length = length;
  }

  /**
   * Get the TIGER code.
   * 
   * @return The code
   */
  public String getCode()
  {
    return code;
  }

  /**
   * Get the tail Intersection.
   * 
   * @return The tail
   */
  public int getTail()
  {
    return tail;
  }

  /**
   * Get the head Intersection.
   * 
   * @return The head
   */
  public int getHead()
  {
    return head;
  }

  /**
   * Get the length of the segment (in KM).
   * 
   * @return The length
   */
  public double getLength()
  {
    return length;
  }

  /**
   * Get the smallest (potential) address on this segment.
   * 
   * @return The low address
   */
  public int getLowAddress()
  {
    return lowAddress;       
  }

  /**
   * Get the largest (potential) address on this segment.
   * 
   * @return The high address
   */
  public int getHighAddress()
  {
    return highAddress;       
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

}
