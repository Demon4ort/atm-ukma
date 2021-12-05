package Storage;

public class Charity {
    public static final int ANIMALS_CARD = 26;
    public static final int ONCOLOGY_CARD = 27;
    public static final int ATO_CARD = 28;
    public static final int KIDS_CARD = 29;

    private static String status;

    public static String getStatus() {
        return status;
    }

    //0 animals
    //itd
    public static void setStatus(String status) {
        Charity.status = status;
    }
}
