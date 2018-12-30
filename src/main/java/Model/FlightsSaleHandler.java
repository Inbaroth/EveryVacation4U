package Model;

public class FlightsSaleHandler {

    public enum Type {PENDING, CONFIRM};
    private int vacationId;
    private String sellerUserName;
    private String buyerUserName;

    public FlightsSaleHandler(int vacationId, String sellerUserName, String buyerUserName) {
        this.vacationId = vacationId;
        this.sellerUserName = sellerUserName;
        this.buyerUserName = buyerUserName;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

}
