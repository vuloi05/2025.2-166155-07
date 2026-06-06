// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businesslogic.ChiTietDonHangDTO;
import businesslogic.DonHangController;
import businesslogic.DonHangDaHuyException;
import domainmodel.DonHang;
import domainmodel.Site;

public class DonHangListView extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final String[] NHAN_TRANG_THAI =
            {"Tất cả trạng thái", "Nháp", "Đang xử lý", "Đã xử lý", "Đã hủy"};
    private static final String[] MA_TRANG_THAI =
            {"", "NHAP", "DANG_XU_LY", "DA_GUI", DonHang.TRANG_THAI_DA_HUY};

    private static final String[] NHAN_PHUONG_TIEN =
            {"Tất cả phương tiện", "Tàu", "Hàng không"};
    private static final String[] MA_PHUONG_TIEN =
            {"", Site.PT_TAU, Site.PT_HANG_KHONG};

    private static final String CARD_BANG = "BANG";
    private static final String CARD_RONG = "RONG";

    private final transient DonHangNavigation navigation;
    private final transient DonHangController controller;
    private final transient Frame chuSoHuu;
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

    public DonHangListView(DonHangNavigation navigation, DonHangController controller) {
        this.navigation = navigation;
        this.controller = controller;
        this.chuSoHuu = (navigation instanceof Frame) ? (Frame) navigation : null;
        this.chiTietView = new ChiTietDonHangView(chuSoHuu, controller);
        this.canhBaoHuyDialog = new CanhBaoHuyDialog(chuSoHuu, controller);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

    private JPanel taoNoiDung() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(Color.WHITE);
        top.add(GroupUiTheme.createBackButton("← Về trang chủ", navigation::showTrangChu));
        top.add(Box.createVerticalStrut(12));
        top.add(taoThanhCongCu());
        top.add(Box.createVerticalStrut(12));

        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(taoTrungTam(), BorderLayout.CENTER);
        wrapper.add(taoChanTrang(), BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel taoThanhCongCu() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        toolbar.setBackground(GroupUiTheme.CARD_COLOR);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR),
                new EmptyBorder(8, 12, 8, 12)));

        JButton btnDashboard = GroupUiTheme.createStyledButton(
                "← Quay lại Dashboard", GroupUiTheme.TEXT_PRIMARY, GroupUiTheme.CARD_COLOR);
        btnDashboard.addActionListener(e -> navigation.showDashboard());
        toolbar.add(btnDashboard);

        toolbar.add(new JLabel("Từ khóa:"));
        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(GroupUiTheme.FONT_BODY);
        toolbar.add(txtTimKiem);

        cboTrangThai = new JComboBox<>(NHAN_TRANG_THAI);
        cboTrangThai.setFont(GroupUiTheme.FONT_BODY);
        toolbar.add(cboTrangThai);

        cboPhuongTien = new JComboBox<>(NHAN_PHUONG_TIEN);
        cboPhuongTien.setFont(GroupUiTheme.FONT_BODY);
        toolbar.add(cboPhuongTien);

        JButton btnTimKiem = GroupUiTheme.createPrimaryButton("🔍 Tìm kiếm");
        btnTimKiem.addActionListener(e -> thucHienTimKiem());
        toolbar.add(btnTimKiem);

        JButton btnLamMoi = GroupUiTheme.createStyledButton(
                "Làm mới", GroupUiTheme.TEXT_PRIMARY, GroupUiTheme.CARD_COLOR);
        btnLamMoi.addActionListener(e -> lamMoi());
        toolbar.add(btnLamMoi);

        return toolbar;
    }

    private JPanel taoTrungTam() {
        cardTrungTam = new CardLayout();
        panelTrungTam = new JPanel(cardTrungTam);
        panelTrungTam.setBackground(Color.WHITE);

        modelDonHang = new DefaultTableModel(
                new String[] {"Mã đơn hàng", "Mã Site", "Tên Site", "Số mặt hàng",
                        "Phương tiện VT", "Ngày tạo", "Trạng thái", "Thao tác"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bangDonHang = GroupUiTheme.createStyledTable(modelDonHang);
        ganSuKienBang();

        JScrollPane scroll = new JScrollPane(bangDonHang);
        scroll.setBorder(BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR));
        scroll.getViewport().setBackground(GroupUiTheme.CARD_COLOR);
        panelTrungTam.add(scroll, CARD_BANG);
        panelTrungTam.add(taoPanelRong(), CARD_RONG);

        return panelTrungTam;
    }

    private JPanel taoPanelRong() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GroupUiTheme.CARD_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR));
        panel.setPreferredSize(new Dimension(0, 320));

        JPanel giua = new JPanel();
        giua.setLayout(new BoxLayout(giua, BoxLayout.Y_AXIS));
        giua.setBackground(GroupUiTheme.CARD_COLOR);
        giua.setBorder(new EmptyBorder(60, 24, 60, 24));

        JLabel lblIcon = new JLabel("\u25A1", SwingConstants.CENTER);
        lblIcon.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 48));
        lblIcon.setForeground(GroupUiTheme.TEXT_SECONDARY);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Không tìm thấy đơn hàng nào");
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitle.setForeground(GroupUiTheme.TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Vui lòng kiểm tra lại từ khóa hoặc bộ lọc.");
        lblSub.setFont(GroupUiTheme.FONT_BODY);
        lblSub.setForeground(GroupUiTheme.TEXT_SECONDARY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLamMoi = GroupUiTheme.createPrimaryButton("Làm mới");
        btnLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLamMoi.addActionListener(e -> lamMoi());

        giua.add(lblIcon);
        giua.add(Box.createVerticalStrut(12));
        giua.add(lblTitle);
        giua.add(Box.createVerticalStrut(6));
        giua.add(lblSub);
        giua.add(Box.createVerticalStrut(16));
        giua.add(btnLamMoi);

        panel.add(giua, BorderLayout.CENTER);
        return panel;
    }

    private JPanel taoChanTrang() {
        JPanel chan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        chan.setBackground(Color.WHITE);
        JButton btnTruoc = GroupUiTheme.createStyledButton(
                "< Trước", GroupUiTheme.TEXT_SECONDARY, GroupUiTheme.CARD_COLOR);
        btnTruoc.setEnabled(false);
        JLabel lblTrang = new JLabel("Trang 1");
        lblTrang.setFont(GroupUiTheme.FONT_BODY);
        lblTrang.setForeground(GroupUiTheme.TEXT_PRIMARY);
        JButton btnSau = GroupUiTheme.createStyledButton(
                "Sau >", GroupUiTheme.TEXT_SECONDARY, GroupUiTheme.CARD_COLOR);
        btnSau.setEnabled(false);

        chan.add(btnTruoc);
        chan.add(lblTrang);
        chan.add(btnSau);
        return chan;
    }

    public void lamMoi() {
        txtTimKiem.setText("");
        cboTrangThai.setSelectedIndex(0);
        cboPhuongTien.setSelectedIndex(0);
        hienThiDanhSachDonHang(controller.yeuCauDSDonHang());
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

    private void thucHienTimKiem() {
        String tuKhoa = txtTimKiem.getText();
        String trangThai = MA_TRANG_THAI[cboTrangThai.getSelectedIndex()];
        String phuongTien = MA_PHUONG_TIEN[cboPhuongTien.getSelectedIndex()];
        hienThiKetQuaTimKiem(controller.traCuuDonHang(tuKhoa, trangThai, phuongTien));
    }

    private void xemChiTiet(String maDonHang) {
        try {
            ChiTietDonHangDTO chiTiet = controller.yeuCauChiTietDonHang(maDonHang);
            if (chiTiet == null) {
                javax.swing.JOptionPane.showMessageDialog(chuSoHuu,
                        "Không tìm thấy đơn hàng: " + maDonHang, "Thông báo",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
                    UiLabels.phuongTien(dh.getPhuongTienVC()),
                    dinhDangNgay.format(dh.getNgayTao()),
                    UiLabels.trangThai(dh.getTrangThai()),
                    "Xem"
            });
        }
    }

    private void ganSuKienBang() {
        bangDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = bangDonHang.rowAtPoint(e.getPoint());
                int col = bangDonHang.columnAtPoint(e.getPoint());
                if (row < 0) {
                    return;
                }
                boolean clickThaoTac = (col == 7);
                boolean clickDoubleRow = (e.getClickCount() == 2);
                if (clickThaoTac || clickDoubleRow) {
                    String maDonHang = String.valueOf(bangDonHang.getValueAt(row, 0));
                    xemChiTiet(maDonHang);
                }
            }
        });
    }
}
