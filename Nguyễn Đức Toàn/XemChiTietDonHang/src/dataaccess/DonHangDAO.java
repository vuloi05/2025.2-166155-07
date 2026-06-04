// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : DonHangDAO.java
// Goi        : dataaccess (tang DataAccess)
// Mo ta      : Ban trien khai cua IDonHangDAO voi du lieu gia lap (mock data).
//              Khop Bieu do lop thiet ke BT6: DonHangDAO ..|> IDonHangDAO.
//              La cong duy nhat xuong tang Database (muc 14 SRS).
// Phu thuoc  : dataaccess.IDonHangDAO, domainmodel.DonHang, domainmodel.Site
// ============================================================
package dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainmodel.DonHang;
import domainmodel.Site;

/**
 * Trien khai IDonHangDAO bang du lieu gia lap trong bo nho.
 *
 * <p>Tuong ung class DonHangDAO trong Bieu do lop BT6. Lop nay thay the
 * cho viec ket noi CSDL that; nho DIP (Service phu thuoc IDonHangDAO),
 * sau nay co the doi sang ban trien khai dung MySQL ma khong sua Service.</p>
 */
public class DonHangDAO implements IDonHangDAO {

    private final List<DonHang> dsDonHang;
    private final Map<String, Site> dsSite;

    public DonHangDAO() {
        this.dsSite = new HashMap<>();
        this.dsDonHang = new ArrayList<>();
        khoiTaoSite();
        khoiTaoDonHang();
    }

    private void khoiTaoSite() {
        themSite(new Site("SITE-TW01", "Taiwan Components Co.", 30, 7));
        themSite(new Site("SITE-JP02", "Japan Electronics Ltd.", 25, 5));
        themSite(new Site("SITE-KR03", "Korea Tech Supply", 28, 6));
        themSite(new Site("SITE-CN04", "China Manufacturing Group", 20, 4));
        themSite(new Site("SITE-SG05", "Singapore Trading Hub", 22, 5));
    }

    private void themSite(Site site) {
        dsSite.put(site.getMaSite(), site);
    }

    private void khoiTaoDonHang() {
        // Don da gui - giao bang Tau (co ngay gui that)
        dsDonHang.add(new DonHang("DH-2025-001", "SITE-TW01", "Taiwan Components Co.",
                3, Site.PT_TAU, ngayTruoc(20), ngayTruoc(18), "DA_GUI"));

        // Don dang xu ly - giao bang Hang khong (chua gui -> ngayGui = null)
        dsDonHang.add(new DonHang("DH-2025-002", "SITE-JP02", "Japan Electronics Ltd.",
                2, Site.PT_HANG_KHONG, ngayTruoc(12), null, "DANG_XU_LY"));

        // Don nhap (chua gui) - giao bang Tau (ngayGui = null)
        dsDonHang.add(new DonHang("DH-2025-003", "SITE-KR03", "Korea Tech Supply",
                1, Site.PT_TAU, ngayTruoc(8), null, "NHAP"));

        // Don da gui - giao bang Hang khong (co ngay gui that)
        dsDonHang.add(new DonHang("DH-2025-004", "SITE-CN04", "China Manufacturing Group",
                2, Site.PT_HANG_KHONG, ngayTruoc(5), ngayTruoc(3), "DA_GUI"));

        // Don DA HUY - phuc vu luong thay the 7a (xem chi tiet don da huy)
        dsDonHang.add(new DonHang("DH-2025-005", "SITE-SG05", "Singapore Trading Hub",
                1, Site.PT_TAU, ngayTruoc(15), null, DonHang.TRANG_THAI_DA_HUY));
    }

    @Override
    public List<DonHang> findAll(int page, int pageSize) {
        List<DonHang> ketQua = new ArrayList<>();
        if (page < 1 || pageSize < 1) {
            return ketQua;
        }
        int tuChiSo = (page - 1) * pageSize;
        int denChiSo = Math.min(tuChiSo + pageSize, dsDonHang.size());
        for (int i = tuChiSo; i < denChiSo; i++) {
            ketQua.add(dsDonHang.get(i));
        }
        return ketQua;
    }

    @Override
    public DonHang findByCode(String maDonHang) {
        if (maDonHang == null) {
            return null;
        }
        for (DonHang dh : dsDonHang) {
            if (dh.getMaDonHang().equals(maDonHang)) {
                return dh;
            }
        }
        return null;
    }

    @Override
    public List<DonHang> findByFilters(String keyword, String trangThai, String phuongTienVT) {
        List<DonHang> ketQua = new ArrayList<>();
        String tuKhoa = (keyword == null) ? "" : keyword.trim().toLowerCase();

        for (DonHang dh : dsDonHang) {
            if (!khopTuKhoa(dh, tuKhoa)) {
                continue;
            }
            if (!khopGiaTriLoc(dh.getTrangThai(), trangThai)) {
                continue;
            }
            if (!khopGiaTriLoc(dh.getPhuongTienVC(), phuongTienVT)) {
                continue;
            }
            ketQua.add(dh);
        }
        return ketQua;
    }

    @Override
    public int countAll() {
        return dsDonHang.size();
    }

    @Override
    public Site findSiteByCode(String maSite) {
        if (maSite == null) {
            return null;
        }
        return dsSite.get(maSite);
    }

    /** Kiem tra don hang co chua tu khoa (theo ma don / ma site / ten site). */
    private boolean khopTuKhoa(DonHang dh, String tuKhoa) {
        if (tuKhoa.isEmpty()) {
            return true;
        }
        return dh.getMaDonHang().toLowerCase().contains(tuKhoa)
                || dh.getMaSite().toLowerCase().contains(tuKhoa)
                || dh.getTenSite().toLowerCase().contains(tuKhoa);
    }

    /** Kiem tra mot truong co khop gia tri loc khong (null/rong = bo qua loc). */
    private boolean khopGiaTriLoc(String giaTriThucTe, String giaTriLoc) {
        if (giaTriLoc == null || giaTriLoc.trim().isEmpty()) {
            return true;
        }
        return giaTriLoc.equals(giaTriThucTe);
    }

    /** Tien ich: tra ve ngay cach hom nay {@code soNgay} ve truoc. */
    private Date ngayTruoc(int soNgay) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -soNgay);
        return cal.getTime();
    }
}
