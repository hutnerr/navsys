package gps;

/**
 * GPGGASentence.
 * 
 * @author Hunter Baker
 */
public class GPGGASentence extends NMEASentence
{
  private String time;
  private double latitude;
  private double longitude;
  private int fixType;
  private int satellites;
  private double dilution;
  private double altitude;
  private String altitudeUnits;
  private double seaLevel;
  private String geoidUnits;

  /**
   * Constructor.
   * 
   * @param time
   * @param latitude
   * @param longitude
   * @param fixType
   * @param satellites
   * @param dilution
   * @param altitude
   * @param altitudeUnits
   * @param seaLevel
   * @param geoidUnits
   */
  public GPGGASentence(final String time, final double latitude, final double longitude,
      final int fixType, final int satellites, final double dilution, final double altitude,
      final String altitudeUnits, final double seaLevel, final String geoidUnits)
  {
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fixType = fixType;
    this.satellites = satellites;
    this.dilution = dilution;
    this.altitude = altitude;
    this.altitudeUnits = altitudeUnits;
    this.seaLevel = seaLevel;
    this.geoidUnits = geoidUnits;
  }

  /**
   * Getter for time.
   * 
   * @return the time
   */
  public String getTime()
  {
    return time;
  }

  /**
   * Getter for latitude.
   * 
   * @return the latitude
   */
  public double getLatitude()
  {
    return latitude;
  }

  /**
   * Getter for longitude.
   * 
   * @return the longitude
   */
  public double getLongitude()
  {
    return longitude;
  }

  /**
   * Getter for fix type.
   * 
   * @return the fix type
   */
  public int getFixType()
  {
    return fixType;
  }

  /**
   * Getter for the satellies.
   * 
   * @return the satellites
   */
  public int getSatellites()
  {
    return satellites;
  }

  /**
   * Getter for the dilution.
   * 
   * @return the dilution
   */
  public double getDilution()
  {
    return dilution;
  }

  /**
   * Getter for the altitude.
   * 
   * @return the altitude
   */
  public double getAltitude()
  {
    return altitude;
  }

  /**
   * Getter for the altitude units.
   * 
   * @return the altitude units
   */
  public String getAltitudeUnits()
  {
    return altitudeUnits;
  }

  /**
   * Getter for the sea level.
   * 
   * @return the sea level
   */
  public double getSeaLevel()
  {
    return seaLevel;
  }

  /**
   * Getter for the geoid units.
   * 
   * @return the geoid uniuts
   */
  public String getGeoidUnits()
  {
    return geoidUnits;
  }

  /**
   * prints out the sentence.
   */
  public void printSentence()
  {
    System.out.println("Time:\t\t" + time);
    System.out.println("Latitude:\t" + latitude);
    System.out.println("Longitude:\t" + longitude);
    System.out.println("Fix:\t\t" + fixType);
    System.out.println("Satellites:\t" + satellites);
    System.out.println("Dilution:\t" + dilution);
    System.out.println("Altitude:\t" + altitude);
    System.out.println("Altitude Units:\t" + altitudeUnits);
    System.out.println("Sea Level:\t" + seaLevel);
    System.out.println("Geoid Units:\t" + geoidUnits);
  }

  /**
   * Converts a GPGGASentence string into an object.
   * 
   * @param s
   *          the string
   * @return the sentence we made
   */
  public static GPGGASentence parseGPGGA(final String s)
  {
    String[] myGPGGASplit;

    // handle the bad
    if (s == null || s.isEmpty() || !s.startsWith("$GPGGA"))
    {
      return null;
    }

    myGPGGASplit = s.split(",");

    try
    {
      String time = myGPGGASplit[1];

      // if our lat or long is empty we r bad
      if (myGPGGASplit[2].isEmpty() || myGPGGASplit[4].isEmpty())
      {
        return null;
      }

      // use this very cool very swag convert function we made
      double latitude = convertLatitude(myGPGGASplit[2]);
      double longitude = convertLongitude(myGPGGASplit[4]);

      // inverse lat if our direction is South (DOWN)
      if (myGPGGASplit[3].equals("S"))
      {
        latitude = -latitude;
      }

      // inverse long if our direction is West (TO THE LEFT)
      if (myGPGGASplit[5].equals("W"))
      {
        longitude = -longitude;
      }

      // get the rest of it. this stuff isn't as important
      int fixType = myGPGGASplit[6].isEmpty() ? 0 : Integer.parseInt(myGPGGASplit[6]);
      int satellites = myGPGGASplit[7].isEmpty() ? 0 : Integer.parseInt(myGPGGASplit[7]);
      double dilution = myGPGGASplit[8].isEmpty() ? 0.0 : Double.parseDouble(myGPGGASplit[8]);
      double altitude = myGPGGASplit[9].isEmpty() ? 0.0 : Double.parseDouble(myGPGGASplit[9]);
      String altitudeUnits = myGPGGASplit[10];
      double seaLevel = myGPGGASplit[11].isEmpty() ? 0.0 : Double.parseDouble(myGPGGASplit[11]);
      String geoidUnits = myGPGGASplit[12];

      return new GPGGASentence(time, latitude, longitude, fixType, satellites, dilution, altitude,
          altitudeUnits, seaLevel, geoidUnits);
    }
    catch (NumberFormatException e)
    {
      // if we fail :(
      return null;
    }
  }
}
