// Tac gia    : Nguyen Duc Toan - 20235846
package businesslogic;

public class DonHangDaHuyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String maDonHang;

    public DonHangDaHuyException(String maDonHang) {
        super("Don hang " + maDonHang + " da bi huy, khong the xem chi tiet.");
        this.maDonHang = maDonHang;
    }

    public String getMaDonHang() {
        return maDonHang;
    }
}
