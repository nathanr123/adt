package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cti.vpx.command.ATP.PROCESSOR_TYPE;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_DetailPanel extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531698786318434675L;

	private JTable tbl_Property;

	private DefaultTableModel tbl_Property_Model;

	private VPX_ETHWindow parent;

	/**
	 * Create the dialog.
	 */

	public VPX_DetailPanel(VPX_ETHWindow parnt, String path) {

		this.parent = parnt;

		init();

		loadComponents();

		loadProperties(path);

		pack();

		centerFrame();
	}

	private void init() {

		setSize(343, 551);

		setTitle("VPX System Details");

		setAlwaysOnTop(true);

		setIconImage(VPXUtilities.getAppIcon());

		setModal(true);
	}

	private void loadComponents() {

		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btn_Close = new JButton("Close");

		btn_Close.setActionCommand("OK");

		btn_Close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_DetailPanel.this.dispose();
			}
		});

		buttonPane.add(btn_Close);

		getRootPane().setDefaultButton(btn_Close);

		JScrollPane scrollPane = new JScrollPane();

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tbl_Property = new JTable();

		tbl_Property.setRowHeight(20);

		tbl_Property_Model = new DefaultTableModel(new String[] { "Property", "Value" }, 0);

		tbl_Property.setModel(tbl_Property_Model);

		scrollPane.setViewportView(tbl_Property);
	}

	private void loadProperties(String path) {

		if (path.startsWith(VPXSystem.class.getSimpleName())) {

			setTitle("VPX System Details");

			loadProperties(VPXUtilities.getVPXSystem());

			parent.updateLog("Showing VPXSystem DEtails");

		} else if (path.startsWith("<html>")) {

			setTitle(VPXUtilities.getCurrentProcType() + " " + VPXUtilities.getCurrentProcessor() + " Details");

			loadProperties(VPXUtilities.getCurrentProcessor(), VPXUtilities.getCurrentProcType());

		} else {

			loadProperties(VPXUtilities.getSelectedSubSystem(path));
		}

	}

	private void loadProperties(VPXSystem sys) {

		setTitle("VPX System Details");

		tbl_Property_Model.addRow(new String[] { "System Name", sys.getName() });

		List<VPXSubSystem> subSystems = sys.getSubsystem();

		tbl_Property_Model.addRow(new String[] { "Total no of Subsystems", String.format("%d", subSystems.size()) });

		tbl_Property_Model.addRow(new String[] { "", "" });

		tbl_Property_Model.addRow(new String[] { "Subsystems Detail", "" });

		for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			tbl_Property_Model.addRow(new String[] { "Sub System Name", vpxSubSystem.getSubSystem() });

			tbl_Property_Model.addRow(new String[] { "Processor Type", "P2020" });

			tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

			tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

			tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

			tbl_Property_Model.addRow(new String[] { "", "" });

			tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 1" });

			tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

			tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

			tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

			tbl_Property_Model.addRow(new String[] { "", "" });

			tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 2" });

			tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

			tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

			tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

			tbl_Property_Model.addRow(new String[] { "", "" });

		}

	}

	private void loadProperties(VPXSubSystem vpxSubSystem) {

		setTitle(vpxSubSystem.getSubSystem() + " Details");

		parent.updateLog("Showing " + vpxSubSystem.getSubSystem() + " Details");

		tbl_Property_Model.addRow(new String[] { "System Name", VPXSystem.class.getSimpleName() });

		tbl_Property_Model.addRow(new String[] { "Sub System Name", vpxSubSystem.getSubSystem() });

		tbl_Property_Model.addRow(new String[] { "Processor Type", "P2020" });

		tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

		tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

		tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

		tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 1" });

		tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

		tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

		tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

		tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 2" });

		tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

		tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

		tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

	}

	private void loadProperties(String ip, String procType) {

		PROCESSOR_TYPE pType = null;

		VPXSubSystem vpxSubSystem = null;

		if (procType.contains("P2020")) {

			pType = PROCESSOR_TYPE.PROCESSOR_P2020;

			parent.updateLog("Showing " + procType + " " + ip + " Details");

		} else if (procType.contains("DSP1")) {

			pType = PROCESSOR_TYPE.PROCESSOR_DSP1;

		} else if (procType.contains("DSP2")) {

			pType = PROCESSOR_TYPE.PROCESSOR_DSP2;

		}

		List<VPXSubSystem> subs = VPXUtilities.getVPXSystem().getSubsystem();

		for (Iterator<VPXSubSystem> iterator = subs.iterator(); iterator.hasNext();) {

			VPXSubSystem subSystem = iterator.next();

			if (subSystem.getIpP2020().equals(ip) || subSystem.getIpDSP1().equals(ip)
					|| subSystem.getIpDSP2().equals(ip)) {

				vpxSubSystem = subSystem;

				break;
			}
		}

		if (vpxSubSystem != null) {

			tbl_Property_Model.addRow(new String[] { "System Name", VPXSystem.class.getSimpleName() });

			tbl_Property_Model.addRow(new String[] { "Sub System Name", vpxSubSystem.getSubSystem() });

			if (pType == PROCESSOR_TYPE.PROCESSOR_P2020) {

				tbl_Property_Model.addRow(new String[] { "Processor Type", "P2020" });

				tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

				tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

				tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

			} else if (pType == PROCESSOR_TYPE.PROCESSOR_DSP1) {

				tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 1" });

				tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

				tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

				tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });

			} else if (pType == PROCESSOR_TYPE.PROCESSOR_DSP2) {

				tbl_Property_Model.addRow(new String[] { "Processor Type", "DSP 2" });

				tbl_Property_Model.addRow(new String[] { "IP Address", vpxSubSystem.getIpP2020() });

				tbl_Property_Model.addRow(new String[] { "Status", "Alive" });

				tbl_Property_Model.addRow(new String[] { "Last Responded Time ", VPXUtilities.getCurrentTime(2) });
			}
		}
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

}
