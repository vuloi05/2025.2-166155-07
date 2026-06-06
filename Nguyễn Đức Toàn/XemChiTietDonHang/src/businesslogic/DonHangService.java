// Tac gia    : Nguyen Duc Toan - 20235846
package businesslogic;

import java.util.List;

import dataaccess.DonHangDAO;
import dataaccess.IDonHangDAO;
import dataaccess.MatHangDonHangDAO;
import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

public class DonHangService {

    public static final int PAGE_SIZE_MAC_DINH = 20;

    private final IDonHangDAO donHangDAO;
    private final MatHangDonHangDAO matHangDAO;

    public DonHangService() {
        this(new DonHangDAO(), new MatHangDonHangDAO());
    }

    public DonHangService(IDonHangDAO donHangDAO, MatHangDonHangDAO matHangDAO) {
        this.donHangDAO = donHangDAO;
        this.matHangDAO = matHangDAO;
    }

    public List<DonHang> layDSDonHang(int page, int pageSize) {
        return donHangDAO.findAll(page, pageSize);
    }

    public List<DonHang> timKiemDonHang(String keyword, String trangThai, String phuongTienVT) {
        return donHangDAO.findByFilters(keyword, trangThai, phuongTienVT);
    }

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

    public int demTongSoDonHang() {
        return donHangDAO.countAll();
    }
}
