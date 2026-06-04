// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : DonHangDaHuyException.java
// Goi        : businesslogic (tang BusinessLogic)
// Mo ta      : Ngoai le bao hieu don hang da bi huy khi nguoi dung
//              co xem chi tiet (luong thay the 7a - muc 2 SRS).
//              Day la lop ho tro ky thuat (exception), khong phai lop
//              nghiep vu tren bieu do lop.
// Phu thuoc  : (khong)
// ============================================================
package businesslogic;

/**
 * Bao hieu truong hop don hang da bi huy (luong thay the 7a UC006).
 *
 * <p>Service nem ngoai le nay khi {@code DonHang.laDaHuy()} tra ve true,
 * de tang Presentation hien Man hinh 5 (CanhBaoHuyDialog - muc 7.10 SRS)
 * thay vi mo man hinh chi tiet.</p>
 */
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
