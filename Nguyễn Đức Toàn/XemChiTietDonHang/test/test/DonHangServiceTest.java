// Tac gia    : Nguyen Duc Toan - 20235846
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import businesslogic.ChiTietDonHangDTO;
import businesslogic.DonHangDaHuyException;
import businesslogic.DonHangService;
import dataaccess.IDonHangDAO;
import dataaccess.MatHangDonHangDAO;
import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

public class DonHangServiceTest {

    private DonHangService service;

    @Before
    public void setUp() {
        // Arrange chung: DAO gia voi 1 don hop le + 1 don da huy.
        FakeDonHangDAO fakeDonHangDAO = new FakeDonHangDAO();

        Site site = new Site("SITE-TW01", "Taiwan Components Co.", 30, 7);
        fakeDonHangDAO.themSite(site);

        // Don hop le: trang thai NHAP, giao bang Tau, chua gui (ngayGui = null)
        fakeDonHangDAO.themDonHang(new DonHang("DH-VALID", "SITE-TW01", "Taiwan Components Co.",
                2, Site.PT_TAU, new Date(), null, "NHAP"));

        // Don da huy
        fakeDonHangDAO.themDonHang(new DonHang("DH-HUY", "SITE-TW01", "Taiwan Components Co.",
                1, Site.PT_TAU, new Date(), null, DonHang.TRANG_THAI_DA_HUY));

        // DAO mat hang gia: don hop le co 2 mat hang
        FakeMatHangDonHangDAO fakeMatHangDAO = new FakeMatHangDonHangDAO();
        fakeMatHangDAO.dat("DH-VALID", Arrays.asList(
                new MatHangDonHang("MH001", "Linh kien IC-7805", 500, "Cai", Site.PT_TAU),
                new MatHangDonHang("MH002", "Tu dien gom 100uF", 1000, "Cai", Site.PT_TAU)));

        service = new DonHangService(fakeDonHangDAO, fakeMatHangDAO);
    }
    // PHAN 1 - KIEM THU HOP DEN (Black-box)
    // Ky thuat: Equivalence Partitioning (EP) + Boundary Value Analysis (BVA)
    @Test
    public void testLayChiTiet_donHopLe_traVeDTODayDu() {
        ChiTietDonHangDTO dto = service.layChiTiet("DH-VALID");

        assertNotNull("Don hop le phai tra ve DTO khong null", dto);
        assertEquals("Ma don trong DTO phai dung",
                "DH-VALID", dto.getDonHang().getMaDonHang());
        assertEquals("DTO phai chua dung 2 mat hang",
                2, dto.getDsMatHang().size());
        assertNotNull("DTO phai co thong tin Site", dto.getSite());
        assertEquals("So ngay van chuyen (Tau) phai = 30",
                30, dto.getSoNgayVanChuyen());
    }

    @Test
    public void testLayChiTiet_donKhongTonTai_traVeNull() {
        ChiTietDonHangDTO dto = service.layChiTiet("DH-KHONG-CO");
        assertNull("Don khong ton tai phai tra ve null", dto);
    }

    @Test(expected = DonHangDaHuyException.class)
    public void testLayChiTiet_donDaHuy_nemDonHangDaHuyException() {
        service.layChiTiet("DH-HUY");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayChiTiet_maNull_nemIllegalArgumentException() {
        service.layChiTiet(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayChiTiet_maRong_nemIllegalArgumentException() {
        service.layChiTiet("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayChiTiet_maToanKhoangTrang_nemIllegalArgumentException() {
        service.layChiTiet("   ");
    }

    @Test
    public void testLayChiTiet_donDaHuy_exceptionChuaMaDon() {
        try {
            service.layChiTiet("DH-HUY");
            fail("Phai nem DonHangDaHuyException cho don da huy");
        } catch (DonHangDaHuyException ex) {
            assertEquals("Exception phai chua dung ma don da huy",
                    "DH-HUY", ex.getMaDonHang());
        }
    }
    // PHAN 2 - KIEM THU HOP TRANG (White-box), do bao phu nhanh C1
    // Module co 3 diem quyet dinh (nhanh):
    //   B1: if (maDonHang == null || maDonHang.trim().isEmpty())
    //   B2: if (donHang == null)
    //   B3: if (donHang.laDaHuy())
    // Bang bao phu C1 (T = re nhanh dung, F = re nhanh sai):
    // => 4 test phu het ca 2 chieu cua moi nhanh => C1 = 100%.
    @Test(expected = IllegalArgumentException.class)
    public void testWB01_nhanhB1True_maRong() {
        service.layChiTiet("");
    }

    @Test
    public void testWB02_nhanhB2True_khongTimThay() {
        assertNull("B2=TRUE phai tra ve null", service.layChiTiet("DH-KHONG-CO"));
    }

    @Test(expected = DonHangDaHuyException.class)
    public void testWB03_nhanhB3True_donDaHuy() {
        service.layChiTiet("DH-HUY");
    }

    @Test
    public void testWB04_taatCaNhanhFalse_donHopLe() {
        assertNotNull("Tat ca nhanh FALSE phai tra ve DTO", service.layChiTiet("DH-VALID"));
    }
    private static class FakeDonHangDAO implements IDonHangDAO {
        private final Map<String, DonHang> dsDonHang = new HashMap<>();
        private final Map<String, Site> dsSite = new HashMap<>();

        void themDonHang(DonHang dh) {
            dsDonHang.put(dh.getMaDonHang(), dh);
        }

        void themSite(Site site) {
            dsSite.put(site.getMaSite(), site);
        }

        @Override
        public List<DonHang> findAll(int page, int pageSize) {
            return new ArrayList<>(dsDonHang.values());
        }

        @Override
        public DonHang findByCode(String maDonHang) {
            return dsDonHang.get(maDonHang);
        }

        @Override
        public List<DonHang> findByFilters(String keyword, String trangThai, String phuongTienVT) {
            return new ArrayList<>(dsDonHang.values());
        }

        @Override
        public int countAll() {
            return dsDonHang.size();
        }

        @Override
        public Site findSiteByCode(String maSite) {
            return dsSite.get(maSite);
        }
    }

    private static class FakeMatHangDonHangDAO extends MatHangDonHangDAO {
        private final Map<String, List<MatHangDonHang>> data = new HashMap<>();

        void dat(String maDonHang, List<MatHangDonHang> ds) {
            data.put(maDonHang, new ArrayList<>(ds));
        }

        @Override
        public List<MatHangDonHang> findByOrderCode(String maDonHang) {
            List<MatHangDonHang> ds = data.get(maDonHang);
            return (ds == null) ? new ArrayList<>() : new ArrayList<>(ds);
        }
    }
}
