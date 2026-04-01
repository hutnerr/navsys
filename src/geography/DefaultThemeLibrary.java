package geography;

import java.awt.*;

/**
 * The default ThemeLibrary.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class DefaultThemeLibrary implements ThemeLibrary
{
  
  private Theme highlight, theme;
  
  /**
   * Default COnstructor.
   */
  public DefaultThemeLibrary()
  {
    theme = new Theme(Color.BLACK, new BasicStroke());
    highlight = new Theme(Color.GREEN, new BasicStroke());
  }
  
  /**
   * Get the Theme to use for highlighted elements.
   * 
   * @return The Theme
   */
  @Override
  public Theme getHighlightTheme()
  {
    return highlight;
  }
  
  /**
   * Get the Theme to use for a particular element.
   * 
   * @return The Theme
   */
  @Override
  public Theme getTheme(final String id)
  {
    return theme;
  }
}
