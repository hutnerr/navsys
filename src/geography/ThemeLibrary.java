package geography;

/**
 * An object that can be used to get a Theme for a particular GeographicShape or Feature.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1
 */
public interface ThemeLibrary
{

  /**
   * Get the Theme for highlighted elements.
   * 
   * @return     The Theme to use
   */
  public abstract Theme getHighlightTheme();

  /**
   * Get the Theme for the given feature code.
   *
   * @param code   The code of the Feature to render
   * @return     The Theme to use.
   */
  public Theme getTheme(String code);

}
