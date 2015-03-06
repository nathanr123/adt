package com.cti.vpx.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ScanWindow extends JPanel {

    private JTable table;
    private JTree tree;
    static DefaultTreeModel dm;
    JPanel buttonPanel, treeContainer;
    static JToggleButton treeButton;
    static JButton scanButton, addButton;
    static DynamicAccessModel accessModel;
    JLabel fromLbl, toLbl;
    JTextField frmField, toField;
    Pinger pinger;
    JScrollPane treeScroll, tableScroll;
    CheckRenderer treeRenderer;
    MyTableRenderer tableRenderer;
    static DefaultMutableTreeNode root;

    /**
     * Create the panel.
     */
    public ScanWindow() {
        setLayout(new BorderLayout(0, 0));
        addingTree();
        addingButtonPanel();

    }

    private void addingButtonPanel() {

        buttonPanel = new JPanel();

        scanButton = new JButton("Scan");
        scanButton.addActionListener(new ScanAction());
        buttonPanel.add(scanButton);

        addButton = new JButton("Add");
        addButton.setEnabled(false);
        addButton.addActionListener(new ScanAction());

        buttonPanel.add(addButton);
        treeButton = new JToggleButton("TableView");
        treeButton.setEnabled(false);
        treeButton.addActionListener(new ScanAction());
        buttonPanel.add(treeButton);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void addingNodes(String ips) {
        CheckNode treeNode = new CheckNode(ips, true, false);
        dm.insertNodeInto(treeNode, root, root.getChildCount());
    }

    private void addingTable() {
        if (table == null) {
            table = new JTable();
            accessModel = new DynamicAccessModel();
            accessModel.addRows();
        }
        table.setSize(new Dimension(450, 400));
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selRow = table.getSelectedRow();

                if (selRow != -1) {
                    if (((Boolean) accessModel.getValueAt(selRow, 0)) == false) {
                        accessModel.setValueAt(false, selRow, 0);
                        System.out.println("mouse listener return false");
                    } else {
                        System.out.println("mouse listener return true");
                        accessModel.setValueAt(true, selRow, 0);
                    }
                }
            }
        });

        
        table.setDefaultRenderer(Object.class, new MyTableRenderer());
        table.setModel(accessModel);
        
        tableScroll = new JScrollPane(table);
        if (treeScroll != null) {
            tableScroll.setVisible(true);
            treeScroll.setVisible(false);
        }

        revalidate();

        repaint();

        add(tableScroll, BorderLayout.CENTER);

    }

    class NodeSelectionListener extends MouseAdapter {

        JTree tree;

        NodeSelectionListener(JTree tree) {
            this.tree = tree;
        }

        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int row = tree.getRowForLocation(x, y);
            TreePath path = tree.getPathForRow(row);
            if (path != null) {
                Object o = ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
                if (!((String) o).equalsIgnoreCase("System")) {
                    CheckNode node = (CheckNode) path.getLastPathComponent();
                    boolean isSelected = !(node.isSelected());
                    node.setSelected(isSelected);
                    if (node.getSelectionMode() == pinger.list.size()) {
                        if (isSelected) {
                            tree.expandPath(path);
                        } else {
                            tree.collapsePath(path);
                        }
                    }
                    ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                    // I need revalidate if node is root.  but why?
                    if (row == 0) {
                        tree.revalidate();
                        tree.repaint();
                    }
                }
            }
        }
    }

    class CheckRenderer extends JPanel implements TreeCellRenderer {

        protected JCheckBox check;
        protected TreeLabel label;
        int h = 10;
        ImageIcon greenIcon = new ImageIcon(IconImages.round(h, Color.GREEN, true));
        ImageIcon emptyIcon = new ImageIcon(IconImages.empty(16, 16));

        public CheckRenderer() {
            setLayout(null);
            check = new JCheckBox();
            add(label = new TreeLabel());

        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean isSelected, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            String stringValue = tree.convertValueToText(value, isSelected,
                    expanded, leaf, row, hasFocus);
            setEnabled(tree.isEnabled());
            if (!stringValue.equals("System")) {
                check.setSelected(((CheckNode) value).isSelected());
                label.setFont(tree.getFont());
                label.setText(stringValue);
                label.setSelected(isSelected);
                label.setFocus(hasFocus);
                check.setBackground(UIManager.getColor("Tree.textBackground"));
                label.setForeground(UIManager.getColor("Tree.textForeground"));
                add(check);
            }

            if (leaf) {
                
                label.setIcon(greenIcon);
                label.setText(stringValue);
            } else if (expanded) {
                label.setIcon(UIManager.getIcon("Tree.openIcon"));
                label.setForeground(UIManager.getColor("Tree.textForeground"));
                label.setText(stringValue);
            } else {
                label.setIcon(greenIcon);
                label.setForeground(UIManager.getColor("Tree.textForeground"));
                label.setText(stringValue);
            }
            return this;
        }

        public Dimension getPreferredSize() {
            Dimension d_check = check.getPreferredSize();
            Dimension d_label = label.getPreferredSize();
            return new Dimension(d_check.width + d_label.width,
                    (d_check.height < d_label.height ? d_label.height
                    : d_check.height));
        }

        public void doLayout() {
            Dimension d_check = check.getPreferredSize();
            Dimension d_label = label.getPreferredSize();
            int y_check = 0;
            int y_label = 0;
            if (d_check.height < d_label.height) {
                y_check = (d_label.height - d_check.height) / 2;
            } else {
                y_label = (d_check.height - d_label.height) / 2;
            }
            check.setLocation(0, y_check);
            check.setBounds(0, y_check, d_check.width, d_check.height);
            label.setLocation(d_check.width, y_label);
            label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
        }

        public void setBackground(Color color) {
            if (color instanceof ColorUIResource) {
                color = null;
            }
            super.setBackground(color);
        }

        public class TreeLabel extends JLabel {

            boolean isSelected;

            boolean hasFocus;

            public TreeLabel() {
            }

            public void setBackground(Color color) {
                if (color instanceof ColorUIResource) {
                    color = null;
                }
                super.setBackground(color);
            }

            public void paint(Graphics g) {
                String str;
                if ((str = getText()) != null) {
                    if (0 < str.length()) {
                        if (isSelected) {
                            g.setColor(UIManager
                                    .getColor("Tree.selectionBorderColor"));
                        } else {
                            g.setColor(UIManager.getColor("Tree.textBackground"));
                        }
                        Dimension d = getPreferredSize();
                        int imageOffset = 0;
                        Icon currentI = getIcon();
                        if (currentI != null) {
                            imageOffset = currentI.getIconWidth()
                                    + Math.max(0, getIconTextGap() - 1);
                        }
                        g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
                                d.height);
                        if (hasFocus) {
                            g.setColor(UIManager
                                    .getColor("Tree.selectionBorderColor"));
                            g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                                    d.height - 1);
                        }
                    }
                }
                super.paint(g);
            }

            public Dimension getPreferredSize() {
                Dimension retDimension = super.getPreferredSize();
                if (retDimension != null) {
                    retDimension = new Dimension(retDimension.width + 3,
                            retDimension.height);
                }
                return retDimension;
            }

            public void setSelected(boolean isSelected) {
                this.isSelected = isSelected;
            }

            public void setFocus(boolean hasFocus) {
                this.hasFocus = hasFocus;
            }
        }
    }

    private void addingTree() {
        dm = new DefaultTreeModel(new DefaultMutableTreeNode("System"));
        root = (DefaultMutableTreeNode) dm.getRoot();
        if (tree == null) {
            tree = new JTree(dm);
        }
        treeRenderer = new CheckRenderer();
        tree.setCellRenderer(treeRenderer);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION
        );

        tree.addMouseListener(new NodeSelectionListener(tree));
        treeScroll = new JScrollPane(tree);
        if (tableScroll != null) {
            tableScroll.setVisible(false);
            treeScroll.setVisible(true);
        }
        revalidate();
        repaint();
        add(treeScroll, BorderLayout.CENTER);

        //     splitPane.revalidate();
        //    splitPane.repaint();
    }

    class MyTableRenderer implements TableCellRenderer {

        int h = 15;
        ImageIcon greenIcon = new ImageIcon(IconImages.round(h, Color.GREEN, true));
        ImageIcon emptyIcon = new ImageIcon(IconImages.empty(16, 16));

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel editor = new JLabel();
            if (value != null) {
                editor.setText(value.toString());
            }
            editor.setHorizontalAlignment(JLabel.CENTER);
            editor.setIcon((Icon) ((column == 3) ? greenIcon : emptyIcon));
            return editor;
        }
    }

    public class ScanAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if ((event.getActionCommand().equals("Scan"))) {
                displayGUI("Scan");

            } else if ((event.getActionCommand().equals("Add"))) {
                displayGUI("Add");
            } else if (event.getActionCommand().equals("TreeView")) {
                treeButton.setText("TableView");
                addingTree();

            } else {
                treeButton.setText("TreeView");
                addingTable();

            }
        }
    }

    private void displayGUI(String scanOrAdd) {

        int value;
        value = JOptionPane.showConfirmDialog(ScanWindow.this,
                getPanel(scanOrAdd),
                "Scaning IPs : ",
                JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (value == JOptionPane.OK_OPTION) {
            if ((scanOrAdd.equalsIgnoreCase("scan"))) {
                scanIPsOnLan(frmField.getText(), toField.getText());
            } else {
                if (accessModel != null) {
                    accessModel.addRow(frmField.getText());
                }
                CheckNode treeNode = new CheckNode(frmField.getText(), true, false);
                dm.insertNodeInto(treeNode, root, root.getChildCount());
                revalidate();
                repaint();
            }
        } else {
            // write the code for cancel button I dont know what is doing here
        }

    }

    public class RowData {

        Boolean flag;
        String ips;
        String processType;
        String status;
        boolean selected;

        public RowData(String ips, int col) {
            this.ips = ips;
            this.flag = setvalueForCol(col);
            this.processType = "";
            this.status = "";

        }

        public boolean setvalueForCol(int col) {
            if (col == 0) {
                return new Boolean(true);
            } else {
                return new Boolean(false);
            }
        }
    }

    private void scanIPsOnLan(String fromIp, String toIp) {
        try {
            StatusWindow statusWindow = new StatusWindow(this, "Scanning IP Address Please wait");
            pinger = new Pinger(fromIp, toIp, this, statusWindow);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private JPanel getPanel(String scanOrAdd) {
        JPanel panel = new JPanel();
        if (scanOrAdd.equalsIgnoreCase("scan")) {
            panel.setLayout(new GridLayout(2, 2));
            panel.setBorder(BorderFactory.createBevelBorder(0));
            fromLbl = new JLabel("From");
            toLbl = new JLabel("To  ");
            frmField = new JTextField(12);
            toField = new JTextField(12);
            frmField.setText("172.17.1.29");
            toField.setText("172.17.1.60");
            panel.add(fromLbl);
            panel.add(frmField);
            panel.add(toLbl);
            panel.add(toField);
        } else {
            panel.setLayout(new GridLayout(1, 2));
            panel.setBorder(BorderFactory.createBevelBorder(0));
            fromLbl = new JLabel("System");
            frmField = new JTextField(12);
            frmField.setText("172.17.1.19");
            panel.add(fromLbl);
            panel.add(frmField);
        }
        return panel;

    }

    public class DynamicAccessModel extends AbstractTableModel {

        int colIndex = 0;
        private List<RowData> rows = new ArrayList<ScanWindow.RowData>();
        String[] columnNames = new String[]{"", "System", "Type", "Status"};

        public DynamicAccessModel() {

            fireTableDataChanged();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            //System.out.println("get row count...." + rows.size());
            return rows.size();
        }

        public void addRows() {

            for (int i = 1; i < pinger.list.size(); i++) {
                rows.add(new RowData((String) pinger.list.get(i), 1));
            }
            fireTableRowsInserted(rows.size(), rows.size());
        }

        public void addRow(String ip) {
            rows.add(new RowData(ip, 1));
            fireTableRowsInserted(rows.size(), rows.size());
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {

            RowData rowData = rows.get(row);
            switch (col) {
                case 0:
                    return rowData.flag;
                case 1:
                    return rowData.ips;
                case 2:
                    return rowData.processType;
                case 3:
                    return rowData.status;
                default:
                    return "";
            }
        }

        public Class getColumnClass(int columnIndex) {
            Object ret;
            if (columnIndex == 0) {
                return Boolean.class;
            } else if (columnIndex == 3) {
                return Image.class;
            } else {
                return String.class;
            }

        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            RowData rowData = rows.get(row);
            switch (col) {
                case 0:
                    rowData.flag = (Boolean) aValue;
                    break;
                case 1:
                    rowData.ips = (String) aValue;
                    break;
                case 2:
                    rowData.processType = (String) aValue;
                    break;
                case 3:
                    rowData.status = (String) aValue;
                    break;
            }
            fireTableCellUpdated(row, col);
        }
    }

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception evt) {
//        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.getContentPane().add(new ScanWindow());
                frame.pack();
                frame.setSize(450, 400);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
