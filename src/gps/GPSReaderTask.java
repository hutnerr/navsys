package gps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * Reads GPS.
 * 
 * @author Hunter Baker
 */
public class GPSReaderTask extends SwingWorker<Void, String> implements GPSSubject
{
  private BufferedReader in;
  private String[] sentences;
  private List<GPSObserver> observers;

  // is passed the InputStream to read from and a variable number of NMEA sentences that must be
  // processed
  /**
   * Constructor.
   * 
   * @param is
   *          The input stream to read
   * @param sentences
   *          The sentences we want to watch for
   */
  public GPSReaderTask(final InputStream is, final String... sentences)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.sentences = sentences;
    this.observers = new ArrayList<>();
  }

  // must continuously (until the task is canelled) read lines from the BufferedReader and invoke
  // publish() for each line.

  @Override
  public Void doInBackground()
  {
    try
    {
      // read from the buffered reader
      String line;
      while ((line = in.readLine()) != null)
      {
        // only consider the sentences we want to process
        for (String sentence : sentences)
        {
          if (line.startsWith("$" + sentence))
          {
            publish(line); // invoke publish for each line
            break;
          }
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (in != null)
        {
          in.close(); // ensure we close
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }

  // must notify all GPSObserver objects of each line that is supposed to process.
  // for performance reasons, multiple lines might be coalesced into a chunk, which is why
  // process is passed a List<String> and not an individual string
  @Override
  public void process(final List<String> lines)
  {
    for (String line : lines)
    {
      notifyGPSObservers(line);
    }
  }

  @Override
  public void addGPSObserver(final GPSObserver observer)
  {
    observers.add(observer);
  }

  @Override
  public void notifyGPSObservers(final String sentence)
  {
    for (GPSObserver observer : observers)
    {
      observer.handleGPSData(sentence);
    }
  }

  @Override
  public void removeGPSObserver(final GPSObserver observer)
  {
    observers.remove(observer);
  }
}
