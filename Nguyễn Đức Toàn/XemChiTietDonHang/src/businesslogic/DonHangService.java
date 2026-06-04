// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : DonHangService.java
// Goi        : businesslogic (tang BusinessLogic)
// Mo ta      : Lop nghiep vu chinh cua UC006 - Xem chi tiet don hang.
//              Khop Bieu do lop thiet ke BT6: phu thuoc IDonHangDAO (DIP),
//              tra ve ChiTietDonHangDTO, dung DonHang.laDaHuy().
// Phu thuoc  : dataaccess.IDonHangDAO, dataaccess.DonHangDAO,
//              dataaccess.MatHangDonHangDAO, domainmodel.*, businesslogic.*
// ============================================================
package businesslogic;

import java.util.List;

import dataaccess.DonHangDAO;
import dataaccess.IDonHangDAO;
import dataaccess.MatHangDonHangDAO;
import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

/**
 * Service nghiep vu cho UC006 (Xem chi tiet don hang).
 *
 * <p>Tuong ung class DonHangService trong Bieu do lop BT6:</p>
 * <ul>
 *   <li>Thuoc tinh: {@code donHangDAO: IDonHangDAO}, {@code matHangDAO: MatHangDonHangDAO}</li>
 *   <li>Hanh vi: layDSDonHang, timKiemDonHang, layChiTiet, demTongSoDonHang</li>
 * </ul>
 *
 * <p>Diem cai tien BT6 da ap dung tai day:</p>
 * <ul>
 *   <li>DIP: phu thuoc interface {@link IDonHangDAO}, khong phu thuoc lop cu the.</li>
 *   <li>Low Coupling: tra ve {@link ChiTietDonHangDTO} thay vi entity tho.</li>
 *   <li>Information Expert: kiem tra huy bang {@code donHang.laDaHuy()}.</li>
 *   <li>Bo {@code DonHang.layDSMatHang()}: lay mat hang qua {@link MatHangDonHangDAO}.</li>
 * </ul>
 */
public class DonHangService {

    /** So ban ghi mac dinh moi trang (muc 7.7 SRS - phan trang). */
    public static final int PAGE_SIZE_MAC_DINH = 10;

    private final IDonHangDAO donHangDAO;
    private final MatHangDonHangDAO matHangDAO;

    /** Constructor mac dinh dung cho chuong trinh chinh (mock DAO that). */
    public DonHangService() {
        this(new DonHangDAO(), new MatHangDonHangDAO());
    }

    /**
     * Constructor cho phep tiem (inject) DAO — phuc vu kiem thu don vi (DIP).
     *
     * @param donHangDAO ban trien khai IDonHangDAO (co the la DAO gia khi test)
     * @param matHangDAO DAO mat hang (co the la DAO gia khi test)
     */
    public DonHangService(IDonHangDAO donHangDAO, MatHangDonHangDAO matHangDAO) {
        this.donHangDAO = donHangDAO;
        this.matHangDAO = matHangDAO;
    }

    /**
     * Lay danh sach don hang theo trang (Ref1 - muc 10.4 SRS).
     *
     * @param page     so trang (>= 1)
     * @param pageSize so ban ghi moi trang (>= 1)
     * @return danh sach don hang trong trang
     */
    public List<DonHang> layDSDonHang(int page, int pageSize) {
        return donHangDAO.findAll(page, pageSize);
    }

    /**
     * Tim kiem don hang theo tu khoa + trang thai + phuong tien VT
     * (luong chinh buoc 3-5 va luong thay the 5a - muc 2 SRS).
     *
     * @param keyword      tu khoa tim kiem
     * @param trangThai    trang thai loc (null/rong = tat ca)
     * @param phuongTienVT phuong tien VT loc (null/rong = tat ca)
     * @return danh sach don hang thoa man; rong neu khong tim thay (luong 5a)
     */
    public List<DonHang> timKiemDonHang(String keyword, String trangThai, String phuongTienVT) {
        return donHangDAO.findByFilters(keyword, trangThai, phuongTienVT);
    }

    /**
     * Lay chi tiet mot don hang theo ma (luong chinh buoc 7-8, luong 7a).
     *
     * <p>Day la module duoc chon de kiem thu hop den + hop trang (BT7).
     * Cac nhanh xu ly:</p>
     * <ol>
     *   <li>Ma don rong/null  -&gt; nem {@link IllegalArgumentException}.</li>
     *   <li>Khong tim thay don -&gt; tra ve {@code null}.</li>
     *   <li>Don da bi huy      -&gt; nem {@link DonHangDaHuyException} (luong 7a).</li>
     *   <li>Don hop le         -&gt; tra ve {@link ChiTietDonHangDTO} (luong chinh).</li>
     * </ol>
     *
     * @param maDonHang ma don hang can xem chi tiet
     * @return DTO chi tiet don hang neu hop le; {@code null} neu khong tim thay
     * @throws IllegalArgumentException neu ma don hang rong/null
     * @throws DonHangDaHuyException    neu don hang da bi huy
     */
    public ChiTietDonHangDTO layChiTiet(String maDonHang) {
        if (maDonHang == null || maDonHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma don hang khong duoc rong");
        }

        DonHang donHang = donHangDAO.findByCode(maDonHang);
        if (donHang == null) {
            return null;
        }

        if (donHang.laDaHuy()) {
            throw new DonHangDaHuyException(maDonHang);
        }

        List<MatHangDonHang> dsMatHang = matHangDAO.findByOrderCode(maDonHang);
        Site site = donHangDAO.findSiteByCode(donHang.getMaSite());
        return ChiTietDonHangDTO.createFrom(donHang, dsMatHang, site);
    }

    /**
     * Dem tong so don hang (phuc vu tinh so trang - muc 7.7 SRS).
     *
     * @return tong so don hang
     */
    public int demTongSoDonHang() {
        return donHangDAO.countAll();
    }
}
