// Tac gia    : Nguyen Duc Toan - 20235846
package dataaccess;

import java.util.List;

import domainmodel.DonHang;
import domainmodel.Site;

public interface IDonHangDAO {

    List<DonHang> findAll(int page, int pageSize);

    DonHang findByCode(String maDonHang);

    List<DonHang> findByFilters(String keyword, String trangThai, String phuongTienVT);

    int countAll();

    Site findSiteByCode(String maSite);
}
