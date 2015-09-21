package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cti.vpx.model.AliasTableModel;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_AliasConfigWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1545589537076717584L;

	private JPanel contentPane;

	private JTextField txtAliasName;

	private JTextField txtP2020;

	private JTextField txtDSP1;

	private JTextField txtDSP2;

	private JTable alaisTable;

	private AliasTableModel aliasTableModel = new AliasTableModel();

	private VPX_ETHWindow parent;

	private VPXSubSystem currentSubSystem = null;

	private VPXSystem sub;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnClearAll;
	private JButton btnReset;
	private JButton btnSave;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VPX_AliasConfigWindow frame = new VPX_AliasConfigWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_AliasConfigWindow() {

		init();

		loadComponents();

		centerFrame();

		loadAliasFile();
	}

	/**
	 * Create the frame.
	 */
	public VPX_AliasConfigWindow(VPX_ETHWindow prnt) {

		this.parent = prnt;

		init();

		loadComponents();

		centerFrame();

		loadAliasFile();
	}

	private void init() {

		setTitle("Alias Configuration");

		setIconImage(VPXUtilities.getAppIcon());

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 600, 523);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		JPanel detailPanel = new JPanel();

		detailPanel.setBorder(new TitledBorder(null, "Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		detailPanel.setPreferredSize(new Dimension(10, 210));

		contentPane.add(detailPanel, BorderLayout.SOUTH);

		detailPanel.setLayout(new BorderLayout(0, 0));

		JPanel dataPanel = new JPanel();

		dataPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		detailPanel.add(dataPanel, BorderLayout.CENTER);

		dataPanel.setLayout(null);

		JLabel lblName = new JLabel("Alias Name");

		lblName.setBounds(15, 21, 108, 20);

		dataPanel.add(lblName);

		txtAliasName = new JTextField();

		txtAliasName.setBounds(133, 21, 286, 20);

		dataPanel.add(txtAliasName);

		txtAliasName.setColumns(10);

		JLabel lblP2020 = new JLabel("P2020 IP");

		lblP2020.setBounds(15, 62, 108, 20);

		dataPanel.add(lblP2020);

		txtP2020 = new JTextField();

		txtP2020.setBounds(133, 62, 215, 20);

		dataPanel.add(txtP2020);

		txtP2020.setColumns(10);

		JLabel lblDSP1 = new JLabel("DSP1 IP");

		lblDSP1.setBounds(15, 103, 108, 20);

		dataPanel.add(lblDSP1);

		txtDSP1 = new JTextField();

		txtDSP1.setBounds(133, 103, 215, 20);

		txtDSP1.setColumns(10);

		dataPanel.add(txtDSP1);

		JLabel lblDSP2 = new JLabel("DSP2 IP");

		lblDSP2.setBounds(15, 144, 108, 20);

		dataPanel.add(lblDSP2);

		txtDSP2 = new JTextField();

		txtDSP2.setBounds(133, 144, 215, 20);

		txtDSP2.setColumns(10);

		dataPanel.add(txtDSP2);

		JPanel controlPanel = new JPanel();

		controlPanel.setPreferredSize(new Dimension(130, 10));

		detailPanel.add(controlPanel, BorderLayout.EAST);

		controlPanel.setLayout(null);

		btnAdd = VPXComponentFactory.createJButton(new AddAliasAction("Add"));

		btnAdd.setBounds(10, 6, 116, 23);

		controlPanel.add(btnAdd);

		btnDelete = VPXComponentFactory.createJButton(new DeleteAliasAction("Delete"));

		btnDelete.setBounds(10, 35, 116, 23);

		controlPanel.add(btnDelete);

		btnUpdate = VPXComponentFactory.createJButton(new UpdateAliasAction("Update"));

		btnUpdate.setBounds(10, 64, 116, 23);

		controlPanel.add(btnUpdate);

		btnClearAll = VPXComponentFactory.createJButton(new DeleteAliasFileAction("Clear All"));

		btnClearAll.setBounds(10, 93, 116, 23);

		controlPanel.add(btnClearAll);

		btnReset = VPXComponentFactory.createJButton(new ResetAliasAction("Reset"));

		btnReset.setBounds(10, 122, 116, 23);

		controlPanel.add(btnReset);

		btnSave = VPXComponentFactory.createJButton(new SaveAliasAction("Save"));

		btnSave.setBounds(10, 154, 116, 23);

		controlPanel.add(btnSave);

		JPanel aliasPanel = new JPanel();

		aliasPanel.setBorder(new TitledBorder(null, "Alias List", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(aliasPanel, BorderLayout.CENTER);

		aliasPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane aliasTableScrollPanel = new JScrollPane();

		aliasPanel.add(aliasTableScrollPanel, BorderLayout.CENTER);

		alaisTable = new JTable();

		alaisTable.setShowVerticalLines(false);

		alaisTable.setShowHorizontalLines(false);

		alaisTable.setShowGrid(false);

		alaisTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		alaisTable.setModel(aliasTableModel);

		alaisTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent paramListSelectionEvent) {

				if (paramListSelectionEvent.getValueIsAdjusting()) {

					String strSource = paramListSelectionEvent.getSource().toString();

					int start = strSource.indexOf("{") + 1, stop = strSource.length() - 1;

					String str = strSource.substring(start, stop);

					if (str.length() > 0) {
						int i = Integer.parseInt(str);

						currentSubSystem = new VPXSubSystem(
								Integer.valueOf(aliasTableModel.getValueAt(i, 0).toString()),
								aliasTableModel.getValueAt(i, 1).toString(),
								aliasTableModel.getValueAt(i, 2).toString(),
								aliasTableModel.getValueAt(i, 3).toString(),
								aliasTableModel.getValueAt(i, 4).toString());

						loadAliasDetail();
					}
				}
			}
		});

		alaisTable.getColumnModel().getColumn(0).setResizable(false);
		alaisTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		alaisTable.getColumnModel().getColumn(0).setMinWidth(50);
		alaisTable.getColumnModel().getColumn(0).setMaxWidth(50);

		alaisTable.getColumnModel().getColumn(1).setResizable(false);
		alaisTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		alaisTable.getColumnModel().getColumn(1).setMinWidth(200);

		alaisTable.getColumnModel().getColumn(1).setMaxWidth(200);
		alaisTable.getColumnModel().getColumn(2).setResizable(false);
		alaisTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		alaisTable.getColumnModel().getColumn(2).setMinWidth(105);
		alaisTable.getColumnModel().getColumn(2).setMaxWidth(105);

		alaisTable.getColumnModel().getColumn(3).setResizable(false);
		alaisTable.getColumnModel().getColumn(3).setPreferredWidth(105);
		alaisTable.getColumnModel().getColumn(3).setMinWidth(105);
		alaisTable.getColumnModel().getColumn(3).setMaxWidth(105);

		alaisTable.getColumnModel().getColumn(4).setResizable(false);
		alaisTable.getColumnModel().getColumn(4).setPreferredWidth(105);
		alaisTable.getColumnModel().getColumn(4).setMinWidth(105);
		alaisTable.getColumnModel().getColumn(4).setMaxWidth(105);

		aliasTableScrollPanel.setViewportView(alaisTable);

		aliasTableScrollPanel.getViewport().setBackground(Color.WHITE);

		// loadToolBar();
	}

	private void loadToolBar() {

		JToolBar aliasToolbar = VPXComponentFactory.createJToolBar();

		aliasToolbar.setFloatable(false);

		contentPane.add(aliasToolbar, BorderLayout.NORTH);

		aliasToolbar.add(VPXComponentFactory
				.createJToolBarButton(new ImportAliasFileAction("Import", VPXConstants.Icons.ICON_DOWNLOAD)), null);

		aliasToolbar.add(VPXComponentFactory
				.createJToolBarButton(new ExportAliasFileAction("Export", VPXConstants.Icons.ICON_UPLOAD)), null);

	}

	private void loadAliasFile() {

		sub = VPXUtilities.readFromXMLFile();

		if (sub != null) {

			aliasTableModel.addSubSystem(sub.getSubsystem());
		}
	}

	private void loadSubsystemEditable(String subsystem) {

		if (subsystem.length() > 0) {

			currentSubSystem = sub.getSubSystemByName(subsystem);

			loadAliasDetail();

			txtAliasName.setEditable(true);

			txtP2020.setEditable(false);

			txtDSP1.setEditable(false);

			txtDSP2.setEditable(false);

			btnAdd.setEnabled(false);

			btnDelete.setEnabled(false);

			btnUpdate.setEnabled(true);

			btnClearAll.setEnabled(false);

			btnReset.setEnabled(false);

			btnSave.setEnabled(true);

		} else {

			txtAliasName.setText("");

			txtP2020.setText("");

			txtDSP1.setText("");

			txtDSP2.setText("");

			txtAliasName.setEditable(true);

			txtP2020.setEditable(true);

			txtDSP1.setEditable(true);

			txtDSP2.setEditable(true);

			btnAdd.setEnabled(true);

			btnDelete.setEnabled(true);

			btnUpdate.setEnabled(true);

			btnClearAll.setEnabled(true);

			btnReset.setEnabled(true);

			btnSave.setEnabled(true);
		}
	}

	private void addorSaveSubSystem() {

		boolean ismodify = true;

		if (currentSubSystem == null) {

			currentSubSystem = new VPXSubSystem();

			ismodify = false;
		}

		boolean isValidName = (txtAliasName.getText().length() >= 3) ? VPXUtilities.isValidName(txtAliasName.getText())
				: false;

		boolean isValidP2020 = VPXUtilities.isValidIP(txtP2020.getText());

		boolean isValidDSP1 = VPXUtilities.isValidIP(txtDSP1.getText());

		boolean isValidDSP2 = VPXUtilities.isValidIP(txtDSP2.getText());

		if (isValidName && isValidP2020 && isValidDSP1 && isValidDSP2) {

			currentSubSystem.setSubSystem(txtAliasName.getText());

			currentSubSystem.setIpP2020(txtP2020.getText());

			currentSubSystem.setIpDSP1(txtDSP1.getText());

			currentSubSystem.setIpDSP2(txtDSP2.getText());

			int val = isSubsystemAvailable();

			if (val < VPXConstants.SUBSYSTEMAVAILBLE) {

				String msg = "";

				switch (val) {

				case VPXConstants.SUBNAMEAVAILBLE:

					msg = "Sub System name is already availble.\nPlease enter new name!.";

					break;

				case VPXConstants.P2020AVAILBLE:

					msg = "P2020 ip address is already availble.\nPlease enter new ip address!.";

					break;

				case VPXConstants.DSP1AVAILBLE:

					msg = "DSP1 ip address is already availble.\nPlease enter new ip address!.";

					break;
				case VPXConstants.DSP2AVAILBLE:

					msg = "DSP2 ip address is already availble.\nPlease enter new ip address!.";

					break;

				}

				JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, msg, "Validation", JOptionPane.ERROR_MESSAGE);

			} else {

				if (ismodify) {

					if (aliasTableModel.modifySubSystem(currentSubSystem)) {

						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this,
								currentSubSystem.getSubSystem() + " updated successfully!");

						parent.updateLog("Alias Configuration modified");
					} else {
						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this,
								"Error in modifying " + currentSubSystem.getSubSystem(), "Updating",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				else {

					if (aliasTableModel.addSubSystem(currentSubSystem)) {

						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this,
								currentSubSystem.getSubSystem() + " added successfully!");

						parent.updateLog("Alias Configuration Subsystem added");
					} else {
						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this,
								"Error in adding " + currentSubSystem.getSubSystem(), "Adding",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} else {

			String msg = "";

			if (!isValidName) {

				msg = "Please enter valid subsystem name";

			} else if (!isValidP2020) {

				msg = "Please enter valid P2020 IP Address";

			} else if (!isValidDSP1) {

				msg = "Please enter valid DSP1 IP Address";

			} else if (!isValidDSP2) {

				msg = "Please enter valid DSP2 IP Address";

			}

			JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, msg, "Validation", JOptionPane.ERROR_MESSAGE);
		}

		currentSubSystem = null;
	}

	private void loadAliasDetail() {

		txtAliasName.setText(currentSubSystem.getSubSystem());

		txtP2020.setText(currentSubSystem.getIpP2020());

		txtDSP1.setText(currentSubSystem.getIpDSP1());

		txtDSP2.setText(currentSubSystem.getIpDSP2());

	}

	private int isSubsystemAvailable() {

		int ret = VPXConstants.SUBSYSTEMAVAILBLE;

		List<VPXSubSystem> curList = aliasTableModel.getSubSystem().getSubsystem();

		for (Iterator<VPXSubSystem> iterator = curList.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (vpxSubSystem.getId() != currentSubSystem.getId()) {

				if (currentSubSystem.getSubSystem().equals(vpxSubSystem.getSubSystem())) {

					ret = VPXConstants.SUBNAMEAVAILBLE;

				} else if (currentSubSystem.getIpP2020().equals(vpxSubSystem.getIpP2020())
						|| currentSubSystem.getIpP2020().equals(vpxSubSystem.getIpDSP1())
						|| currentSubSystem.getIpP2020().equals(vpxSubSystem.getIpDSP2())) {

					ret = VPXConstants.P2020AVAILBLE;

				} else if (currentSubSystem.getIpDSP1().equals(vpxSubSystem.getIpP2020())
						|| currentSubSystem.getIpDSP1().equals(vpxSubSystem.getIpDSP1())
						|| currentSubSystem.getIpDSP1().equals(vpxSubSystem.getIpDSP2())) {

					ret = VPXConstants.DSP1AVAILBLE;

				} else if (currentSubSystem.getIpDSP2().equals(vpxSubSystem.getIpP2020())
						|| currentSubSystem.getIpDSP2().equals(vpxSubSystem.getIpDSP1())
						|| currentSubSystem.getIpDSP2().equals(vpxSubSystem.getIpDSP2())) {

					ret = VPXConstants.DSP2AVAILBLE;
				}
			}
		}

		return ret;
	}

	public void deleteSelectedSubSystem(String subSystem) {

		currentSubSystem = sub.getSubSystemByName(subSystem);

		if (currentSubSystem != null)

			if (aliasTableModel.deleteSubSystem(currentSubSystem.getId())) {

				VPXUtilities.writeToXMLFile(aliasTableModel.getSubSystem());

				VPXSessionManager.setVPXSystem(VPXUtilities.readFromXMLFile());

				parent.updateProcessorTree();
				
				parent.updateLog(subSystem + " deleted ");

				JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this,
						currentSubSystem.getSubSystem() + " deleted successfully!");
				currentSubSystem = null;
				
				
			} else {
				JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Error in deleting " + subSystem, "Deletion",
						JOptionPane.ERROR_MESSAGE);
			}

	}

	public void showAliasWindow() {

		loadAliasFile();

		loadSubsystemEditable("");

		setVisible(true);

	}

	public void showAliasWindow(String subSystemName) {

		loadAliasFile();

		loadSubsystemEditable(subSystemName);

		setVisible(true);

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	public class AddAliasAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public AddAliasAction(String name) {

			putValue(NAME, name);

		}

		public AddAliasAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			addorSaveSubSystem();
		}
	}

	public class DeleteAliasAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public DeleteAliasAction(String name) {

			putValue(NAME, name);

		}

		public DeleteAliasAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String sub = currentSubSystem.getSubSystem();

			int option = JOptionPane.showConfirmDialog(VPX_AliasConfigWindow.this, "Do you want delete " + sub + " ?",
					"Confirmation", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {

				if (currentSubSystem != null)

					if (aliasTableModel.deleteSubSystem(currentSubSystem.getId())) {

						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, sub + " deleted successfully!");
					} else {
						JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Error in deleting " + sub,
								"Deletion", JOptionPane.ERROR_MESSAGE);
					}
			}

		}
	}

	public class UpdateAliasAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public UpdateAliasAction(String name) {

			putValue(NAME, name);

		}

		public UpdateAliasAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String sub = currentSubSystem.getSubSystem();

			int option = JOptionPane.showConfirmDialog(VPX_AliasConfigWindow.this, "Do you want update " + sub + " ?",
					"Confirmation", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {

				if (currentSubSystem != null)

					addorSaveSubSystem();

			}
		}
	}

	public class ResetAliasAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ResetAliasAction(String name) {

			putValue(NAME, name);

		}

		public ResetAliasAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			loadAliasDetail();
		}
	}

	public class SaveAliasAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public SaveAliasAction(String name) {

			putValue(NAME, name);

		}

		public SaveAliasAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			VPXUtilities.writeToXMLFile(aliasTableModel.getSubSystem());

			VPXSessionManager.setVPXSystem(VPXUtilities.readFromXMLFile());

			parent.updateProcessorTree();

			VPX_AliasConfigWindow.this.dispose();

		}
	}

	public class ImportAliasFileAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ImportAliasFileAction(String name) {

			putValue(NAME, name);

		}

		public ImportAliasFileAction(String name, ImageIcon icon) {

			super(name, icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public class ExportAliasFileAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ExportAliasFileAction(String name) {

			putValue(NAME, name);

		}

		public ExportAliasFileAction(String name, ImageIcon icon) {

			super(name, icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (VPXUtilities.writetoXLSFile(aliasTableModel.getSubSystem())) {

				JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Exported successfully!");
			} else {
				JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Error in Exporting ", "Export VPX System",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class DeleteAliasFileAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public DeleteAliasFileAction(String name) {

			putValue(NAME, name);

		}

		public DeleteAliasFileAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(VPX_AliasConfigWindow.this, "Do you want delete all ?",
					"Confirmation", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {

				if (VPXUtilities.deleteXMLFile()) {

					aliasTableModel.clearAllSubSystem();

					JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Deleted successfully!");

				} else {

					JOptionPane.showMessageDialog(VPX_AliasConfigWindow.this, "Error in deleting ", "Deletion",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {

		parent.reloadVPXSystem();

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
