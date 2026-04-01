package geography;
/**
 *  An encapsulation of the Sinusoidal Projection.
 *
 *  @author  Prof. David Bernstein, James Madison University
 *  @version 1.0
 */
public class SinusoidalProjection extends AbstractMapProjection
{
  // Array used to construct points
  private double[] retval;

  /**
   * Default Constructor.
   */
  public SinusoidalProjection()
  {
    retval = new double[2];
  }

  /**
   * The forward transformation (i.e., from Longitude/Latitude in
   * __radians__ to kilometers above the equator and to the west of the
   * reference meridian).
   *
   * @param lambda The longitude in __radians__
   * @param phi    The latitude in __radians__
   * @return   KMs west of reference and north of equator (in that order)
   */
  public double[] forward(final double lambda, final double phi)
  {
    double cosphi  = Math.cos(phi);

    retval[0] = R * lambda * cosphi;
    retval[1] = R * phi;

    return (retval);
  }


  /**
   * The inverse transformation from kilometers (above the 
   * equator and to the west of the reference meridian)
   * to Longitude/Latitude in __radians__.
   *
   * @param ew The distance east/west in kilometers
   * @param ns The distance north/south in kilometers
   * @return   The longitude and latitude in __radians__ (in that order)
   */
  public double[] inverse(final double ew, final double ns)
  {
    double phi    = ns / R;
    double cosphi = Math.cos(phi);
    double lambda = ew / (R * cosphi);

    retval[0] = lambda;
    retval[1] = phi;

    return retval;
  }


}
