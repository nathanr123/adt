package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class VPX_P2020ConfigurationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6997499677011531392L;

	private JTabbedPane tabbedPane;

	private JPanel vlanConfigPanel;

	private JPanel p2020IPConfigPanel;

	private JTextField txtVLANID2;

	private JTextField txtVLANID3;

	private JTextField txtVLANID4;

	private JTextField txtVLANID5;

	private JTextField txtVLANID6;

	private JTextField txtVLANID7;

	private JTextField txtVLANID8;

	private JTextField txtVLANID9;

	private JTextField txtVLANID10;

	private JTextField txtVLANID11;

	private JTextField txtVLANID12;

	private JTextField txtVLANID13;

	private JTextField txtVLANID14;

	private JTextField txtVLANID1;

	private JCheckBox chkPort0;

	private JCheckBox chkPort1;

	private JCheckBox chkPort2;

	private JCheckBox chkPort3;

	private JCheckBox chkPort4;

	private JCheckBox chkPort13;

	private JCheckBox chkPort12;

	private JCheckBox chkPort11;

	private JCheckBox chkPort10;

	private JCheckBox chkPort9;

	private JCheckBox chkPort8;

	private JCheckBox chkPort7;

	private JCheckBox chkPort6;

	private JCheckBox chkPort5;

	private JTextField txtIPETH1;

	private JTextField txtIPETH2;

	private JTextField txtIPETH3;

	private int selTab = 0;

	/**
	 * Create the panel.
	 */
	public VPX_P2020ConfigurationPanel(int tab) {

		selTab = tab;

		init();

		loadComponents();

		tabbedPane.setSelectedIndex(selTab);

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		add(tabbedPane, BorderLayout.CENTER);

		createVLANPanel();

		createIPConfigPanel();
	}

	private void createVLANPanel() {

		vlanConfigPanel = new JPanel();

		vlanConfigPanel.setPreferredSize(new Dimension(800, 300));

		vlanConfigPanel.setBorder(new TitledBorder(null, "VLAN Configuration", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		vlanConfigPanel.setLayout(new BorderLayout(0, 0));

		JPanel vlanConfigControlsPanel = new JPanel();

		vlanConfigControlsPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		vlanConfigPanel.add(vlanConfigControlsPanel);

		GridBagLayout gbl_vlanConfigControlsPanel = new GridBagLayout();

		gbl_vlanConfigControlsPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };

		gbl_vlanConfigControlsPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, 1.0 };

		gbl_vlanConfigControlsPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };

		vlanConfigControlsPanel.setLayout(gbl_vlanConfigControlsPanel);

		JLabel lblHeaderDetail = new JLabel("Detail");

		lblHeaderDetail.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderDetail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderDetail = new GridBagConstraints();

		gbc_lblHeaderDetail.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderDetail.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderDetail.gridx = 0;

		gbc_lblHeaderDetail.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderDetail, gbc_lblHeaderDetail);

		JLabel lblHeaderP2020 = new JLabel("P2020");

		lblHeaderP2020.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderP2020.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderP2020 = new GridBagConstraints();

		gbc_lblHeaderP2020.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderP2020.gridwidth = 2;

		gbc_lblHeaderP2020.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderP2020.gridx = 1;

		gbc_lblHeaderP2020.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderP2020, gbc_lblHeaderP2020);

		JLabel lblHeaderDSP1 = new JLabel("DSP1");

		lblHeaderDSP1.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderDSP1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderDSP1 = new GridBagConstraints();

		gbc_lblHeaderDSP1.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderDSP1.gridwidth = 2;

		gbc_lblHeaderDSP1.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderDSP1.gridx = 3;

		gbc_lblHeaderDSP1.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderDSP1, gbc_lblHeaderDSP1);

		JLabel lblHeaderDSP2 = new JLabel("DSP2");

		lblHeaderDSP2.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderDSP2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderDSP2 = new GridBagConstraints();

		gbc_lblHeaderDSP2.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderDSP2.gridwidth = 2;

		gbc_lblHeaderDSP2.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderDSP2.gridx = 5;

		gbc_lblHeaderDSP2.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderDSP2, gbc_lblHeaderDSP2);

		JLabel lblHeaderRearDualPhy = new JLabel("Rear Dual Phy");

		lblHeaderRearDualPhy.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderRearDualPhy.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderRearDualPhy = new GridBagConstraints();

		gbc_lblHeaderRearDualPhy.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderRearDualPhy.gridwidth = 2;

		gbc_lblHeaderRearDualPhy.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderRearDualPhy.gridx = 7;

		gbc_lblHeaderRearDualPhy.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderRearDualPhy, gbc_lblHeaderRearDualPhy);

		JLabel lblHeaderReadQuadPhy = new JLabel("Read Quad Phy");

		lblHeaderReadQuadPhy.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderReadQuadPhy.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderReadQuadPhy = new GridBagConstraints();

		gbc_lblHeaderReadQuadPhy.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderReadQuadPhy.gridwidth = 4;

		gbc_lblHeaderReadQuadPhy.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderReadQuadPhy.gridx = 9;

		gbc_lblHeaderReadQuadPhy.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderReadQuadPhy, gbc_lblHeaderReadQuadPhy);

		JLabel lblHeaderFrontDualPhy = new JLabel("Front Dual Phy");

		lblHeaderFrontDualPhy.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderFrontDualPhy.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderFrontDualPhy = new GridBagConstraints();

		gbc_lblHeaderFrontDualPhy.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderFrontDualPhy.gridwidth = 2;

		gbc_lblHeaderFrontDualPhy.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderFrontDualPhy.gridx = 13;

		gbc_lblHeaderFrontDualPhy.gridy = 0;

		vlanConfigControlsPanel.add(lblHeaderFrontDualPhy, gbc_lblHeaderFrontDualPhy);

		JLabel lblHeaderPortNo = new JLabel("Port Number");

		lblHeaderPortNo.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderPortNo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderPortNo = new GridBagConstraints();

		gbc_lblHeaderPortNo.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderPortNo.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderPortNo.gridx = 0;

		gbc_lblHeaderPortNo.gridy = 1;

		vlanConfigControlsPanel.add(lblHeaderPortNo, gbc_lblHeaderPortNo);

		JLabel lblPort0 = new JLabel("0");

		lblPort0.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort0.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort0 = new GridBagConstraints();

		gbc_lblPort0.fill = GridBagConstraints.BOTH;

		gbc_lblPort0.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort0.gridx = 1;

		gbc_lblPort0.gridy = 1;

		vlanConfigControlsPanel.add(lblPort0, gbc_lblPort0);

		JLabel lblPort1 = new JLabel("1");

		lblPort1.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort1 = new GridBagConstraints();

		gbc_lblPort1.fill = GridBagConstraints.BOTH;

		gbc_lblPort1.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort1.gridx = 2;

		gbc_lblPort1.gridy = 1;

		vlanConfigControlsPanel.add(lblPort1, gbc_lblPort1);

		JLabel lblPort2 = new JLabel("2");

		lblPort2.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort2 = new GridBagConstraints();

		gbc_lblPort2.fill = GridBagConstraints.BOTH;

		gbc_lblPort2.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort2.gridx = 3;

		gbc_lblPort2.gridy = 1;

		vlanConfigControlsPanel.add(lblPort2, gbc_lblPort2);

		JLabel lblPort3 = new JLabel("3");

		lblPort3.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort3 = new GridBagConstraints();

		gbc_lblPort3.fill = GridBagConstraints.BOTH;

		gbc_lblPort3.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort3.gridx = 4;

		gbc_lblPort3.gridy = 1;

		vlanConfigControlsPanel.add(lblPort3, gbc_lblPort3);

		JLabel lblPort4 = new JLabel("4");

		lblPort4.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort4 = new GridBagConstraints();

		gbc_lblPort4.fill = GridBagConstraints.BOTH;

		gbc_lblPort4.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort4.gridx = 5;

		gbc_lblPort4.gridy = 1;

		vlanConfigControlsPanel.add(lblPort4, gbc_lblPort4);

		JLabel lblPort5 = new JLabel("5");

		lblPort5.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort5 = new GridBagConstraints();

		gbc_lblPort5.fill = GridBagConstraints.BOTH;

		gbc_lblPort5.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort5.gridx = 6;

		gbc_lblPort5.gridy = 1;

		vlanConfigControlsPanel.add(lblPort5, gbc_lblPort5);

		JLabel lblPort6 = new JLabel("6");

		lblPort6.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort6 = new GridBagConstraints();

		gbc_lblPort6.fill = GridBagConstraints.BOTH;

		gbc_lblPort6.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort6.gridx = 7;

		gbc_lblPort6.gridy = 1;

		vlanConfigControlsPanel.add(lblPort6, gbc_lblPort6);

		JLabel lblPort7 = new JLabel("7");

		lblPort7.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort7 = new GridBagConstraints();

		gbc_lblPort7.fill = GridBagConstraints.BOTH;

		gbc_lblPort7.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort7.gridx = 8;

		gbc_lblPort7.gridy = 1;

		vlanConfigControlsPanel.add(lblPort7, gbc_lblPort7);

		JLabel lblPort8 = new JLabel("8");

		lblPort8.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort8 = new GridBagConstraints();

		gbc_lblPort8.fill = GridBagConstraints.BOTH;

		gbc_lblPort8.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort8.gridx = 9;

		gbc_lblPort8.gridy = 1;

		vlanConfigControlsPanel.add(lblPort8, gbc_lblPort8);

		JLabel lblPort9 = new JLabel("9");

		lblPort9.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort9 = new GridBagConstraints();

		gbc_lblPort9.fill = GridBagConstraints.BOTH;

		gbc_lblPort9.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort9.gridx = 10;

		gbc_lblPort9.gridy = 1;

		vlanConfigControlsPanel.add(lblPort9, gbc_lblPort9);

		JLabel lblPort10 = new JLabel("10");

		lblPort10.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort10 = new GridBagConstraints();

		gbc_lblPort10.fill = GridBagConstraints.BOTH;

		gbc_lblPort10.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort10.gridx = 11;

		gbc_lblPort10.gridy = 1;

		vlanConfigControlsPanel.add(lblPort10, gbc_lblPort10);

		JLabel lblPort11 = new JLabel("11");

		lblPort11.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort11 = new GridBagConstraints();

		gbc_lblPort11.fill = GridBagConstraints.BOTH;

		gbc_lblPort11.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort11.gridx = 12;

		gbc_lblPort11.gridy = 1;

		vlanConfigControlsPanel.add(lblPort11, gbc_lblPort11);

		JLabel lblPort12 = new JLabel("12");

		lblPort12.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort12 = new GridBagConstraints();

		gbc_lblPort12.fill = GridBagConstraints.BOTH;

		gbc_lblPort12.insets = new Insets(0, 0, 5, 5);

		gbc_lblPort12.gridx = 13;

		gbc_lblPort12.gridy = 1;

		vlanConfigControlsPanel.add(lblPort12, gbc_lblPort12);

		JLabel lblPort13 = new JLabel("13");

		lblPort13.setHorizontalAlignment(SwingConstants.CENTER);

		lblPort13.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblPort13 = new GridBagConstraints();

		gbc_lblPort13.fill = GridBagConstraints.BOTH;

		gbc_lblPort13.insets = new Insets(0, 0, 5, 0);

		gbc_lblPort13.gridx = 14;

		gbc_lblPort13.gridy = 1;

		vlanConfigControlsPanel.add(lblPort13, gbc_lblPort13);

		JLabel lblHeaderVLANId = new JLabel("VLAN ID");

		lblHeaderVLANId.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderVLANId.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderVLANId = new GridBagConstraints();

		gbc_lblHeaderVLANId.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderVLANId.insets = new Insets(0, 0, 5, 5);

		gbc_lblHeaderVLANId.gridx = 0;

		gbc_lblHeaderVLANId.gridy = 2;

		vlanConfigControlsPanel.add(lblHeaderVLANId, gbc_lblHeaderVLANId);

		txtVLANID1 = new JTextField();

		GridBagConstraints gbc_txtVLANID1 = new GridBagConstraints();

		gbc_txtVLANID1.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID1.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID1.gridx = 1;

		gbc_txtVLANID1.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID1, gbc_txtVLANID1);

		txtVLANID1.setColumns(10);

		txtVLANID2 = new JTextField();

		txtVLANID2.setColumns(10);

		GridBagConstraints gbc_txtVLANID2 = new GridBagConstraints();

		gbc_txtVLANID2.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID2.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID2.gridx = 2;

		gbc_txtVLANID2.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID2, gbc_txtVLANID2);

		txtVLANID3 = new JTextField();

		txtVLANID3.setColumns(10);

		GridBagConstraints gbc_txtVLANID3 = new GridBagConstraints();

		gbc_txtVLANID3.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID3.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID3.gridx = 3;

		gbc_txtVLANID3.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID3, gbc_txtVLANID3);

		txtVLANID4 = new JTextField();

		txtVLANID4.setColumns(10);

		GridBagConstraints gbc_txtVLANID4 = new GridBagConstraints();

		gbc_txtVLANID4.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID4.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID4.gridx = 4;

		gbc_txtVLANID4.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID4, gbc_txtVLANID4);

		txtVLANID5 = new JTextField();

		txtVLANID5.setColumns(10);

		GridBagConstraints gbc_txtVLANID5 = new GridBagConstraints();

		gbc_txtVLANID5.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID5.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID5.gridx = 5;

		gbc_txtVLANID5.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID5, gbc_txtVLANID5);

		txtVLANID6 = new JTextField();

		txtVLANID6.setColumns(10);

		GridBagConstraints gbc_txtVLANID6 = new GridBagConstraints();

		gbc_txtVLANID6.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID6.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID6.gridx = 6;

		gbc_txtVLANID6.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID6, gbc_txtVLANID6);

		txtVLANID7 = new JTextField();

		txtVLANID7.setColumns(10);

		GridBagConstraints gbc_txtVLANID7 = new GridBagConstraints();

		gbc_txtVLANID7.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID7.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID7.gridx = 7;

		gbc_txtVLANID7.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID7, gbc_txtVLANID7);

		txtVLANID8 = new JTextField();

		txtVLANID8.setColumns(10);

		GridBagConstraints gbc_txtVLANID8 = new GridBagConstraints();

		gbc_txtVLANID8.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID8.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID8.gridx = 8;

		gbc_txtVLANID8.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID8, gbc_txtVLANID8);

		txtVLANID9 = new JTextField();

		txtVLANID9.setColumns(10);

		GridBagConstraints gbc_txtVLANID9 = new GridBagConstraints();

		gbc_txtVLANID9.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID9.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID9.gridx = 9;

		gbc_txtVLANID9.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID9, gbc_txtVLANID9);

		txtVLANID10 = new JTextField();

		txtVLANID10.setColumns(10);

		GridBagConstraints gbc_txtVLANID10 = new GridBagConstraints();

		gbc_txtVLANID10.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID10.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID10.gridx = 10;

		gbc_txtVLANID10.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID10, gbc_txtVLANID10);

		txtVLANID11 = new JTextField();

		txtVLANID11.setColumns(10);

		GridBagConstraints gbc_txtVLANID11 = new GridBagConstraints();

		gbc_txtVLANID11.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID11.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID11.gridx = 11;

		gbc_txtVLANID11.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID11, gbc_txtVLANID11);

		txtVLANID12 = new JTextField();

		txtVLANID12.setColumns(10);

		GridBagConstraints gbc_txtVLANID12 = new GridBagConstraints();

		gbc_txtVLANID12.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID12.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID12.gridx = 12;

		gbc_txtVLANID12.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID12, gbc_txtVLANID12);

		txtVLANID13 = new JTextField();

		txtVLANID13.setColumns(10);

		GridBagConstraints gbc_txtVLANID13 = new GridBagConstraints();

		gbc_txtVLANID13.insets = new Insets(0, 0, 5, 5);

		gbc_txtVLANID13.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID13.gridx = 13;

		gbc_txtVLANID13.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID13, gbc_txtVLANID13);

		txtVLANID14 = new JTextField();

		txtVLANID14.setColumns(10);

		GridBagConstraints gbc_txtVLANID14 = new GridBagConstraints();

		gbc_txtVLANID14.insets = new Insets(0, 0, 5, 0);

		gbc_txtVLANID14.fill = GridBagConstraints.HORIZONTAL;

		gbc_txtVLANID14.gridx = 14;

		gbc_txtVLANID14.gridy = 2;

		vlanConfigControlsPanel.add(txtVLANID14, gbc_txtVLANID14);

		JLabel lblHeaderEnable = new JLabel("Enable");

		lblHeaderEnable.setHorizontalAlignment(SwingConstants.CENTER);

		lblHeaderEnable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_lblHeaderEnable = new GridBagConstraints();

		gbc_lblHeaderEnable.fill = GridBagConstraints.BOTH;

		gbc_lblHeaderEnable.insets = new Insets(0, 0, 0, 5);

		gbc_lblHeaderEnable.gridx = 0;

		gbc_lblHeaderEnable.gridy = 3;

		vlanConfigControlsPanel.add(lblHeaderEnable, gbc_lblHeaderEnable);

		chkPort0 = new JCheckBox("");

		chkPort0.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort0.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort0 = new GridBagConstraints();

		gbc_chkPort0.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort0.gridx = 1;

		gbc_chkPort0.gridy = 3;

		vlanConfigControlsPanel.add(chkPort0, gbc_chkPort0);

		chkPort1 = new JCheckBox("");

		chkPort1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort1.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort1 = new GridBagConstraints();

		gbc_chkPort1.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort1.gridx = 2;

		gbc_chkPort1.gridy = 3;

		vlanConfigControlsPanel.add(chkPort1, gbc_chkPort1);

		chkPort2 = new JCheckBox("");

		chkPort2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort2.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort2 = new GridBagConstraints();

		gbc_chkPort2.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort2.gridx = 3;

		gbc_chkPort2.gridy = 3;

		vlanConfigControlsPanel.add(chkPort2, gbc_chkPort2);

		chkPort3 = new JCheckBox("");

		chkPort3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort3.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort3 = new GridBagConstraints();

		gbc_chkPort3.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort3.gridx = 4;

		gbc_chkPort3.gridy = 3;

		vlanConfigControlsPanel.add(chkPort3, gbc_chkPort3);

		chkPort4 = new JCheckBox("");

		chkPort4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort4.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort4 = new GridBagConstraints();

		gbc_chkPort4.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort4.gridx = 5;

		gbc_chkPort4.gridy = 3;

		vlanConfigControlsPanel.add(chkPort4, gbc_chkPort4);

		chkPort5 = new JCheckBox("");

		chkPort5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort5.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort5 = new GridBagConstraints();

		gbc_chkPort5.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort5.gridx = 6;

		gbc_chkPort5.gridy = 3;

		vlanConfigControlsPanel.add(chkPort5, gbc_chkPort5);

		chkPort6 = new JCheckBox("");

		chkPort6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort6.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort6 = new GridBagConstraints();

		gbc_chkPort6.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort6.gridx = 7;

		gbc_chkPort6.gridy = 3;

		vlanConfigControlsPanel.add(chkPort6, gbc_chkPort6);

		chkPort7 = new JCheckBox("");

		chkPort7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort7.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort7 = new GridBagConstraints();

		gbc_chkPort7.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort7.gridx = 8;

		gbc_chkPort7.gridy = 3;

		vlanConfigControlsPanel.add(chkPort7, gbc_chkPort7);

		chkPort8 = new JCheckBox("");

		chkPort8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort8.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort8 = new GridBagConstraints();

		gbc_chkPort8.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort8.gridx = 9;

		gbc_chkPort8.gridy = 3;

		vlanConfigControlsPanel.add(chkPort8, gbc_chkPort8);

		chkPort9 = new JCheckBox("");

		chkPort9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort9.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort9 = new GridBagConstraints();

		gbc_chkPort9.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort9.gridx = 10;

		gbc_chkPort9.gridy = 3;

		vlanConfigControlsPanel.add(chkPort9, gbc_chkPort9);

		chkPort10 = new JCheckBox("");

		chkPort10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort10.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort10 = new GridBagConstraints();

		gbc_chkPort10.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort10.gridx = 11;

		gbc_chkPort10.gridy = 3;

		vlanConfigControlsPanel.add(chkPort10, gbc_chkPort10);

		chkPort11 = new JCheckBox("");

		chkPort11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort11.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort11 = new GridBagConstraints();

		gbc_chkPort11.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort11.gridx = 12;

		gbc_chkPort11.gridy = 3;

		vlanConfigControlsPanel.add(chkPort11, gbc_chkPort11);

		chkPort12 = new JCheckBox("");

		chkPort12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort12.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort12 = new GridBagConstraints();

		gbc_chkPort12.insets = new Insets(0, 0, 0, 5);

		gbc_chkPort12.gridx = 13;

		gbc_chkPort12.gridy = 3;

		vlanConfigControlsPanel.add(chkPort12, gbc_chkPort12);

		chkPort13 = new JCheckBox("");

		chkPort13.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		chkPort13.setHorizontalAlignment(SwingConstants.CENTER);

		GridBagConstraints gbc_chkPort13 = new GridBagConstraints();

		gbc_chkPort13.gridx = 14;

		gbc_chkPort13.gridy = 3;

		vlanConfigControlsPanel.add(chkPort13, gbc_chkPort13);

		JLabel lblDummyNorth = new JLabel("");

		lblDummyNorth.setPreferredSize(new Dimension(0, 35));

		vlanConfigPanel.add(lblDummyNorth, BorderLayout.NORTH);

		JLabel lblDummyWest = new JLabel("");

		lblDummyWest.setPreferredSize(new Dimension(25, 14));

		vlanConfigPanel.add(lblDummyWest, BorderLayout.WEST);

		JLabel lblDummyEast = new JLabel("");

		lblDummyEast.setPreferredSize(new Dimension(25, 14));

		vlanConfigPanel.add(lblDummyEast, BorderLayout.EAST);

		JPanel controlsPanel = new JPanel();

		FlowLayout fl_controlsPanel = (FlowLayout) controlsPanel.getLayout();

		fl_controlsPanel.setVgap(10);

		controlsPanel.setPreferredSize(new Dimension(10, 300));

		vlanConfigPanel.add(controlsPanel, BorderLayout.SOUTH);

		JButton btnConfigApply = new JButton("Apply Configuration");

		controlsPanel.add(btnConfigApply);

		JButton btnConfigRestore = new JButton("Restore");

		controlsPanel.add(btnConfigRestore);

		tabbedPane.addTab("VLAN", null, vlanConfigPanel, null);
	}

	private void createIPConfigPanel() {

		p2020IPConfigPanel = new JPanel();

		p2020IPConfigPanel.setPreferredSize(new Dimension(400, 200));

		p2020IPConfigPanel.setLayout(new MigLayout("", "[16.00][][][260.00,fill][grow]",
				"[16.00][][16.00][][16.00][][16.00][]"));

		JLabel lblETH1 = new JLabel("ETH1");

		p2020IPConfigPanel.add(lblETH1, "cell 1 1");

		txtIPETH1 = new JTextField();

		p2020IPConfigPanel.add(txtIPETH1, "cell 3 1,growx");

		txtIPETH1.setColumns(10);

		JLabel lblETH2 = new JLabel("ETH2");

		p2020IPConfigPanel.add(lblETH2, "cell 1 3");

		txtIPETH2 = new JTextField();

		txtIPETH2.setColumns(10);

		p2020IPConfigPanel.add(txtIPETH2, "cell 3 3,growx");

		JLabel lblETH3 = new JLabel("ETH3");

		p2020IPConfigPanel.add(lblETH3, "cell 1 5");

		txtIPETH3 = new JTextField();

		txtIPETH3.setEnabled(false);

		txtIPETH3.setColumns(10);

		p2020IPConfigPanel.add(txtIPETH3, "cell 3 5,growx");

		JButton btnIPApply = new JButton("Apply");

		p2020IPConfigPanel.add(btnIPApply, "flowx,cell 3 7");

		JButton btnClear = new JButton("Clear");

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		p2020IPConfigPanel.add(btnClear, "cell 3 7");

		tabbedPane.addTab("P2020 IP Config", null, p2020IPConfigPanel, null);
	}
}
