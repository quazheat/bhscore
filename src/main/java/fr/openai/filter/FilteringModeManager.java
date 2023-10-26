package fr.openai.filter;

public class FilteringModeManager {
    private static boolean rageModeEnabled;
    private static boolean loyalModeEnabled;

    public static boolean isRageModeEnabled() {
        return rageModeEnabled;
    }
    public static void setRageModeEnabled(boolean rageModeEnabled) {
        FilteringModeManager.rageModeEnabled = rageModeEnabled;
    }
    public static boolean isLoyalModeEnabled() {
        return loyalModeEnabled;
    }
    public static void setLoyalModeEnabled(boolean loyalModeEnabled) {
        FilteringModeManager.loyalModeEnabled = loyalModeEnabled;
    }
    public static void toggleRageMode() {
        rageModeEnabled = !rageModeEnabled;
    }
    public static void toggleLoyalMode() {
        loyalModeEnabled = !loyalModeEnabled;
    }
}
