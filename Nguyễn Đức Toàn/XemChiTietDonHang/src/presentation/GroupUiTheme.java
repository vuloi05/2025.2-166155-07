// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class GroupUiTheme {

    public static final Color PRIMARY_COLOR = new Color(41, 98, 255);
    public static final Color ACCENT_SENT = new Color(0, 200, 83);
    public static final Color ACCENT_PROCESS = new Color(255, 152, 0);
    public static final Color ACCENT_NEW = new Color(24, 62, 171);
    public static final Color DANGER_COLOR = new Color(244, 67, 54);
    public static final Color BG_COLOR = new Color(245, 247, 250);
    public static final Color CARD_COLOR = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    public static final Color TABLE_HEADER_BG = new Color(52, 58, 64);
    public static final Color TABLE_STRIPE = new Color(248, 249, 250);
    public static final Color BORDER_COLOR = new Color(222, 226, 230);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    private GroupUiTheme() {
    }

    public static JButton createMenuButton(String title, String subtitle, Color color) {
        JButton btn = new JButton("<html><center><b style='font-size:14px'>" + title
                + "</b><br><span style='font-size:11px; color:#ddd'>" + subtitle + "</span></center></html>");
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(350, 120));

        Color hoverColor = color.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(color);
                }
            }
        });
        return btn;
    }

    public static JButton createStyledButton(String text, Color fgColor, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(fgColor);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        bgColor.equals(CARD_COLOR) ? new Color(206, 212, 218) : bgColor.darker(), 1),
                new EmptyBorder(8, 20, 8, 20)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    public static JButton createBackButton(String text, Runnable action) {
        JButton btn = createStyledButton(text, TEXT_PRIMARY, CARD_COLOR);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_TABLE);
        table.setRowHeight(32);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(233, 236, 239));
        table.setSelectionBackground(new Color(206, 224, 255));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 38));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        return table;
    }

    public static JLabel createPageTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(PRIMARY_COLOR);
        return lbl;
    }
}
