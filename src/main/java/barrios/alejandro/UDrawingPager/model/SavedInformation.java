package barrios.alejandro.UDrawingPager.model;

public class SavedInformation {

    private static SavedInformation savedInformation = null;
    private int windowsQt = 0;

    public static SavedInformation getInstance() {
        if (savedInformation == null)
            savedInformation = new SavedInformation();
        return savedInformation;
    }

    public int getWindowsQt() {
        return windowsQt;
    }

    public void setWindowsQt(int windowsQt) {
        this.windowsQt = windowsQt;
    }
}
