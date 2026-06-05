// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import businesslogic.DonHangController;
import domainmodel.DonHang;
import domainmodel.Site;

public class TrangChuView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY_COLOR = new Color(26, 35, 126);
    private static final Color BG_COLOR = new Color(245, 247, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color SIDEBAR_BG = new Color(33, 41, 92);
    private static final Color TABLE_HEADER_BG = new Color(52, 58, 64);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color ACCENT_SENT = new Color(46, 125, 50);
    private static final Color ACCENT_PROCESS = new Color(230, 126, 0);
    private static final Color ACCENT_NEW = new Color(21, 101, 192);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private final transient DonHangController controller;

    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private DefaultTableModel modelGanDay;

    public TrangChuView() {
        this(new DonHangController());
    }

    public TrangChuView(DonHangController controller) {
        this.controller = controller;
        khoiTaoGiaoDien();
        napDuLieu();
    }

    private void khoiTaoGiaoDien() {
        setTitle("He thong dat hang nhap khau - Trang chu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setMinimumSize(new Dimension(960, 560));
        setLocationRelativeTo(null);

        JPanel goc = new JPanel(new BorderLayout());
        goc.setBackground(BG_COLOR);
        goc.add(taoHeader(), BorderLayout.NORTH);
        goc.add(taoSidebar(), BorderLayout.WEST);
        goc.add(taoTrungTam(), BorderLayout.CENTER);
        add(goc);
    }

    private JPanel taoHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel lblTitle = new JLabel("TRANG CHU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);

        JLabel lblUser = new JLabel("NV Dat hang quoc te");
        lblUser.setFont(FONT_BODY);
        lblUser.setForeground(new Color(200, 210, 240));
        header.add(lblUser, BorderLayout.EAST);
        return header;
    }

    private JPanel taoSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(20, 16, 20, 16));
        sidebar.setPreferredSize(new Dimension(230, 0));

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMenu.setForeground(new Color(150, 160, 210));
        lblMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblMenu);
        sidebar.add(Box.createVerticalStrut(12));

        JButton btnTrangChu = taoNutSidebar("Trang chu", true);
        sidebar.add(btnTrangChu);
        sidebar.add(Box.createVerticalStrut(8));

        JButton btnQuanLy = taoNutSidebar("Quan ly don hang", false);
        btnQuanLy.addActionListener(e -> moDanhSachDonHang());
        sidebar.add(btnQuanLy);

        return sidebar;
    }

    private JButton taoNutSidebar(String chu, boolean active) {
        JButton btn = new JButton(chu);
        btn.setFont(FONT_BUTTON);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setBackground(active ? PRIMARY_COLOR : SIDEBAR_BG);
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));
        return btn;
    }

    private JPanel taoTrungTam() {
        JPanel than = new JPanel(new BorderLayout(0, 16));
        than.setBackground(BG_COLOR);
        than.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel phiaTren = new JPanel(new BorderLayout(0, 16));
        phiaTren.setBackground(BG_COLOR);
        phiaTren.add(taoHangThongKe(), BorderLayout.NORTH);
        phiaTren.add(taoTruyCapNhanh(), BorderLayout.SOUTH);
        than.add(phiaTren, BorderLayout.NORTH);

        than.add(taoBangGanDay(), BorderLayout.CENTER);
        return than;
    }

    private JPanel taoHangThongKe() {
        JPanel hang = new JPanel(new GridLayout(1, 4, 16, 0));
        hang.setBackground(BG_COLOR);

        List<DonHang> tatCa = controller.traCuuDonHang("", "", "");
        int tong = (tatCa == null) ? 0 : tatCa.size();
        int daGui = demTheoTrangThai(tatCa, "DA_GUI");
        int dangXuLy = demTheoTrangThai(tatCa, "DANG_XU_LY");
        int yeuCauMoi = demTheoTrangThai(tatCa, "NHAP");

        hang.add(taoCardThongKe("Tong don hang", tong, PRIMARY_COLOR));
        hang.add(taoCardThongKe("Da gui", daGui, ACCENT_SENT));
        hang.add(taoCardThongKe("Dang xu ly", dangXuLy, ACCENT_PROCESS));
        hang.add(taoCardThongKe("Yeu cau moi", yeuCauMoi, ACCENT_NEW));
        return hang;
    }

    private int demTheoTrangThai(List<DonHang> ds, String trangThai) {
        if (ds == null) {
            return 0;
        }
        int dem = 0;
        for (DonHang dh : ds) {
            if (trangThai.equals(dh.getTrangThai())) {
                dem++;
            }
        }
        return dem;
    }

    private JPanel taoCardThongKe(String nhan, int soLieu, Color mau) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, mau),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(222, 226, 230)),
                        new EmptyBorder(14, 16, 14, 16))));

        JLabel lblNhan = new JLabel(nhan);
        lblNhan.setFont(FONT_BODY);
        lblNhan.setForeground(TEXT_SECONDARY);

        JLabel lblSo = new JLabel(String.valueOf(soLieu));
        lblSo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblSo.setForeground(mau);

        card.add(lblNhan, BorderLayout.NORTH);
        card.add(lblSo, BorderLayout.CENTER);
        return card;
    }

    private JPanel taoTruyCapNhanh() {
        JPanel panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        panel.setBackground(BG_COLOR);

        JButton btnXemDS = new JButton("Xem danh sach don hang");
        btnXemDS.setFont(FONT_BUTTON);
        btnXemDS.setForeground(Color.WHITE);
        btnXemDS.setBackground(PRIMARY_COLOR);
        btnXemDS.setOpaque(true);
        btnXemDS.setFocusPainted(false);
        btnXemDS.setBorder(new EmptyBorder(9, 18, 9, 18));
        btnXemDS.addActionListener(e -> moDanhSachDonHang());
        panel.add(btnXemDS);
        return panel;
    }

    private JPanel taoBangGanDay() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                new EmptyBorder(12, 16, 12, 16)));

        JLabel lbl = new JLabel("Don hang gan day");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(PRIMARY_COLOR);
        card.add(lbl, BorderLayout.NORTH);

        modelGanDay = new DefaultTableModel(
                new String[] {"Ma don hang", "Ten Site", "Phuong tien VT", "Ngay tao", "Trang thai"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable bang = new JTable(modelGanDay);
        bang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bang.setRowHeight(30);
        bang.setGridColor(new Color(233, 236, 239));
        bang.setRowSelectionAllowed(false);
        bang.setColumnSelectionAllowed(false);
        bang.setFocusable(false);

        JTableHeader header = bang.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 34));

        JScrollPane scroll = new JScrollPane(bang);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        scroll.getViewport().setBackground(CARD_COLOR);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private void napDuLieu() {
        modelGanDay.setRowCount(0);
        List<DonHang> ds = controller.yeuCauDSDonHang();
        if (ds == null) {
            return;
        }
        for (DonHang dh : ds) {
            modelGanDay.addRow(new Object[] {
                    dh.getMaDonHang(),
                    dh.getTenSite(),
                    nhanPhuongTien(dh.getPhuongTienVC()),
                    dinhDangNgay.format(dh.getNgayTao()),
                    nhanTrangThai(dh.getTrangThai())
            });
        }
    }

    private void moDanhSachDonHang() {
        DonHangListView dsView = new DonHangListView(this, controller);
        dsView.setVisible(true);
    }

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
