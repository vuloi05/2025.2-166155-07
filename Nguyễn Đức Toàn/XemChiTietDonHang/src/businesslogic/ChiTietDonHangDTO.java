// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : ChiTietDonHangDTO.java
// Goi        : businesslogic (tang BusinessLogic)
// Mo ta      : Doi tuong truyen du lieu (DTO) cho man hinh chi tiet don hang.
//              Diem cai tien BT6 (Low Coupling + Pure Fabrication): gom
//              DonHang + danh sach mat hang + Site vao 1 goi, de tang
//              Presentation khong nhan truc tiep entity tho.
// Phu thuoc  : domainmodel.DonHang, domainmodel.MatHangDonHang, domainmodel.Site
// ============================================================
package businesslogic;

import java.util.ArrayList;
import java.util.List;

import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

/**
 * DTO chua toan bo du lieu can thiet de hien thi Man hinh chi tiet don hang
 * (Man hinh 3 - muc 7.8 SRS).
 *
 * <p>Tuong ung class ChiTietDonHangDTO trong Bieu do lop BT6:</p>
 * <ul>
 *   <li>Thuoc tinh: donHang, dsMatHang, site</li>
 *   <li>Hanh vi: {@code static createFrom(donHang, dsMatHang, site)} —
 *       Factory Method tap trung logic tao DTO.</li>
 * </ul>
 */
public class ChiTietDonHangDTO {

    private final DonHang donHang;
    private final List<MatHangDonHang> dsMatHang;
    private final Site site;

    private ChiTietDonHangDTO(DonHang donHang, List<MatHangDonHang> dsMatHang, Site site) {
        this.donHang = donHang;
        this.dsMatHang = dsMatHang;
        this.site = site;
    }

    /**
     * Tao DTO tu cac entity nguon (Factory Method - khop Bieu do lop BT6).
     *
     * @param donHang   thong tin chung don hang
     * @param dsMatHang danh sach mat hang trong don
     * @param site      thong tin Site cua don hang
     * @return DTO da gom du du lieu cho man hinh chi tiet
     */
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

    /**
     * So ngay van chuyen cua don, suy ra tu Site theo phuong tien cua don.
     * Phuc vu truong "So ngay van chuyen" o Man hinh chi tiet (muc 7.8 SRS).
     *
     * @return so ngay van chuyen; 0 neu thieu thong tin site/phuong tien
     */
    public int getSoNgayVanChuyen() {
        if (site == null || donHang == null) {
            return 0;
        }
        return site.layThoiGianVanChuyen(donHang.getPhuongTienVC());
    }
}
