package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_SubnetFilterWindow extends JDialog {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1991438363434310071L;

    private final JPanel contentPanel = new JPanel();

    private JTextField subnetMask;

    private VPX_ETHWindow parent;

    /**
     * Create the dialog.
     */
    public VPX_SubnetFilterWindow(VPX_ETHWindow parent) {

	this.parent = parent;

	init();

	loadComponents();

	centerFrame();

    }

    private void init() {

	setResizable(false);

	setTitle("Subnet Filter");

	setBounds(100, 100, 261, 130);

	setModal(true);

	getContentPane().setLayout(new BorderLayout());
    }

    private void loadComponents() {

	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	getContentPane().add(contentPanel, BorderLayout.CENTER);

	contentPanel.setLayout(new MigLayout("", "[79.00,grow]", "[14.00,fill][fill]"));

	JLabel lblAdminPassword = new JLabel("Enter subnet mask to filter");

	contentPanel.add(lblAdminPassword, "cell 0 0,alignx left");

	subnetMask = new JTextField();

	contentPanel.add(subnetMask, "cell 0 1,growx");

	JPanel buttonPane = new JPanel();

	buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

	getContentPane().add(buttonPane, BorderLayout.SOUTH);

	JButton btnOK = new JButton("Apply Filter");

	btnOK.setActionCommand("OK");

	btnOK.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		if (VPXUtilities.isValidIP(subnetMask.getText())) {

		    parent.applyMask(subnetMask.getText());

		    VPXUtilities.updateProperties(VPXConstants.ResourceFields.FILTER_SUBNET, subnetMask.getText().trim());

		    parent.refreshProcessorTree(true);

		    VPX_SubnetFilterWindow.this.dispose();
		} else {

		    JOptionPane.showMessageDialog(VPX_SubnetFilterWindow.this,
			    "Given subnet mask is not valid.Please check", "Validation", JOptionPane.ERROR_MESSAGE);

		    subnetMask.requestFocus();
		}
	    }
	});

	buttonPane.add(btnOK);

	getRootPane().setDefaultButton(btnOK);

	JButton btnCancel = new JButton("Close");

	btnCancel.setActionCommand("Cancel");

	btnCancel.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		parent.unToggleFilter();

		VPX_SubnetFilterWindow.this.dispose();

	    }
	});

	buttonPane.add(btnCancel);
    }

    public void showFilter() {

	subnetMask.setText(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.FILTER_SUBNET));

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

}
