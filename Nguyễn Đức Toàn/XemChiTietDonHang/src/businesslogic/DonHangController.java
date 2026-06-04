// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : DonHangController.java
// Goi        : businesslogic (tang BusinessLogic)
// Mo ta      : Lop dieu khien <<control>> cua UC006. Nhan yeu cau tu cac
//              View, goi DonHangService xu ly, tra ket qua ve View.
//              Khop Bieu do lop thiet ke BT6 (4 phuong thuc cong khai).
// Phu thuoc  : businesslogic.DonHangService, businesslogic.ChiTietDonHangDTO,
//              domainmodel.DonHang
// ============================================================
package businesslogic;

import java.util.List;

import domainmodel.DonHang;

/**
 * Controller dieu phoi UC006 (Xem chi tiet don hang).
 *
 * <p>Tuong ung class &lt;&lt;control&gt;&gt; DonHangController trong Bieu do lop BT6:</p>
 * <ul>
 *   <li>yeuCauDSDonHang(): List</li>
 *   <li>traCuuDonHang(keyword, trangThai, phuongTienVT): List</li>
 *   <li>yeuCauChiTietDonHang(maDonHang): ChiTietDonHangDTO</li>
 *   <li>xacNhanVaQuayLai(): List</li>
 * </ul>
 *
 * <p>Controller chi dieu phoi, KHONG chua logic nghiep vu phuc tap (logic
 * nam o Service) — dung tinh than Controller (GRASP) + Low Coupling.</p>
 */
public class DonHangController {

    /** Trang dau tien khi mo man hinh danh sach. */
    private static final int TRANG_DAU = 1;

    private final DonHangService donHangService;

    public DonHangController() {
        this.donHangService = new DonHangService();
    }

    public DonHangController(DonHangService donHangService) {
        this.donHangService = donHangService;
    }

    /**
     * Yeu cau danh sach don hang (luong chinh buoc 1-2, Ref1 muc 10.4).
     *
     * @return danh sach don hang trang dau
     */
    public List<DonHang> yeuCauDSDonHang() {
        return donHangService.layDSDonHang(TRANG_DAU, DonHangService.PAGE_SIZE_MAC_DINH);
    }

    /**
     * Tra cuu don hang theo dieu kien tim kiem (luong chinh buoc 3-5, 5a).
     *
     * @param keyword      tu khoa
     * @param trangThai    trang thai loc
     * @param phuongTienVT phuong tien VT loc
     * @return danh sach don hang phu hop (rong neu khong tim thay - luong 5a)
     */
    public List<DonHang> traCuuDonHang(String keyword, String trangThai, String phuongTienVT) {
        return donHangService.timKiemDonHang(keyword, trangThai, phuongTienVT);
    }

    /**
     * Yeu cau chi tiet mot don hang (luong chinh buoc 6-8, luong 7a).
     *
     * @param maDonHang ma don hang
     * @return DTO chi tiet neu hop le; {@code null} neu khong tim thay
     * @throws DonHangDaHuyException neu don da huy (tang View se hien Man hinh 5)
     */
    public ChiTietDonHangDTO yeuCauChiTietDonHang(String maDonHang) {
        return donHangService.layChiTiet(maDonHang);
    }

    /**
     * Xac nhan va quay lai man hinh danh sach (luong chinh buoc 9, luong 7b).
     *
     * @return danh sach don hang de hien thi lai Man hinh 2
     */
    public List<DonHang> xacNhanVaQuayLai() {
        return donHangService.layDSDonHang(TRANG_DAU, DonHangService.PAGE_SIZE_MAC_DINH);
    }
}
