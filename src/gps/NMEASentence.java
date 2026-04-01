package gps;

/**
 * NMEA Sentence.
 * 
 * @author Hunter Baker
 */
public class NMEASentence
{
  // stole from the slides fr
  // thank u bernstein
  /**
   * Adds a string to a checksum.
   * 
   * @param s
   *          The string to add
   * @param originalChecksum
   *          The original checksum we're adding to
   * @return The new checksum
   */
  public static int addToChecksum(final String s, final int originalChecksum)
  {
    int current;
    int temp = originalChecksum;

    for (int i = 0; i <= s.length() - 1; i++)
    {
      current = (int) (s.charAt(i)); // Get a char
      temp ^= current; // xor the char into the checksum
    }

    return temp;
  }

  /**
   * Converts a NMEA latitude input into a latitude.
   * 
   * @param latitudeString
   *          The lat string
   * @return The latitude
   */
  public static double convertLatitude(final String latitudeString)
  {
    // lat is DDmm.mm
    int degrees = Integer.parseInt(latitudeString.substring(0, 2));
    double minutes = Double.parseDouble(latitudeString.substring(2));
    return degrees + (minutes / 60.0);
  }

  /**
   * Converts a NMEA longitude input into a longitude.
   * 
   * @param longitudeString
   *          The long string
   * @return The longitude
   */
  public static double convertLongitude(final String longitudeString)
  {
    // long is DDDmm.mm
    int degrees = Integer.parseInt(longitudeString.substring(0, 3));
    double minutes = Double.parseDouble(longitudeString.substring(3));
    return degrees + (minutes / 60.0);
  }

}
