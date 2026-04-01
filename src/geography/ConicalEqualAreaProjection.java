package geography;

/**
 *  Conical Equal Area (Albers) Projection.
 * 
 *  @author  Prof. David Bernstein
 *  @version 1.0
 */
public class ConicalEqualAreaProjection extends AbstractMapProjection
{
  // Array used to construct points
  protected double[] retval = new double[2];

  private double lambda0;   // Reference meridian (or longitude) in radians
  private double phi0;      // Reference parallel (or latitude) in radians
  private double phi1;      // First standard parallel in radians
  private double phi2;      // Second standard parallel in radians

  private double c, cosphi1, n, rho0, sinphi1, sinphi2;

  /**
   * Explicit Value Constructor.
   *
   * @param referenceMeridian   The reference (origin) longitude (in degrees)
   * @param referenceParallel   The reference (origin) longitude (in degrees)
   * @param standardParallel1  The first standard parallel (in degrees)
   * @param standardParallel2  The second standard parallel (in degrees)
   */
  public ConicalEqualAreaProjection(final double referenceMeridian, 
      final double referenceParallel,
      final double standardParallel1, 
      final double standardParallel2)
  {
    lambda0 = referenceMeridian  * RADIANS_PER_DEGREE;
    phi0    = referenceParallel  * RADIANS_PER_DEGREE;
    phi1    = standardParallel1 * RADIANS_PER_DEGREE;
    phi2    = standardParallel2 * RADIANS_PER_DEGREE;

    cosphi1 = Math.cos(phi1);
    sinphi1 = Math.sin(phi1);
    sinphi2 = Math.sin(phi2);

    n = 0.5*(sinphi1+sinphi2);
    c = cosphi1*cosphi1 + 2.0*n*sinphi1;
    rho0 = Math.sqrt(c - 2.0*n*Math.sin(phi0)) / n;
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
    double sinphi  = Math.sin(phi);
    double theta = n * (lambda - lambda0);
    double rho = Math.sqrt(c - 2.0*n*sinphi) / n;

    retval[0] = R * rho * Math.sin(theta);        
    retval[1] = R * (rho0 - rho * Math.cos(theta));

    return retval;
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
    double a = Math.sqrt(Math.pow(ew/R, 2) + Math.pow((rho0 - ns/R), 2));
    double b = Math.atan((ew/R)/(rho0 - (ns/R)));

    retval[0] = lambda0 + b/n;
    retval[1] = Math.asin((c-a*a*n*n)/(2.0*n));

    return retval;
  }
}
