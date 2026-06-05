// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import businesslogic.ChiTietDonHangDTO;
import businesslogic.DonHangController;
import businesslogic.DonHangDaHuyException;
import domainmodel.DonHang;
import domainmodel.Site;

public class DonHangListView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY_COLOR = new Color(26, 35, 126);
    private static final Color BG_COLOR = new Color(245, 247, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TABLE_HEADER_BG = new Color(52, 58, 64);
    private static final Color TABLE_STRIPE = new Color(248, 249, 250);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color BREADCRUMB_LINK = new Color(173, 216, 255);
    private static final Color BREADCRUMB_CURRENT = new Color(200, 210, 240);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private static final String[] NHAN_TRANG_THAI =
            {"Tat ca trang thai", "Nhap", "Dang xu ly", "Da gui", "Da huy"};
    private static final String[] MA_TRANG_THAI =
            {"", "NHAP", "DANG_XU_LY", "DA_GUI", DonHang.TRANG_THAI_DA_HUY};

    private static final String[] NHAN_PHUONG_TIEN =
            {"Tat ca phuong tien", "Tau", "Hang khong"};
    private static final String[] MA_PHUONG_TIEN =
            {"", Site.PT_TAU, Site.PT_HANG_KHONG};

    private static final String CARD_BANG = "BANG";
    private static final String CARD_RONG = "RONG";

    private final transient DonHangController controller;
    private final transient Frame trangChu;
    private final ChiTietDonHangView chiTietView;
    private final CanhBaoHuyDialog canhBaoHuyDialog;

    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JComboBox<String> cboPhuongTien;
    private JTable bangDonHang;
    private DefaultTableModel modelDonHang;
    private CardLayout cardTrungTam;
    private JPanel panelTrungTam;
    private JLabel lblTuKhoaRong;

    public DonHangListView() {
        this(null, new DonHangController());
    }

    public DonHangListView(DonHangController controller) {
        this(null, controller);
    }

    public DonHangListView(Frame trangChu, DonHangController controller) {
        this.trangChu = trangChu;
        this.controller = controller;
        this.chiTietView = new ChiTietDonHangView(this, controller);
        this.canhBaoHuyDialog = new CanhBaoHuyDialog(this, controller);
        khoiTaoGiaoDien();
        hienThiDanhSachDonHang(controller.yeuCauDSDonHang());
    }

    private void khoiTaoGiaoDien() {
        setTitle("He thong dat hang nhap khau - Danh sach don hang");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                focusTrangChu();
            }
        });
        setSize(1100, 680);
        setMinimumSize(new Dimension(960, 560));
        setLocationRelativeTo(null);

        JPanel goc = new JPanel(new BorderLayout());
        goc.setBackground(BG_COLOR);

        goc.add(taoHeader(), BorderLayout.NORTH);

        JPanel than = new JPanel(new BorderLayout());
        than.setBackground(BG_COLOR);
        than.setBorder(new EmptyBorder(16, 24, 16, 24));
        than.add(taoThanhCongCu(), BorderLayout.NORTH);
        than.add(taoTrungTam(), BorderLayout.CENTER);
        than.add(taoChanTrang(), BorderLayout.SOUTH);

        goc.add(than, BorderLayout.CENTER);
        add(goc);
    }

    private JPanel taoHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel headerTrai = new JPanel();
        headerTrai.setLayout(new javax.swing.BoxLayout(headerTrai, javax.swing.BoxLayout.Y_AXIS));
        headerTrai.setOpaque(false);

        JLabel lblTitle = new JLabel("DANH SACH DON HANG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerTrai.add(lblTitle);
        headerTrai.add(javax.swing.Box.createVerticalStrut(6));
        headerTrai.add(taoBreadcrumb());
        header.add(headerTrai, BorderLayout.WEST);

        JLabel lblUser = new JLabel("NV Dat hang quoc te");
        lblUser.setFont(FONT_BODY);
        lblUser.setForeground(new Color(200, 210, 240));
        header.add(lblUser, BorderLayout.EAST);
        return header;
    }

    private JPanel taoBreadcrumb() {
        JPanel breadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        breadcrumb.setOpaque(false);
        breadcrumb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel linkTrangChu = new JLabel("Trang chu");
        linkTrangChu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        linkTrangChu.setForeground(BREADCRUMB_LINK);
        linkTrangChu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkTrangChu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                quayVeTrangChu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkTrangChu.setText("<html><u>Trang chu</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkTrangChu.setText("Trang chu");
            }
        });
        breadcrumb.add(linkTrangChu);

        JLabel lblSeparator = new JLabel("  >  ");
        lblSeparator.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSeparator.setForeground(BREADCRUMB_CURRENT);
        breadcrumb.add(lblSeparator);

        JLabel lblHienTai = new JLabel("Danh sach don hang");
        lblHienTai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblHienTai.setForeground(BREADCRUMB_CURRENT);
        breadcrumb.add(lblHienTai);
        return breadcrumb;
    }

    private void quayVeTrangChu() {
        dispose();
    }

    private void focusTrangChu() {
        if (trangChu != null) {
            trangChu.setVisible(true);
            trangChu.toFront();
            trangChu.requestFocus();
        }
    }

    private JPanel taoThanhCongCu() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(CARD_COLOR);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                new EmptyBorder(8, 12, 8, 12)));

        txtTimKiem = new JTextField(24);
        txtTimKiem.setFont(FONT_BODY);
        txtTimKiem.setToolTipText("Nhap ma don hang, ma Site hoac tu khoa...");
        toolbar.add(new JLabel("Tu khoa:"));
        toolbar.add(txtTimKiem);

        cboTrangThai = new JComboBox<>(NHAN_TRANG_THAI);
        cboTrangThai.setFont(FONT_BODY);
        toolbar.add(cboTrangThai);

        cboPhuongTien = new JComboBox<>(NHAN_PHUONG_TIEN);
        cboPhuongTien.setFont(FONT_BODY);
        toolbar.add(cboPhuongTien);

        JButton btnTimKiem = taoNut("Tim kiem", PRIMARY_COLOR, Color.WHITE);
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        toolbar.add(btnTimKiem);

        JButton btnDatLai = taoNut("Dat lai", CARD_COLOR, TEXT_PRIMARY);
        btnDatLai.addActionListener(e -> datLai());
        toolbar.add(btnDatLai);

        return toolbar;
    }

    private JPanel taoTrungTam() {
        cardTrungTam = new CardLayout();
        panelTrungTam = new JPanel(cardTrungTam);
        panelTrungTam.setBackground(BG_COLOR);

        // Card bang du lieu
        modelDonHang = new DefaultTableModel(
                new String[] {"Ma don hang", "Ma Site", "Ten Site", "So mat hang",
                        "Phuong tien VT", "Ngay tao", "Trang thai", "Thao tac"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bangDonHang = taoBangDonHang(modelDonHang);
        JScrollPane scroll = new JScrollPane(bangDonHang);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        scroll.getViewport().setBackground(CARD_COLOR);
        panelTrungTam.add(scroll, CARD_BANG);
        panelTrungTam.add(taoPanelRong(), CARD_RONG);

        return panelTrungTam;
    }

    private JPanel taoPanelRong() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        JPanel giua = new JPanel();
        giua.setLayout(new javax.swing.BoxLayout(giua, javax.swing.BoxLayout.Y_AXIS));
        giua.setBackground(CARD_COLOR);

        JLabel lblIcon = new JLabel("\uD83D\uDD0D", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 56));
        lblIcon.setForeground(TEXT_SECONDARY);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Khong tim thay don hang nao");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTuKhoaRong = new JLabel("Vui long kiem tra lai tu khoa hoac bo loc.");
        lblTuKhoaRong.setFont(FONT_BODY);
        lblTuKhoaRong.setForeground(TEXT_SECONDARY);
        lblTuKhoaRong.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnDatLai = taoNut("Dat lai", PRIMARY_COLOR, Color.WHITE);
        btnDatLai.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDatLai.addActionListener(e -> datLai());

        giua.add(lblIcon);
        giua.add(javax.swing.Box.createVerticalStrut(12));
        giua.add(lblTitle);
        giua.add(javax.swing.Box.createVerticalStrut(6));
        giua.add(lblTuKhoaRong);
        giua.add(javax.swing.Box.createVerticalStrut(16));
        giua.add(btnDatLai);

        panel.add(giua, BorderLayout.CENTER);
        return panel;
    }

    private JPanel taoChanTrang() {
        JPanel chan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        chan.setBackground(BG_COLOR);
        JButton btnTruoc = taoNut("< Truoc", CARD_COLOR, TEXT_SECONDARY);
        btnTruoc.setEnabled(false);
        JLabel lblTrang = new JLabel("Trang 1");
        lblTrang.setFont(FONT_BODY);
        lblTrang.setForeground(TEXT_PRIMARY);
        JButton btnSau = taoNut("Sau >", CARD_COLOR, TEXT_SECONDARY);
        btnSau.setEnabled(false);

        chan.add(btnTruoc);
        chan.add(lblTrang);
        chan.add(btnSau);
        return chan;
    }
    public void hienThiDanhSachDonHang(List<DonHang> dsDonHang) {
        doDuLieuVaoBang(dsDonHang);
        cardTrungTam.show(panelTrungTam, CARD_BANG);
    }

    public void hienThiKetQuaTimKiem(List<DonHang> dsKetQua) {
        if (dsKetQua == null || dsKetQua.isEmpty()) {
            cardTrungTam.show(panelTrungTam, CARD_RONG);
            return;
        }
        doDuLieuVaoBang(dsKetQua);
        cardTrungTam.show(panelTrungTam, CARD_BANG);
    }

    public void hienThiThongBao(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }
    // XU LY SU KIEN
    private void thucHienTimKiem() {
        String tuKhoa = txtTimKiem.getText();
        String trangThai = MA_TRANG_THAI[cboTrangThai.getSelectedIndex()];
        String phuongTien = MA_PHUONG_TIEN[cboPhuongTien.getSelectedIndex()];
        hienThiKetQuaTimKiem(controller.traCuuDonHang(tuKhoa, trangThai, phuongTien));
    }

    private void datLai() {
        txtTimKiem.setText("");
        cboTrangThai.setSelectedIndex(0);
        cboPhuongTien.setSelectedIndex(0);
        hienThiDanhSachDonHang(controller.yeuCauDSDonHang());
    }

    private void xemChiTiet(String maDonHang) {
        try {
            ChiTietDonHangDTO chiTiet = controller.yeuCauChiTietDonHang(maDonHang);
            if (chiTiet == null) {
                hienThiThongBao("Khong tim thay don hang: " + maDonHang);
            } else {
                chiTietView.hienThiChiTietDonHang(chiTiet);
            }
        } catch (DonHangDaHuyException ex) {
            canhBaoHuyDialog.hienThiCanhBaoDaHuy(maDonHang);
        }
    }

    private void doDuLieuVaoBang(List<DonHang> dsDonHang) {
        modelDonHang.setRowCount(0);
        if (dsDonHang == null) {
            return;
        }
        for (DonHang dh : dsDonHang) {
            modelDonHang.addRow(new Object[] {
                    dh.getMaDonHang(),
                    dh.getMaSite(),
                    dh.getTenSite(),
                    dh.getSoLuongMatHang(),
                    nhanPhuongTien(dh.getPhuongTienVC()),
                    dinhDangNgay.format(dh.getNgayTao()),
                    nhanTrangThai(dh.getTrangThai()),
                    "Xem"
            });
        }
    }

    private JTable taoBangDonHang(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setGridColor(new Color(233, 236, 239));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(206, 224, 255));
        table.setSelectionForeground(TEXT_PRIMARY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 36));

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : TABLE_STRIPE);
                }
                return c;
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row < 0) {
                    return;
                }
                boolean clickThaoTac = (col == 7);
                boolean clickDoubleRow = (e.getClickCount() == 2);
                if (clickThaoTac || clickDoubleRow) {
                    String maDonHang = String.valueOf(table.getValueAt(row, 0));
                    xemChiTiet(maDonHang);
                }
            }
        });

        return table;
    }

    private JButton taoNut(String chu, Color mauNen, Color mauChu) {
        JButton btn = new JButton(chu);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(mauChu);
        btn.setBackground(mauNen);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(7, 16, 7, 16)));
        return btn;
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
