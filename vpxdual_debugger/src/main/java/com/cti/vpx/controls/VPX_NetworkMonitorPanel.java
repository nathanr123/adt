package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;

import com.cti.vpx.model.NetworkMonitorModel;
import com.cti.vpx.model.VPXNWPacket;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;

public class VPX_NetworkMonitorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6846202469821045654L;
	private JTable tableNetworkMonitor;
	private JScrollPane scrlNtetwork;
	private JButton btnSave;
	private JButton btnClear;
	private JPanel panelControl;
	private JPanel panelNetworkMonitor;

	private NetworkMonitorModel nwMonitorModel = new NetworkMonitorModel();

	private DefaultTableCellRenderer centerRenderer;
	private DefaultTableCellRenderer infoRenderer;

	private final JPopupMenu vpxLogContextMenu = new JPopupMenu();

	private JMenuItem vpxSendMsgContextMenu_Clear;

	private JMenuItem vpxLogContextMenu_Show;

	private JMenuItem vpxLogContextMenu_Save;

	private int rowNum = -1;

	/**
	 * Create the panel.
	 */
	public VPX_NetworkMonitorPanel() {

		init();

		loadComponents();

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		createRenderers();
	}

	private void loadComponents() {

		panelControl = new JPanel();

		FlowLayout fl_panelControl = (FlowLayout) panelControl.getLayout();

		fl_panelControl.setAlignment(FlowLayout.RIGHT);

		add(panelControl, BorderLayout.NORTH);

		btnClear = VPXComponentFactory.createJButton(new ClearAction("Clear"));

		btnClear.setPreferredSize(new Dimension(22, 22));

		btnClear.setFocusPainted(false);

		btnClear.setBorderPainted(false);

		panelControl.add(btnClear);

		btnSave = VPXComponentFactory.createJButton(new SaveAction("Save"));

		btnSave.setPreferredSize(new Dimension(22, 22));

		btnSave.setFocusPainted(false);

		btnSave.setBorderPainted(false);

		panelControl.add(btnSave);

		panelNetworkMonitor = new JPanel();

		add(panelNetworkMonitor, BorderLayout.CENTER);

		panelNetworkMonitor.setLayout(new BorderLayout(0, 0));

		scrlNtetwork = new JScrollPane();

		panelNetworkMonitor.add(scrlNtetwork, BorderLayout.CENTER);

		tableNetworkMonitor = new JTable();

		tableNetworkMonitor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent me) {

				JTable table = (JTable) me.getSource();

				Point p = me.getPoint();

				rowNum = table.rowAtPoint(p);

				if (me.getButton() == 3) {
					
					table.setRowSelectionInterval(rowNum, rowNum);

					if (rowNum == -1)
						vpxLogContextMenu_Show.setEnabled(false);
					else
						vpxLogContextMenu_Show.setEnabled(true);

					showPopupMenu(me.getX(), me.getY());
				}
			}

			public void mousePressed(MouseEvent me) {

				JTable table = (JTable) me.getSource();

				Point p = me.getPoint();

				int row = table.rowAtPoint(p);

				if (me.getClickCount() == 2 && row != -1) {

					new VPX_NWDataDetailWindow(nwMonitorModel.getPacket((Integer) nwMonitorModel.getValueAt(row, 0)));

				}
			}
		});

		tableNetworkMonitor.setModel(nwMonitorModel);

		tableNetworkMonitor.setShowVerticalLines(false);

		tableNetworkMonitor.setShowHorizontalLines(false);

		tableNetworkMonitor.setShowGrid(false);

		tableNetworkMonitor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableNetworkMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tableNetworkMonitor.getColumnModel().getColumn(0).setPreferredWidth(60);

		tableNetworkMonitor.getColumnModel().getColumn(0).setMinWidth(60);

		tableNetworkMonitor.getColumnModel().getColumn(0).setMaxWidth(60);

		tableNetworkMonitor.getColumnModel().getColumn(1).setPreferredWidth(200);

		tableNetworkMonitor.getColumnModel().getColumn(1).setMinWidth(200);

		tableNetworkMonitor.getColumnModel().getColumn(1).setMaxWidth(200);

		tableNetworkMonitor.getColumnModel().getColumn(2).setPreferredWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(2).setMinWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(2).setMaxWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(3).setPreferredWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(3).setMinWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(3).setMaxWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(4).setPreferredWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(4).setMinWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(4).setMaxWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(5).setPreferredWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(5).setMinWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(5).setMaxWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(6).setPreferredWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(6).setMinWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(6).setMaxWidth(150);

		tableNetworkMonitor.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		tableNetworkMonitor.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		tableNetworkMonitor.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		tableNetworkMonitor.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

		tableNetworkMonitor.getColumnModel().getColumn(7).setCellRenderer(infoRenderer);

		DefaultTableCellRenderer rend = (DefaultTableCellRenderer) tableNetworkMonitor.getTableHeader()
				.getDefaultRenderer();

		rend.setHorizontalAlignment(JLabel.CENTER);

		tableNetworkMonitor.setAutoCreateRowSorter(true);

		DefaultRowSorter<?, ?> sorter = ((DefaultRowSorter<?, ?>) tableNetworkMonitor.getRowSorter());

		ArrayList<SortKey> list = new ArrayList<SortKey>();

		list.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

		sorter.setSortKeys(list);

		sorter.sort();

		scrlNtetwork.setViewportView(tableNetworkMonitor);

		scrlNtetwork.getViewport().setBackground(Color.WHITE);

		createContextMenus();
	}

	private void createContextMenus() {

		vpxSendMsgContextMenu_Clear = VPXComponentFactory.createJMenuItem("Clear All");

		vpxSendMsgContextMenu_Clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				clear();
			}
		});

		vpxLogContextMenu_Show = VPXComponentFactory.createJMenuItem("Show Detail");

		vpxLogContextMenu_Show.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				showDetail();
			}
		});

		vpxLogContextMenu_Save = VPXComponentFactory.createJMenuItem("Save");

		vpxLogContextMenu_Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				save();
			}
		});

		vpxLogContextMenu.add(vpxSendMsgContextMenu_Clear);

		vpxLogContextMenu.add(vpxLogContextMenu_Save);

		vpxLogContextMenu.add(VPXComponentFactory.createJSeparator());

		vpxLogContextMenu.add(vpxLogContextMenu_Show);

	}

	private void showPopupMenu(int x, int y) {

		vpxLogContextMenu.show(this, x, y);

	}

	private void showDetail() {

		new VPX_NWDataDetailWindow(nwMonitorModel.getPacket((Integer) nwMonitorModel.getValueAt(rowNum, 0)));

	}

	public void addPacket(VPXNWPacket packet) {

		nwMonitorModel.addPacket(packet);
	}

	public void clear() {

		nwMonitorModel.clearAll();
	}

	public void save() {

		try {

			String str = "Pckt No.\tTime\tSource\tDestination\tProtocol\tPort\tLength\tInfo"
					+ System.getProperty("line.separator")
					+ "-----------------------------------------------------------------------------------------------------------------------------------------------------"
					+ System.getProperty("line.separator");

			List<VPXNWPacket> pkts = nwMonitorModel.getPacketsList();

			for (Iterator<VPXNWPacket> iterator = pkts.iterator(); iterator.hasNext();) {

				VPXNWPacket vpxnwPacket = iterator.next();

				str = str + String.format("%d\t%s\t%s\t%s\t%s\t%d\t%d\t\t%s", vpxnwPacket.getPktNo(),
						vpxnwPacket.getRecievedTime(), vpxnwPacket.getSourceIP(), vpxnwPacket.getDestIP(),
						vpxnwPacket.getProtocol(), vpxnwPacket.getPort(), vpxnwPacket.getLength(),
						vpxnwPacket.getInfo().getText()) + System.getProperty("line.separator");
			}

			String path = VPXSessionManager.getLogPath() + "/network";

			File f = new File(path);

			f.mkdir();

			path = path + "/network_" + VPXUtilities.getCurrentTime(3) + ".log";

			FileWriter fw = new FileWriter(new File(path), true);

			fw.write(str);

			fw.close();

			VPXUtilities.showPopup("File Saved at " + path, path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createRenderers() {

		centerRenderer = new DefaultTableCellRenderer();

		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		infoRenderer = new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5453085557892248874L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel label = (JLabel) value;

				String vals = label.getText();

				label.setBackground(Color.white);

				label.setFont(tableNetworkMonitor.getFont());

				if (vals.toLowerCase().startsWith("recieve"))
					label.setIcon(VPXConstants.Icons.ICON_DOWN);
				else
					label.setIcon(VPXConstants.Icons.ICON_UP);

				label.setOpaque(true);

				return label;

			}
		};

	}

	class ClearAction extends AbstractAction {

		/**
		 * 
		 */

		public ClearAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_CLEAR);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_SAVE);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save();
		}
	}

	public static void main(String[] args) {

		JFrame jf = new JFrame();

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf.setBounds(0, 0, 1920, 400);

		jf.getContentPane().setLayout(new BorderLayout());

		VPX_NetworkMonitorPanel m = new VPX_NetworkMonitorPanel();

		m.addPacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), "172.17.1.28", "172.17.1.128", "UDP", 12345,
				1440, new JLabel("in-ADV Packet"), "frewt".getBytes()));
		m.addPacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), "172.17.1.28", "172.17.1.128", "UDP", 12345,
				1440, new JLabel("out-ADV Packet"), "frewt".getBytes()));

		jf.getContentPane().add(m, BorderLayout.CENTER);

		jf.setVisible(true);
	}

}
