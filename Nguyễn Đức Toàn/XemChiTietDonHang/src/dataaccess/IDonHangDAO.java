// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : IDonHangDAO.java
// Goi        : dataaccess (tang DataAccess)
// Mo ta      : Interface DAO cho Don hang. Khop Bieu do lop thiet ke BT6.
//              Day la diem cai tien BT6 (DIP): tang BusinessLogic phu thuoc
//              vao abstraction nay, KHONG phu thuoc vao lop cu the DonHangDAO.
// Phu thuoc  : domainmodel.DonHang, domainmodel.Site
// ============================================================
package dataaccess;

import java.util.List;

import domainmodel.DonHang;
import domainmodel.Site;

/**
 * Cong truy xuat du lieu Don hang (DAO pattern - phan abstraction).
 *
 * <p>Tuong ung interface IDonHangDAO trong Bieu do lop BT6. Cac phuong thuc
 * dung tien to "find" theo dung quy uoc DAO trong bieu do.</p>
 */
public interface IDonHangDAO {

    /**
     * Lay danh sach don hang theo trang (phuc vu phan trang - muc 7.7 SRS).
     *
     * @param page     so thu tu trang, bat dau tu 1
     * @param pageSize so ban ghi moi trang
     * @return danh sach don hang trong trang yeu cau
     */
    List<DonHang> findAll(int page, int pageSize);

    /**
     * Tim mot don hang theo ma.
     *
     * @param maDonHang ma don hang can tim
     * @return don hang tuong ung, hoac null neu khong ton tai
     */
    DonHang findByCode(String maDonHang);

    /**
     * Tim danh sach don hang theo bo loc (tu khoa + trang thai + phuong tien VT).
     * Tuong ung chuc nang tim kiem o Man hinh danh sach (muc 7.7 SRS).
     *
     * @param keyword     tu khoa (ma don hang / ma site / ten site); null hoac rong = bo qua
     * @param trangThai   ma trang thai loc; null hoac rong = tat ca
     * @param phuongTienVT ma phuong tien van chuyen loc; null hoac rong = tat ca
     * @return danh sach don hang thoa man dieu kien loc
     */
    List<DonHang> findByFilters(String keyword, String trangThai, String phuongTienVT);

    /**
     * Dem tong so don hang (phuc vu phan trang).
     *
     * @return tong so don hang trong he thong
     */
    int countAll();

    /**
     * Tim Site theo ma (phuc vu hien thi thong tin Site o man hinh chi tiet).
     *
     * @param maSite ma site can tim
     * @return site tuong ung, hoac null neu khong ton tai
     */
    Site findSiteByCode(String maSite);
}
