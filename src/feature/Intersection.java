package feature;

import java.util.*;

/**
 * An encapsulation of an Intersection.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class Intersection
{
  private List<StreetSegment> inbound, outbound;
  
  /**
   * Default Constructor.
   */
  public Intersection()
  {
    inbound = new ArrayList<StreetSegment>();
    outbound = new ArrayList<StreetSegment>();
  }
  
  /**
   * Add an inbound StreetSegment to this Intersection.
   * 
   * @param segment The StreetSegment
   */
  public void addInbound(final StreetSegment segment)
  {
    inbound.add(segment);
  }
  
  /**
   * Add an outbound StreetSegment to this Intersection.
   * 
   * @param segment The StreetSegment
   */
  public void addOutbound(final StreetSegment segment)
  {
    outbound.add(segment);
  }

  /**
   * Get the inbound segments (i.e., the StreetSegment objects that
   * have this Intersection as a head node).
   *  
   * @return The StreetSegment objects
   */
  public List<StreetSegment> getInbound()
  {
    return inbound;
  }

  /**
   * Get the outbound segments (i.e., the StreetSegment objects that
   * have this Intersection as a tail node).
   *  
   * @return The StreetSegment objects
   */
  public List<StreetSegment> getOutbound()
  {
    return outbound;
  }

}
