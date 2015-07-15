package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AliasTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5091869069102370980L;

	private static final String[] COLUMN_NAMES = { "S.No", "Alias Name", "P2020", "DSP1", "DSP2" };

	private static final Class<?>[] COLUMN_CLASSES = { String.class, String.class, String.class, String.class,
			String.class };

	private List<VPXSubSystem> subSystems = new ArrayList<VPXSubSystem>();

	static {
		assert COLUMN_NAMES.length == COLUMN_CLASSES.length;
	}

	public AliasTableModel() {

	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return subSystems.size();
	}

	@Override
	public String getColumnName(int arg0) {
		return COLUMN_NAMES[arg0];
	}

	@Override
	public Class<?> getColumnClass(int arg0) {

		return COLUMN_CLASSES[arg0];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		Object ret;

		VPXSubSystem sub = subSystems.get(arg0);

		switch (arg1) {
		case 0:
			ret = sub.getId();
			break;

		case 1:
			ret = sub.getSubSystem();
			break;

		case 2:
			ret = sub.getIpP2020();
			break;
		case 3:
			ret = sub.getIpDSP1();
			break;
		case 4:
			ret = sub.getIpDSP2();
			break;

		default:
			ret = null;
			break;
		}

		return ret;
	}

	public void addSubSystem(List<VPXSubSystem> subs) {

		if (subs != null) {
			subSystems.clear();

			subSystems = subs;

			fireTableDataChanged();
		}
	}

	public boolean addSubSystem(VPXSubSystem sub) {

		try {
			sub.setId(subSystems.size() + 1);

			subSystems.add(sub);

			int i = subSystems.size() - 1;

			fireTableRowsInserted(i, i);

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public boolean clearAllSubSystem() {

		try {

			subSystems.clear();

			fireTableDataChanged();

			return true;

		} catch (Exception e) {

			return false;
		}

	}

	public boolean modifySubSystem(VPXSubSystem sub) {

		try {
			int i = 0;

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getId() == sub.getId()) {

					subSystems.set(i, sub);

					break;
				}

				i++;
			}

			fireTableDataChanged();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public boolean deleteSubSystem(int id) {

		boolean ret = false;

		int i = -1;

		for (int j = 0; j < subSystems.size(); j++) {

			if (subSystems.get(j).getId() == id) {

				i = j;

				break;
			}
		}

		if (i > 0) {
			subSystems.remove(i);

			Collections.sort(subSystems);

			i = 0;

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				iterator.next().setId(i + 1);

				i++;
			}

			fireTableDataChanged();

			ret = true;
		}

		return ret;

	}

	public VPXSystem getSubSystem() {

		VPXSystem vpxSystem = new VPXSystem();

		vpxSystem.setSubsystem(subSystems);

		return vpxSystem;
	}

}
