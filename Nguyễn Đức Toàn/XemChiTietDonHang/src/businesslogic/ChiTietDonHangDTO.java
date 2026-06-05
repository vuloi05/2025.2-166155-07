// Tac gia    : Nguyen Duc Toan - 20235846
package businesslogic;

import java.util.ArrayList;
import java.util.List;

import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

public class ChiTietDonHangDTO {

    private final DonHang donHang;
    private final List<MatHangDonHang> dsMatHang;
    private final Site site;

    private ChiTietDonHangDTO(DonHang donHang, List<MatHangDonHang> dsMatHang, Site site) {
        this.donHang = donHang;
        this.dsMatHang = dsMatHang;
        this.site = site;
    }

    public static ChiTietDonHangDTO createFrom(DonHang donHang,
                                               List<MatHangDonHang> dsMatHang,
                                               Site site) {
        List<MatHangDonHang> banSao = (dsMatHang == null)
                ? new ArrayList<>()
                : new ArrayList<>(dsMatHang);
        return new ChiTietDonHangDTO(donHang, banSao, site);
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public List<MatHangDonHang> getDsMatHang() {
        return new ArrayList<>(dsMatHang);
    }

    public Site getSite() {
        return site;
    }

    public int getSoNgayVanChuyen() {
        if (site == null || donHang == null) {
            return 0;
        }
        return site.layThoiGianVanChuyen(donHang.getPhuongTienVC());
    }
}
