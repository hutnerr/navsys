package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * A driver for the final project.
 *
 * @author Hunter Baker
 */
public class GPSDriver
{
  /**
   * The entry point of the application.
   *
   * @param args
   *          The command line arguments
   * @throws InterruptedException
   *           if something goes wrong
   * @throws InvocationTargetException
   *           if something goes wrong
   */
  public static void main(final String[] args)
      throws InterruptedException, InvocationTargetException
  {
    SwingUtilities.invokeAndWait(new FinalApp());
  }
}
