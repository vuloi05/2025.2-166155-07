// Tac gia    : Nguyen Duc Toan - 20235846
package businesslogic;

import java.util.List;

import domainmodel.DonHang;

public class DonHangController {

    private static final int TRANG_DAU = 1;

    private final DonHangService donHangService;

    public DonHangController() {
        this.donHangService = new DonHangService();
    }

    public DonHangController(DonHangService donHangService) {
        this.donHangService = donHangService;
    }

    public List<DonHang> yeuCauDSDonHang() {
        return donHangService.layDSDonHang(TRANG_DAU, DonHangService.PAGE_SIZE_MAC_DINH);
    }

    public List<DonHang> traCuuDonHang(String keyword, String trangThai, String phuongTienVT) {
        return donHangService.timKiemDonHang(keyword, trangThai, phuongTienVT);
    }

    public ChiTietDonHangDTO yeuCauChiTietDonHang(String maDonHang) {
        return donHangService.layChiTiet(maDonHang);
    }

    public List<DonHang> xacNhanVaQuayLai() {
        return donHangService.layDSDonHang(TRANG_DAU, DonHangService.PAGE_SIZE_MAC_DINH);
    }
}
