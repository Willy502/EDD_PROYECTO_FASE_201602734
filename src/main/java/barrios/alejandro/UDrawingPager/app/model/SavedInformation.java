package barrios.alejandro.UDrawingPager.app.model;

public class SavedInformation {

    private static SavedInformation savedInformation = null;
    private int hatchQt = 0;

    public static SavedInformation getInstance() {
        if (savedInformation == null)
            savedInformation = new SavedInformation();
        return savedInformation;
    }

    public int getHatchQt() {
        return hatchQt;
    }

    public void setWindowsQt(int hatchQt) {
        this.hatchQt = hatchQt;
    }
}
