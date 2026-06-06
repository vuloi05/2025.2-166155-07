// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import businesslogic.ChiTietDonHangDTO;
import businesslogic.DonHangController;
import domainmodel.DonHang;
import domainmodel.MatHangDonHang;
import domainmodel.Site;

public class ChiTietDonHangView extends JDialog {

    private static final long serialVersionUID = 1L;

    private static final String GIA_TRI_TRONG = "\u2014";

    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private final transient DonHangController controller;
    private final JPanel panelThongTinChung;
    private final DefaultTableModel modelMatHang;

    public ChiTietDonHangView(Frame chuSoHuu, DonHangController controller) {
        super(chuSoHuu, true);
        this.controller = controller;

        this.panelThongTinChung = new JPanel(new GridLayout(0, 2, 12, 8));
        this.modelMatHang = new DefaultTableModel(
                new String[] {"STT", "Mã hàng", "Tên mặt hàng", "Số lượng đặt", "Đơn vị", "Phương tiện VT"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setUndecorated(false);
        setSize(860, 620);
        setLocationRelativeTo(getOwner());

        JPanel goc = new JPanel(new BorderLayout());
        goc.setBackground(GroupUiTheme.BG_COLOR);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(GroupUiTheme.PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(14, 24, 14, 24));

        JLabel lblTitle = new JLabel("CHI TIẾT ĐƠN HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);

        JButton btnDong = new JButton("✕");
        btnDong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDong.setForeground(Color.WHITE);
        btnDong.setBackground(GroupUiTheme.PRIMARY_COLOR);
        btnDong.setBorderPainted(false);
        btnDong.setFocusPainted(false);
        btnDong.setOpaque(false);
        btnDong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDong.addActionListener(e -> dispose());
        header.add(btnDong, BorderLayout.EAST);
        goc.add(header, BorderLayout.NORTH);

        JPanel noiDung = new JPanel(new BorderLayout(0, 14));
        noiDung.setBackground(GroupUiTheme.BG_COLOR);
        noiDung.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel cardChung = taoCard("Thông tin chung");
        panelThongTinChung.setBackground(GroupUiTheme.CARD_COLOR);
        cardChung.add(panelThongTinChung, BorderLayout.CENTER);
        noiDung.add(cardChung, BorderLayout.NORTH);

        JPanel cardMatHang = taoCard("Danh sách mặt hàng");
        JTable bangMatHang = GroupUiTheme.createStyledTable(modelMatHang);
        JScrollPane scroll = new JScrollPane(bangMatHang);
        scroll.setBorder(BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR));
        scroll.getViewport().setBackground(GroupUiTheme.CARD_COLOR);
        cardMatHang.add(scroll, BorderLayout.CENTER);
        noiDung.add(cardMatHang, BorderLayout.CENTER);

        goc.add(noiDung, BorderLayout.CENTER);

        JPanel chanTrang = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        chanTrang.setBackground(GroupUiTheme.BG_COLOR);
        chanTrang.setBorder(new EmptyBorder(0, 24, 16, 24));
        JButton btnQuayLai = GroupUiTheme.createPrimaryButton("← Quay lại danh sách");
        btnQuayLai.addActionListener(e -> quayLaiDanhSach());
        chanTrang.add(btnQuayLai);
        goc.add(chanTrang, BorderLayout.SOUTH);

        setContentPane(goc);
    }

    public void hienThiChiTietDonHang(ChiTietDonHangDTO chiTiet) {
        DonHang donHang = chiTiet.getDonHang();
        Site site = chiTiet.getSite();

        panelThongTinChung.removeAll();
        themDongThongTin("Mã đơn hàng:", donHang.getMaDonHang());
        themDongThongTin("Trạng thái:", UiLabels.trangThai(donHang.getTrangThai()));
        themDongThongTin("Mã Site:", donHang.getMaSite());
        themDongThongTin("Tên Site:", site != null ? site.getTenSite() : donHang.getTenSite());
        themDongThongTin("Phương tiện VT:", UiLabels.phuongTien(donHang.getPhuongTienVC()));
        themDongThongTin("Số ngày vận chuyển:", chiTiet.getSoNgayVanChuyen() + " ngày");
        themDongThongTin("Ngày tạo đơn:", dinhDangNgay.format(donHang.getNgayTao()));
        String ngayGui = (donHang.getNgayGui() == null)
                ? GIA_TRI_TRONG
                : dinhDangNgay.format(donHang.getNgayGui());
        themDongThongTin("Ngày gửi đơn:", ngayGui);
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
                    UiLabels.phuongTien(mh.getPhuongTienVC())
            });
        }

        setVisible(true);
    }

    public void quayLaiDanhSach() {
        controller.xacNhanVaQuayLai();
        dispose();
    }

    private void themDongThongTin(String nhan, String giaTri) {
        JLabel lblNhan = new JLabel(nhan);
        lblNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNhan.setForeground(GroupUiTheme.TEXT_SECONDARY);

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGiaTri.setForeground(GroupUiTheme.TEXT_PRIMARY);

        panelThongTinChung.add(lblNhan);
        panelThongTinChung.add(lblGiaTri);
    }

    private JPanel taoCard(String tieuDe) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(GroupUiTheme.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GroupUiTheme.BORDER_COLOR),
                new EmptyBorder(12, 16, 12, 16)));

        JLabel lbl = new JLabel(tieuDe);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(GroupUiTheme.PRIMARY_COLOR);
        card.add(lbl, BorderLayout.NORTH);
        return card;
    }
}
