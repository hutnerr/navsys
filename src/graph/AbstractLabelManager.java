package graph;

/**
 * Abstract implementation of LabelManager.
 *
 * @author Hunter Baker
 */
public abstract class AbstractLabelManager implements LabelManager
{
  protected Label[] labels;

  /**
   * Constructor.
   *
   * @param networkSize
   *          The size of the network.
   */
  public AbstractLabelManager(final int networkSize)
  {
    this.labels = new Label[networkSize];

    for (int i = 0; i < networkSize; i++)
    {
      this.labels[i] = new Label(i);
    }
  }

  @Override
  public Label getLabel(final int intersectionID)
  {
    if (intersectionID < 0 || intersectionID >= labels.length)
    {
      throw new IndexOutOfBoundsException("ruh roh");
    }
    return labels[intersectionID];
  }
}
