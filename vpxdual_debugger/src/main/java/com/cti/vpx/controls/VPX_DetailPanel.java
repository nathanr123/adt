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

import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;

public class VPX_DetailPanel extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531698786318434675L;

	private JTable tbl_Property;

	private DefaultTableModel tbl_Property_Model;

	/**
	 * Create the dialog.
	 */

	public VPX_DetailPanel(String path) {
		init();

		loadComponents();

		loadProperties(path);

		centerFrame();
	}

	private void init() {
		setSize(343, 551);

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

		tbl_Property_Model = new DefaultTableModel(new String[] { "Property", "Value" }, 0);

		tbl_Property.setModel(tbl_Property_Model);

		scrollPane.setViewportView(tbl_Property);
	}

	private void loadProperties(String path) {

		if (path.startsWith(VPXSystem.class.getSimpleName())) {		
			loadProperties(VPXUtilities.getVPXSystem());
		} else {
			loadProperties(VPXUtilities.getSelectedProcessor(path));		
		}

	}

	private void loadProperties(VPXSystem sys) {

		tbl_Property_Model.addRow(new String[] { "Name", sys.getName() });

		List<Processor> processors = sys.getProcessors();

		if (processors != null) {
			tbl_Property_Model.addRow(new String[] { "Total No of Processors", ("" + processors.size()) });

			for (Iterator<Processor> iterator = processors.iterator(); iterator.hasNext();) {

				Processor processor = (Processor) iterator.next();

				tbl_Property_Model.addRow(new String[] { "", "" });

				tbl_Property_Model.addRow(new String[] { "Processor Name", processor.getName() });

				tbl_Property_Model.addRow(new String[] { "Processor ID", ("" + processor.getID()) });

				tbl_Property_Model.addRow(new String[] { "IP Address", processor.getiP_Addresses() });

				tbl_Property_Model.addRow(new String[] { "Port", ("" + processor.getPortno()) });

				tbl_Property_Model.addRow(new String[] { "Processor Type", processor.getProcessorType().toString() });

			}
		}

	}

	private void loadProperties(Processor processor) {

		if (processor != null) {

			tbl_Property_Model.addRow(new String[] { "Processor Name", processor.getName() });

			tbl_Property_Model.addRow(new String[] { "Processor ID", ("" + processor.getID()) });

			tbl_Property_Model.addRow(new String[] { "IP Address", processor.getiP_Addresses() });

			tbl_Property_Model.addRow(new String[] { "Port", ("" + processor.getPortno()) });

			tbl_Property_Model.addRow(new String[] { "Processor Type", processor.getProcessorType().toString() });

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
