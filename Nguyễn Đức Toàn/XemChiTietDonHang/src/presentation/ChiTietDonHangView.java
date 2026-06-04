// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : ChiTietDonHangView.java
// Goi        : presentation (tang Presentation)
// Mo ta      : Lop bien (boundary) hien thi Man hinh 3 - Chi tiet don hang
//              (muc 7.8 SRS). Nhan du lieu qua ChiTietDonHangDTO (BT6),
//              khong nhan entity tho. Khop Bieu do lop thiet ke BT6.
// Phu thuoc  : businesslogic.ChiTietDonHangDTO, domainmodel.*, javax.swing
// ============================================================
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import businesslogic.ChiTietDonHangDTO;
import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

/**
 * Man hinh chi tiet don hang (Man hinh 3 - muc 7.8 SRS).
 *
 * <p>Tuong ung class &lt;&lt;boundary&gt;&gt; ChiTietDonHangView trong Bieu do lop BT6:</p>
 * <ul>
 *   <li>hienThiChiTietDonHang(chiTiet: ChiTietDonHangDTO): void</li>
 *   <li>quayLaiDanhSach(): void</li>
 * </ul>
 */
public class ChiTietDonHangView extends JDialog {

    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY_COLOR = new Color(26, 35, 126);
    private static final Color BG_COLOR = new Color(245, 247, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TABLE_HEADER_BG = new Color(52, 58, 64);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_VALUE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    /** Hien thi khi khong co du lieu (vd: ngay gui don) - muc 7.11 SRS. */
    private static final String GIA_TRI_TRONG = "\u2014";

    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private final JPanel panelThongTinChung;
    private final JTable bangMatHang;
    private final DefaultTableModel modelMatHang;

    public ChiTietDonHangView(Frame chuSoHuu) {
        super(chuSoHuu, "Chi tiet don hang", true);

        this.panelThongTinChung = new JPanel(new GridLayout(0, 2, 12, 8));
        this.modelMatHang = new DefaultTableModel(
                new String[] {"STT", "Ma hang", "Ten mat hang", "So luong dat", "Don vi", "Phuong tien VT"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.bangMatHang = taoBangMatHang(modelMatHang);

        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setSize(820, 600);
        setLocationRelativeTo(getOwner());

        JPanel goc = new JPanel(new BorderLayout());
        goc.setBackground(BG_COLOR);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(14, 24, 14, 24));
        JLabel lblTitle = new JLabel("CHI TIET DON HANG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);
        goc.add(header, BorderLayout.NORTH);

        // Noi dung
        JPanel noiDung = new JPanel(new BorderLayout(0, 14));
        noiDung.setBackground(BG_COLOR);
        noiDung.setBorder(new EmptyBorder(16, 24, 16, 24));

        // Card thong tin chung
        JPanel cardChung = taoCard("Thong tin chung");
        panelThongTinChung.setBackground(CARD_COLOR);
        cardChung.add(panelThongTinChung, BorderLayout.CENTER);
        noiDung.add(cardChung, BorderLayout.NORTH);

        // Card danh sach mat hang
        JPanel cardMatHang = taoCard("Danh sach mat hang");
        JScrollPane scroll = new JScrollPane(bangMatHang);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        scroll.getViewport().setBackground(CARD_COLOR);
        cardMatHang.add(scroll, BorderLayout.CENTER);
        noiDung.add(cardMatHang, BorderLayout.CENTER);

        goc.add(noiDung, BorderLayout.CENTER);

        // Nut quay lai (muc 7.8 SRS)
        JPanel chanTrang = new JPanel(new BorderLayout());
        chanTrang.setBackground(BG_COLOR);
        chanTrang.setBorder(new EmptyBorder(0, 24, 16, 24));
        JButton btnQuayLai = new JButton("\u2190 Quay lai danh sach");
        btnQuayLai.setFont(FONT_BUTTON);
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setBackground(PRIMARY_COLOR);
        btnQuayLai.setOpaque(true);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setBorder(new EmptyBorder(8, 20, 8, 20));
        btnQuayLai.addActionListener(e -> quayLaiDanhSach());
        chanTrang.add(btnQuayLai, BorderLayout.WEST);
        goc.add(chanTrang, BorderLayout.SOUTH);

        add(goc);
    }

    /**
     * Hien thi chi tiet don hang tu DTO (Man hinh 3).
     *
     * <p>Tuong ung hienThiChiTietDonHang(chiTiet: ChiTietDonHangDTO): void
     * trong Bieu do lop BT6.</p>
     *
     * @param chiTiet DTO chi tiet don hang (khong null)
     */
    public void hienThiChiTietDonHang(ChiTietDonHangDTO chiTiet) {
        DonHang donHang = chiTiet.getDonHang();
        Site site = chiTiet.getSite();

        panelThongTinChung.removeAll();
        themDongThongTin("Ma don hang:", donHang.getMaDonHang());
        themDongThongTin("Trang thai:", nhanTrangThai(donHang.getTrangThai()));
        themDongThongTin("Ma Site:", donHang.getMaSite());
        themDongThongTin("Ten Site:", site != null ? site.getTenSite() : donHang.getTenSite());
        themDongThongTin("Phuong tien VT:", nhanPhuongTien(donHang.getPhuongTienVC()));
        themDongThongTin("So ngay van chuyen:", chiTiet.getSoNgayVanChuyen() + " ngay");
        themDongThongTin("Ngay tao don:", dinhDangNgay.format(donHang.getNgayTao()));
        // Ngay gui don: hien "—" neu chua gui (Nhap / Dang xu ly) - muc 7.11 SRS
        String ngayGui = (donHang.getNgayGui() == null)
                ? GIA_TRI_TRONG
                : dinhDangNgay.format(donHang.getNgayGui());
        themDongThongTin("Ngay gui don:", ngayGui);
        panelThongTinChung.revalidate();
        panelThongTinChung.repaint();

        modelMatHang.setRowCount(0);
        List<MatHangDonHang> dsMatHang = chiTiet.getDsMatHang();
        int stt = 1;
        for (MatHangDonHang mh : dsMatHang) {
            modelMatHang.addRow(new Object[] {
                    stt++,
                    mh.getMaMatHang(),
                    mh.getTenMatHang(),
                    mh.getSoLuong(),
                    mh.getDonVi(),
                    nhanPhuongTien(mh.getPhuongTienVC())
            });
        }

        setVisible(true);
    }

    /**
     * Dong man hinh chi tiet, quay ve Man hinh danh sach (luong chinh buoc 9).
     *
     * <p>Tuong ung quayLaiDanhSach(): void trong Bieu do lop BT6.</p>
     */
    public void quayLaiDanhSach() {
        dispose();
    }

    private void themDongThongTin(String nhan, String giaTri) {
        JLabel lblNhan = new JLabel(nhan);
        lblNhan.setFont(FONT_LABEL);
        lblNhan.setForeground(TEXT_SECONDARY);

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(FONT_VALUE);
        lblGiaTri.setForeground(TEXT_PRIMARY);

        panelThongTinChung.add(lblNhan);
        panelThongTinChung.add(lblGiaTri);
    }

    private JPanel taoCard(String tieuDe) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                new EmptyBorder(12, 16, 12, 16)));

        JLabel lbl = new JLabel(tieuDe);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(PRIMARY_COLOR);
        card.add(lbl, BorderLayout.NORTH);
        return card;
    }

    private JTable taoBangMatHang(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_VALUE);
        table.setRowHeight(28);
        table.setGridColor(new Color(233, 236, 239));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 34));
        return table;
    }

    /** Doi ma trang thai sang nhan hien thi (muc 7.11 SRS). */
    private String nhanTrangThai(String maTrangThai) {
        if (maTrangThai == null) {
            return "";
        }
        switch (maTrangThai) {
            case "NHAP":
                return "Nhap";
            case "DANG_XU_LY":
                return "Dang xu ly";
            case "DA_GUI":
                return "Da gui";
            case DonHang.TRANG_THAI_DA_HUY:
                return "Da huy";
            default:
                return maTrangThai;
        }
    }

    /** Doi ma phuong tien sang nhan hien thi (muc 7.11 SRS). */
    private String nhanPhuongTien(String maPhuongTien) {
        if (Site.PT_TAU.equals(maPhuongTien)) {
            return "Tau";
        }
        if (Site.PT_HANG_KHONG.equals(maPhuongTien)) {
            return "Hang khong";
        }
        return maPhuongTien != null ? maPhuongTien : "";
    }
}
