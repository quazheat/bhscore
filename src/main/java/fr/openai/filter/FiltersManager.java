package fr.openai.filter;


public class FiltersManager {
    static boolean swearingFilter = true;

    public FiltersManager() {
    }
    public static boolean isSwearingFilter() {
        return swearingFilter;
    }

    public static void setEnableSwearingFilter(boolean enabled) {
        swearingFilter = enabled;
    }
}
