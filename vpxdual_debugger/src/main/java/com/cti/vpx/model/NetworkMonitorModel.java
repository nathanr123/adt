package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class NetworkMonitorModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4422961699830807091L;

	private static final String[] COLUMN_NAMES = { "Pckt No.", "Time", "Source", "Destination", "Protocol", "Port",
			"Length", "Info" };

	private static final Class<?>[] COLUMN_CLASSES = { Integer.class, String.class, String.class, String.class,
			String.class, Integer.class, Integer.class, JLabel.class };

	static {
		assert COLUMN_NAMES.length == COLUMN_CLASSES.length;
	}

	private List<VPXNWPacket> packets = new ArrayList<VPXNWPacket>();

	public NetworkMonitorModel() {

	}

	@Override
	public int getRowCount() {
		return packets.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object ret;

		VPXNWPacket sub = packets.get(rowIndex);

		switch (columnIndex) {
		case 0:
			ret = sub.getPktNo();
			break;

		case 1:
			ret = sub.getRecievedTime();
			break;

		case 2:
			ret = sub.getSourceIP();
			break;

		case 3:
			ret = sub.getDestIP();
			break;
		case 4:
			ret = sub.getProtocol();
			break;

		case 5:
			ret = sub.getPort();
			break;

		case 6:
			ret = sub.getLength();
			break;

		case 7:
			ret = sub.getInfo();
			break;

		default:
			ret = null;
			break;
		}

		return ret;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public List<VPXNWPacket> getPacketsList() {

		return this.packets;
	}

	public boolean addPacket(VPXNWPacket pkt) {

		try {
			if (packets.size() > 10000) {

				packets.clear();

				fireTableDataChanged();
			}

			pkt.setPktNo(packets.size() + 1);

			packets.add(pkt);

			int i = packets.size() - 1;

			fireTableRowsInserted(i, i);

			return true;

		} catch (Exception e) {
		
			return false;
		}
	}

	public boolean clearAll() {

		try {

			packets.clear();

			fireTableDataChanged();

			return true;

		} catch (Exception e) {

			return false;
		}

	}

	public VPXNWPacket getPacket(int id) {

		VPXNWPacket pkt = null;

		for (Iterator<VPXNWPacket> iterator = packets.iterator(); iterator.hasNext();) {

			VPXNWPacket vpxnwPacket = iterator.next();

			if (id == vpxnwPacket.getPktNo()) {

				pkt = vpxnwPacket;

				break;
			}

		}

		return pkt;
	}
}
