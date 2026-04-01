package math;

/**
 * A utility class that can be used to perform operations on 2-D vectors.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1
 */
public class Vector
{
  /**
   * Default Cpnstructor.
   */
  private Vector()
  {
  }

  /**
   * Calculate the dot (inner) product of two vectors.
   * 
   * @param v One vector
   * @param w The other vector
   * @return The dot product
   */
  public static double dot(final double[] v, final double[] w)
  {
    return v[0]*w[0] + v[1]*w[1];
  }
  
  /**
   * Calculate v - w.
   * 
   * @param v One vector
   * @param w The other vector
   * @return v-w
   */
  public static double[] minus(final double[] v, final double[] w)
  {
    double[] result = new double[2];
    result[0] = v[0] - w[0];
    result[1] = v[1] - w[1];

    return result;
  }
  
  /**
   * Calculate the norm of a vector.
   * 
   * @param v The vector
   * @return The norm
   */
  public static double norm(final double[] v)
  {
    return Math.sqrt(v[0]*v[0] + v[1]*v[1]);
  }
  
  /**
   * Normalize a vector.
   * 
   * @param v The vector to normalize
   * @return The normalized vector (i.e., 1/norm(v) * v)
   */
  public static double[] normalize(final double[] v)
  {
    double denom = norm(v);
    return times(1.0/denom, v);
  }
  
  /**
   * Find a vector that is perpendicular to a vector.
   * 
   * @param v The vector
   * @return The vector (-v[1], v[0])
   */
  public static double[] perp(final double[] v)
  {
    return new double[] {-v[1], v[0]};
  }

  /**
   * Calculate v + w.
   * 
   * @param v One vector
   * @param w Another vector
   * @return v + w
   */
  public static double[] plus(final double[] v, final double[] w)
  {
    double[] result = new double[2];
    result[0] = v[0] + w[0];
    result[1] = v[1] + w[1];

    return result;
  }
  
  /**
   * Multiple a scalar and a vector.
   * 
   * @param s The scalar
   * @param v The vector
   * @return The result of the multiplication
   */
  public static double[] times(final double s, final double[] v)
  {
    return new double[] {s*v[0], s*v[1]};
  }
  
  /**
   * Multiple a vector and a scalar.
   * 
   * @param v The vector
   * @param s The scalar
   * @return The result of the multiplication
   */
  public static double[] times(final double[] v, final double s)
  {
    return times(s, v);
  }
  
}
