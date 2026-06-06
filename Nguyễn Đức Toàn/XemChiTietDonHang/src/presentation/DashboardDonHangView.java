// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businesslogic.DonHangController;
import domainmodel.DonHang;
import domainmodel.Site;

public class DashboardDonHangView extends JPanel {

    private static final long serialVersionUID = 1L;

    private final transient DonHangNavigation navigation;
    private final transient DonHangController controller;
    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private JLabel lblTong;
    private JLabel lblDaXuLy;
    private JLabel lblDangXuLy;
    private JLabel lblYeuCauMoi;
    private DefaultTableModel modelGanDay;

    public DashboardDonHangView(DonHangNavigation navigation, DonHangController controller) {
        this.navigation = navigation;
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(taoNoiDung(), BorderLayout.CENTER);
        lamMoi();
    }

    private JPanel taoNoiDung() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(16, 24, 24, 24));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(Color.WHITE);
        top.setAlignmentX(LEFT_ALIGNMENT);

        top.add(GroupUiTheme.createBackButton("← Về trang chủ", navigation::showTrangChu));
        top.add(Box.createVerticalStrut(16));

        JPanel titleRow = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        titleRow.setBackground(Color.WHITE);
        titleRow.add(new JLabel("📋"));
        titleRow.add(GroupUiTheme.createPageTitle("DASHBOARD QUẢN LÝ ĐƠN HÀNG"));
        top.add(titleRow);
        top.add(Box.createVerticalStrut(20));

        top.add(taoHangThongKe());
        top.add(Box.createVerticalStrut(16));

        JButton btnXemDS = GroupUiTheme.createPrimaryButton("Xem danh sách đơn hàng");
        btnXemDS.addActionListener(e -> navigation.showDanhSach());
        JPanel btnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnXemDS);
        top.add(btnPanel);
        top.add(Box.createVerticalStrut(20));

        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(taoBangGanDay(), BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel taoHangThongKe() {
        JPanel hang = new JPanel(new GridLayout(1, 4, 16, 0));
        hang.setBackground(Color.WHITE);
        hang.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 90));

        lblTong = new JLabel("0");
        lblDaXuLy = new JLabel("0");
        lblDangXuLy = new JLabel("0");
        lblYeuCauMoi = new JLabel("0");

        hang.add(taoCardThongKe("Tổng đơn hàng", lblTong, GroupUiTheme.PRIMARY_COLOR));
        hang.add(taoCardThongKe("Đã xử lý", lblDaXuLy, GroupUiTheme.ACCENT_SENT));
        hang.add(taoCardThongKe("Đang xử lý", lblDangXuLy, GroupUiTheme.ACCENT_PROCESS));
        hang.add(taoCardThongKe("Yêu cầu mới", lblYeuCauMoi, GroupUiTheme.ACCENT_NEW));
        return hang;
    }

    private JPanel taoCardThongKe(String nhan, JLabel lblSo, Color mau) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(GroupUiTheme.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, mau),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR),
                        new EmptyBorder(14, 16, 14, 16))));

        JLabel lblNhan = new JLabel(nhan);
        lblNhan.setFont(GroupUiTheme.FONT_BODY);
        lblNhan.setForeground(GroupUiTheme.TEXT_SECONDARY);
        lblSo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 30));
        lblSo.setForeground(mau);

        card.add(lblNhan, BorderLayout.NORTH);
        card.add(lblSo, BorderLayout.CENTER);
        return card;
    }

    private JPanel taoBangGanDay() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(GroupUiTheme.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR),
                new EmptyBorder(12, 16, 12, 16)));

        JPanel titleRow = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        titleRow.setBackground(GroupUiTheme.CARD_COLOR);
        titleRow.add(new JLabel("📋"));
        JLabel lbl = new JLabel("Đơn hàng gần đây");
        lbl.setFont(GroupUiTheme.FONT_SUBTITLE);
        lbl.setForeground(GroupUiTheme.PRIMARY_COLOR);
        titleRow.add(lbl);
        card.add(titleRow, BorderLayout.NORTH);

        modelGanDay = new DefaultTableModel(
                new String[] {"Mã đơn hàng", "Tên Site", "Phương tiện", "Ngày tạo", "Trạng thái"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable bang = GroupUiTheme.createStyledTable(modelGanDay);
        JScrollPane scroll = new JScrollPane(bang);
        scroll.setBorder(BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR));
        scroll.getViewport().setBackground(GroupUiTheme.CARD_COLOR);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    public void lamMoi() {
        List<DonHang> tatCa = controller.traCuuDonHang("", "", "");
        int tong = (tatCa == null) ? 0 : tatCa.size();
        lblTong.setText(String.valueOf(tong));
        lblDaXuLy.setText(String.valueOf(demTheoTrangThai(tatCa, "DA_GUI")));
        lblDangXuLy.setText(String.valueOf(demTheoTrangThai(tatCa, "DANG_XU_LY")));
        lblYeuCauMoi.setText(String.valueOf(demTheoTrangThai(tatCa, "NHAP")));

        modelGanDay.setRowCount(0);
        List<DonHang> ds = controller.yeuCauDSDonHang();
        if (ds == null) {
            return;
        }
        for (DonHang dh : ds) {
            modelGanDay.addRow(new Object[] {
                    dh.getMaDonHang(),
                    dh.getTenSite(),
                    UiLabels.phuongTien(dh.getPhuongTienVC()),
                    dinhDangNgay.format(dh.getNgayTao()),
                    UiLabels.trangThai(dh.getTrangThai())
            });
        }
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
}
