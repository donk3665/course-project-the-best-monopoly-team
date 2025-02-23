package GUI.Screens.ScreenElements;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontCreator {
    static Font aharoni;
    static boolean fontLoaded =false;
    public static void createFont(){
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontCreator.class.getResourceAsStream("/Images/MenuAssets/aharoni.ttf")));
        }
        catch (FontFormatException | IOException e){
            System.err.println("Font error");
            System.exit(0);
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        aharoni = font;
    }
    public static Font getFontAharoni(float pt){
        if (!fontLoaded){
            createFont();
            fontLoaded = true;
        }
        return aharoni.deriveFont(pt);
    }

}
