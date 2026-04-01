package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dataprocessing.Geocoder;
import feature.Street;
import feature.StreetSegment;
import feature.StreetsReader;
import geography.AbstractMapProjection;
import geography.ConicalEqualAreaProjection;
import geography.GeographicShape;
import geography.GeographicShapesReader;
import gps.GPSPositionObserver;
import gps.GPSReaderTask;
import gps.GPSSimulator;
import graph.Label;
import graph.LabelSettingAlgorithm;
import graph.PermanentLabelBuckets;
import graph.PermanentLabelManager;
import graph.StreetNetwork;
import gui.CartographyDocument;
import gui.DynamicCartographyPanel;
import gui.StreetSegmentCartographer;
import relocator.RouteRelocator;

/**
 * The application for final project.
 * 
 * @author HUNTER BAKER
 * @version 1.0
 */
public class FinalApp implements Runnable
{
  // hard encoded addresses for simplicity of testing and display
  private static final String ORIGIN_ADDRESS = "410 Paul St";
  private static final String DESTINATION_ADDRESS = "6500 Mount Clinton Pike";

  private DynamicCartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> document;
  private CartographyDocument<GeographicShape> geographicShapes;
  private Map<String, Street> streets;
  private JFrame frame;
  private LabelSettingAlgorithm alg;
  private StreetSegment originSegment, destSeg;
  private StreetNetwork network;
  private RouteRelocator routeRelocator;
  private AbstractMapProjection proj;
  private GPSReaderTask gpsReader;

  @Override
  public void run()
  {
    try
    {
      proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);

      // read geographic shapes
      InputStream isgeo = new FileInputStream(new File("rockingham-streets.geo"));
      GeographicShapesReader gsReader = new GeographicShapesReader(isgeo, proj);
      geographicShapes = gsReader.read();
      System.out.println("Read the .geo file");

      // read streets
      InputStream iss = new FileInputStream(new File("rockingham-streets.str"));
      StreetsReader sReader = new StreetsReader(iss, geographicShapes);
      streets = new HashMap<String, Street>();
      document = sReader.read(streets);
      System.out.println("Read the .str file");

      network = StreetNetwork.createStreetNetwork(streets);

      // setup ui
      panel = new DynamicCartographyPanel<StreetSegment>(document, new StreetSegmentCartographer(),
          proj);
      frame = new JFrame("NavSys");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 600);
      frame.setContentPane(panel);

      Geocoder geocoder = new Geocoder(geographicShapes, document, streets);

      // geocode origin
      List<String> originSegments = new ArrayList<>();
      geocodeAddress(geocoder, ORIGIN_ADDRESS, originSegments);
      originSegment = document.getElement(originSegments.get(0));

      // geocode dest
      List<String> destSegments = new ArrayList<>();
      geocodeAddress(geocoder, DESTINATION_ADDRESS, destSegments);
      destSeg = document.getElement(destSegments.get(0));

      // setup gps
      GPSSimulator gps = new GPSSimulator("rockingham.gps");
      InputStream is = gps.getInputStream();

      gpsReader = new GPSReaderTask(is, "GPGGA");
      gpsReader.addGPSObserver(panel);
      frame.setVisible(true);
      gpsReader.execute();

      // calculating our shortest path
      PermanentLabelManager labels = new PermanentLabelBuckets(network.size());
      alg = new LabelSettingAlgorithm(labels);

      // calculate paths from dest to ALL other points in graph
      // we do this so we can have the shorest path from everywhere to the destination
      // and we only have to calculate this once
      final int dest = destSeg.getHead();
      Map<Integer, Label> allPaths = ((LabelSettingAlgorithm) alg).findAllPaths(dest, network);

      // now we can extract our current path we want
      final int origin = originSegment.getHead();
      Map<String, StreetSegment> exactPath = ((LabelSettingAlgorithm) alg).extractPath(origin, dest,
          allPaths);

      // disp[lay it
      document.setHighlighted(exactPath);

      // setup route relocating
      routeRelocator = new RouteRelocator(alg, exactPath, allPaths, document, destSeg);
      GPSPositionObserver positionObserver = new GPSPositionObserver(routeRelocator, proj);
      gpsReader.addGPSObserver(positionObserver);
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(frame, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * wrapper to help geocode my static addresses.
   * 
   * @param geocoder
   *          the geocoder to use
   * @param address
   *          the address to geocode
   * @param segmentIDs
   *          the segmentids
   */
  private void geocodeAddress(final Geocoder geocoder, final String address,
      final List<String> segmentIDs)
  {
    String[] parts = address.split(" ", 2);
    int streetNumber = Integer.parseInt(parts[0]);
    String streetName = parts[1];
    geocoder.geocode(streetName, streetNumber, segmentIDs);
  }
}
