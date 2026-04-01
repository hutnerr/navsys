package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import feature.StreetSegment;

/**
 * PermanentLabel buckets.
 *
 * @author Hunter Baker
 */
public class PermanentLabelBuckets extends AbstractLabelManager implements PermanentLabelManager
{
  private List<LinkedList<Integer>> buckets;
  private int currentBucket;
  private double bucketWidth;
  private int maxBuckets;

  /**
   * Constructor.
   *
   * @param networkSize
   *          The size of the network
   */
  public PermanentLabelBuckets(final int networkSize)
  {
    super(networkSize);

    this.maxBuckets = 1000;
    this.bucketWidth = 1.0;

    this.buckets = new ArrayList<>(maxBuckets);
    for (int i = 0; i < maxBuckets; i++)
    {
      buckets.add(new LinkedList<Integer>());
    }

    this.currentBucket = 0;

    // init all labels
    for (int i = 0; i < networkSize; i++)
    {
      labels[i].setValue(Double.MAX_VALUE);
    }
  }

  /**
   * Map a distance value to a bucket index.
   * 
   * @param value
   *          The distance value
   * @return The bucket index
   */
  private int getBucketIndex(final double value)
  {
    if (value == Double.MAX_VALUE)
    {
      return -1;
    }

    int index = (int) (value / bucketWidth);
    if (index >= maxBuckets)
    {
      index = maxBuckets - 1;
    }
    return index;
  }

  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int tailNode = segment.getTail();
    int headNode = segment.getHead();

    Label tailLabel = getLabel(tailNode);
    Label headLabel = getLabel(headNode);

    if (!tailLabel.isPermanent())
    {
      return;
    }

    double newValue = tailLabel.getValue() + segment.getLength();

    if (newValue < headLabel.getValue())
    {
      // if already in a bucket, remove it
      if (!headLabel.isPermanent() && headLabel.getValue() < Double.MAX_VALUE)
      {
        int oldBucketIndex = getBucketIndex(headLabel.getValue());
        if (oldBucketIndex >= 0)
        {
          buckets.get(oldBucketIndex).remove(Integer.valueOf(headNode));
        }
      }

      // update label!!!
      headLabel.adjustValue(newValue, segment);

      // add to bucket if not permanent
      if (!headLabel.isPermanent())
      {
        int newBucketIndex = getBucketIndex(newValue);
        if (newBucketIndex >= 0)
        {
          buckets.get(newBucketIndex).add(headNode);
        }
      }
    }
  }

  @Override
  public Label getSmallestLabel()
  {
    // we want the next non empty bucket
    while (currentBucket < maxBuckets)
    {
      LinkedList<Integer> bucket = buckets.get(currentBucket);
      if (!bucket.isEmpty())
      {
        int intersectionID = bucket.getFirst();
        return getLabel(intersectionID);
      }
      currentBucket++;
    }
    return null;
  }

  @Override
  public void makePermanent(final int intersectionID)
  {
    Label label = getLabel(intersectionID);

    if (label.isPermanent())
    {
      return;
    }

    // remove from its bucket
    int bucketIndex = getBucketIndex(label.getValue());
    if (bucketIndex >= 0)
    {
      buckets.get(bucketIndex).remove(Integer.valueOf(intersectionID));
    }
    label.makePermanent();
  }
}
