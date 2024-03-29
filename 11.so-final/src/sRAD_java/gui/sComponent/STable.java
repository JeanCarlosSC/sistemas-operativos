package sRAD_java.gui.sComponent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import static sRAD_java.gui.component.Resource.*;
import static sRAD_java.gui.sComponent.SScrollPane.getCustomScroll;

public class STable extends JScrollPane {

    public STable(int x, int y, int width, int height, int cellWidth, ArrayList<ArrayList<String>> matriz) {
        this(x, y, width, height, matriz, 100, 30);
    }

    public STable(int x, int y, int width, int height, ArrayList<ArrayList<String>> matriz, int cellWidth, int cellHeight) {
        setProperties(x, y, width, height, matriz, cellWidth, cellHeight);
    }

    public void setProperties(int x, int y, int width, int height, ArrayList<ArrayList<String>> matriz, int cellWidth, int cellHeight) {
        Vector<String> placeholdes = new Vector<>();
        for(String element: matriz.get(0)) {
            placeholdes.add(element);
        }
        final DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(placeholdes);

        for (int i = 1; i < matriz.size(); i++) {
            modelo.addRow(new Vector<>());

            for (int j=0; j<matriz.get(0).size(); j++) {
                modelo.setValueAt(matriz.get(i).get(j), i-1, j);
            }
        }

        final JTable table = new JTable();
        setProperties(table, modelo, cellHeight);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(cellHeight);
        for (int i=0; i<matriz.get(0).size(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
            table.getColumnModel().getColumn(i).setMaxWidth(cellWidth);
            table.getColumnModel().getColumn(i).setMinWidth(cellWidth);
        }
        table.setSize(cellWidth*matriz.get(0).size(), cellHeight*matriz.size());
        table.setPreferredSize(new Dimension(cellWidth*matriz.get(0).size(), cellHeight*(matriz.size()-1)));

        JTableHeader header = table.getTableHeader();
        header.setBackground(DTII6);
        header.setReorderingAllowed(false);
        header.setSize(cellWidth*matriz.get(0).size(), 30);
        header.setPreferredSize(new Dimension(cellWidth*matriz.get(0).size(), 30));
        header.setDefaultRenderer(getCustomTable(DTII6, null, null, WHITE, fontText1));

        setViewportView(table);
        setLocation(x, y);
        setSize(width, height);

        setBackground(DTII6);
        viewport.setBackground(DTII3);
        setBorder(DTII4Border);
        verticalScrollBar.setUI(getCustomScroll());
        horizontalScrollBar.setUI(getCustomScroll());
    }

    public JScrollPane getPanelBar(JTable table, int x, int y, int width, int height) {
        return getPanelBar(table, x, y, width, height, DTII3, null);
    }

    public JScrollPane getPanelBar(JTable table, int x, int y, int width, int height, Color background, Border border) {
        JScrollPane panelScroll = new JScrollPane(table);
        panelScroll.setLocation(x, y);
        panelScroll.setSize(width, height);
        panelScroll.getViewport().setBackground(background);
        panelScroll.setBorder(border);
        return panelScroll;
    }

    public void setProperties(JTable table, DefaultTableModel modelo) {
        setProperties(table, modelo, DTII6, 40);
    }

    public void setProperties(JTable table, DefaultTableModel modelo, int rowHeight) {
        setProperties(table, modelo, DTII6, rowHeight);
    }

    public void setProperties(JTable table, DefaultTableModel modelo, Color gridColor, int rowHeight) {
        table.setModel(modelo);
        table.setRowHeight(rowHeight);
        table.setDefaultRenderer(Object.class, getCustomTable());
        table.setGridColor(gridColor);
    }

//advanced graphic builder
    public static DefaultTableCellRenderer getCustomTable() {
        return getCustomTable(DTII3, DTII3, MDB1, DTII14, fontText1);
    }

    public static DefaultTableCellRenderer getCustomTable(
            Color colorPrincipal, Color colorSecundario, Color colorSeleccion, Color colorFuente, Font fuente
    ) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                celda.setOpaque(true);
                celda.setFont(fuente);
                celda.setForeground(colorFuente);
                celda.setHorizontalAlignment(CENTER);
                if (row % 2 != 0) {
                    celda.setBackground(colorPrincipal);
                }
                else {
                    celda.setBackground(colorSecundario);
                }
                if (isSelected) {
                    celda.setBackground(colorSeleccion);
                    celda.setForeground(WHITE);
                }
                return celda;
            }
        };
    }

}
