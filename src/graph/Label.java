package graph;

import feature.StreetSegment;

/**
 * Label objects are used in label setting and correcting algorithms. They maintain information
 * about the shortest path to a particular Intersection that has been found thus far, including the
 * length of the path and the incoming StreetSegment (i.e. the StreetSegment from the predecessor
 * Intersection) on the path.
 * 
 * @author Hunter Baker
 */
public class Label
{
  private boolean permanent;
  private double value;
  private int id;
  private StreetSegment predecessor;

  /**
   * Default Constructor.
   */
  public Label()
  {
    this(0);
  }

  /**
   * Constructor.
   * 
   * @param id
   *          The id of the label
   */
  public Label(final int id)
  {
    this.permanent = false;
    this.value = Double.MAX_VALUE;
    this.id = id;
    this.predecessor = null;
  }

  /**
   * Try and update the value. Only update if it is less than current.
   * 
   * @param possibleValue
   * @param possiblePredecessor
   */
  public void adjustValue(final double possibleValue, final StreetSegment possiblePredecessor)
  {
    if (possibleValue < value)
    {
      this.value = possibleValue;
      this.predecessor = possiblePredecessor;
    }
  }

  /**
   * Getter for the ID.
   * 
   * @return The ID
   */
  public int getID()
  {
    return this.id;
  }

  /**
   * Getter for the Predecessor.
   * 
   * @return The Predecessor
   */
  public StreetSegment getPredecessor()
  {
    return this.predecessor;
  }

  /**
   * Getter for the value.
   * 
   * @return The value
   */
  public double getValue()
  {
    return this.value;
  }

  /**
   * Getter for if the label is permanent or not. Used in label setting algorithms.
   * 
   * @return True if permanent, false otherwise
   */
  public boolean isPermanent()
  {
    return this.permanent;
  }

  /**
   * Converts a label into a permanent one. Used in label setting algorithms.
   */
  public void makePermanent()
  {
    this.permanent = true;
  }

  /**
   * Sets the value.
   * 
   * @param value
   *          The new value
   */
  public void setValue(final double value)
  {
    this.value = value;
  }

}
