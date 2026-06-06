// Tac gia    : Nguyen Duc Toan - 20235846
package dataaccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainmodel.DonHang;
import domainmodel.Site;

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
        themSite(new Site("S01", "Site Nhật Bản", 25, 5));
        themSite(new Site("S02", "Site S02", 5, 5));
        themSite(new Site("S03", "Site Singapore", 22, 5));
        themSite(new Site("S04", "Site Đài Loan", 30, 7));
        themSite(new Site("SITE-TW02", "Nhà kho Đài Bắc", 28, 6));
        themSite(new Site("SITE-JP01", "Site Nhật Bản 01", 25, 5));
    }

    private void themSite(Site site) {
        dsSite.put(site.getMaSite(), site);
    }

    private void khoiTaoDonHang() {
        Date ngayTao = ngayCoDinh(2026, Calendar.JUNE, 6);

        // 2 don Nhap (Yeu cau moi)
        dsDonHang.add(new DonHang("DH-2026-001", "S04", "Site Đài Loan",
                1, Site.PT_TAU, ngayTao, null, "NHAP"));
        dsDonHang.add(new DonHang("DH-0001", "S03", "Site Singapore",
                2, Site.PT_HANG_KHONG, ngayTao, null, "NHAP"));

        // 7 don Dang xu ly
        dsDonHang.add(new DonHang("DH-2026-002", "S01", "Site Nhật Bản",
                2, Site.PT_HANG_KHONG, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-004", "S04", "Site Đài Loan",
                1, Site.PT_TAU, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-005", "SITE-TW02", "Nhà kho Đài Bắc",
                2, Site.PT_TAU, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-006", "SITE-JP01", "Site Nhật Bản 01",
                1, Site.PT_HANG_KHONG, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-007", "S02", "Site S02",
                2, Site.PT_TAU, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-009", "S01", "Site Nhật Bản",
                1, Site.PT_TAU, ngayTao, null, "DANG_XU_LY"));
        dsDonHang.add(new DonHang("DH-2026-010", "S03", "Site Singapore",
                2, Site.PT_HANG_KHONG, ngayTao, null, "DANG_XU_LY"));

        // 2 don Da xu ly
        dsDonHang.add(new DonHang("DH-2026-003", "S02", "Site S02",
                1, Site.PT_TAU, ngayTao, ngayTao, "DA_GUI"));
        dsDonHang.add(new DonHang("DH-2026-011", "S04", "Site Đài Loan",
                1, Site.PT_HANG_KHONG, ngayTao, ngayTao, "DA_GUI"));

        // 2 don Da huy
        dsDonHang.add(new DonHang("DH-2026-008", "S04", "Site Đài Loan",
                1, Site.PT_TAU, ngayTao, null, DonHang.TRANG_THAI_DA_HUY));
        dsDonHang.add(new DonHang("DH-2026-012", "S01", "Site Nhật Bản",
                1, Site.PT_HANG_KHONG, ngayTao, null, DonHang.TRANG_THAI_DA_HUY));
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

    private boolean khopTuKhoa(DonHang dh, String tuKhoa) {
        if (tuKhoa.isEmpty()) {
            return true;
        }
        return chua(dh.getMaDonHang(), tuKhoa)
                || chua(dh.getMaSite(), tuKhoa)
                || chua(dh.getTenSite(), tuKhoa);
    }

    private boolean chua(String giaTri, String tuKhoa) {
        return giaTri != null && giaTri.toLowerCase().contains(tuKhoa);
    }

    private boolean khopGiaTriLoc(String giaTri, String loc) {
        if (loc == null || loc.isEmpty()) {
            return true;
        }
        return loc.equals(giaTri);
    }

    private Date ngayCoDinh(int nam, int thang, int ngay) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, thang, ngay, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
