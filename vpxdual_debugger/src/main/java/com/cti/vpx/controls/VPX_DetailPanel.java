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
import com.cti.vpx.model.Slot;
import com.cti.vpx.model.VPXSystem;

public class VPX_DetailPanel extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531698786318434675L;

	private JTable tbl_Property;

	private DefaultTableModel tbl_Property_Model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_DetailPanel dialog = new VPX_DetailPanel(new VPXSystem());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_DetailPanel(VPXSystem system) {

		init();

		loadComponents();

		loadProperties(system);

		centerFrame();
	}

	public VPX_DetailPanel(Slot slot) {

		init();

		loadComponents();

		loadProperties(slot);

		centerFrame();
	}

	public VPX_DetailPanel(Processor procr) {

		init();

		loadComponents();

		loadProperties(procr);

		centerFrame();
	}

	private void init() {
		setSize(343, 551);

		setAlwaysOnTop(true);

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

	private void loadProperties(VPXSystem sys) {

		tbl_Property_Model.addRow(new String[] { "Name", sys.getName() });

		tbl_Property_Model.addRow(new String[] { "ID", ("" + sys.getID()) });

		List<Slot> slots = sys.getSlots();

		tbl_Property_Model.addRow(new String[] { "Total No of Slots", ("" + slots.size()) });

		tbl_Property_Model.addRow(new String[] { "", "" });

		for (Iterator<Slot> iterator = slots.iterator(); iterator.hasNext();) {

			Slot slot = iterator.next();

			tbl_Property_Model.addRow(new String[] { "Slot Name", slot.getName() });

			tbl_Property_Model.addRow(new String[] { "Slot ID", ("" + slot.getID()) });

			tbl_Property_Model.addRow(new String[] { "Slot Model", slot.getModel() });

			List<Processor> procrs = slot.getProcessors();

			tbl_Property_Model.addRow(new String[] { "Total Number of Processors", ("" + procrs.size()) });

			for (Iterator<Processor> iterator2 = procrs.iterator(); iterator2.hasNext();) {

				Processor processor = iterator2.next();

				tbl_Property_Model.addRow(new String[] { "", "" });

				tbl_Property_Model.addRow(new String[] { "Processor Name", processor.getName() });

				tbl_Property_Model.addRow(new String[] { "Processor ID", ("" + processor.getID()) });

				tbl_Property_Model.addRow(new String[] { "Processor Type", processor.getProcessorType().toString() });

				if (processor.getiP_Addresses().size() > 0) {

					List<String> ips = processor.getiP_Addresses();

					int i = 0;

					for (Iterator<String> iterator3 = ips.iterator(); iterator3.hasNext();) {

						String string = iterator3.next();

						tbl_Property_Model.addRow(new String[] { "IP Address " + i, string });

						i++;
					}
				} else {
					tbl_Property_Model.addRow(new String[] { "IP Address", processor.getiP_Addresses().get(0) });
				}

			}
		}
	}

	private void loadProperties(Slot slt) {
	}

	private void loadProperties(Processor proc) {
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
