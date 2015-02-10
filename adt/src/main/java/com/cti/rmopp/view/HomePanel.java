/**
 * 
 */
package com.cti.rmopp.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import com.cti.rmopp.controls.CPanel;
import com.cti.rmopp.controls.CScrollPane;
import com.cti.rmopp.controls.CTable;
import com.cti.rmopp.controls.ComponentFactory;

/**
 * @author nathanr_kamal
 *
 */
public class HomePanel extends CPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8807188681325827344L;

	private CPanel usersListPanel, serversListPanel;

	public HomePanel() {
		init();
	}

	private void init() {

		setLayout(new GridLayout(1, 2, 5, 5));

		initandLoadComponents();
	}

	private void initandLoadComponents() {

		File dir = new File(System.getProperty("user.home"));

		FileTableModel model = new FileTableModel(dir);

		CTable table = ComponentFactory.createTable(model,30);

		CScrollPane scr = ComponentFactory.createScrollPane(table);

		usersListPanel = ComponentFactory.createPanel(new BorderLayout());

		usersListPanel.setBorder(ComponentFactory.createTitledBorder("Clients"));

		usersListPanel.add(scr, BorderLayout.CENTER);

		add(usersListPanel);

		CTable table1 = ComponentFactory.createTable(model,30);

		CScrollPane scr1 = ComponentFactory.createScrollPane(table1);

		serversListPanel = ComponentFactory.createPanel(new BorderLayout());

		serversListPanel.setBorder(ComponentFactory.createTitledBorder("Servers"));

		serversListPanel.add(scr1, BorderLayout.CENTER);

		add(serversListPanel);

	}

}
