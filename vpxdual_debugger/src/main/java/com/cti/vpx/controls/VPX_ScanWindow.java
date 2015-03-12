package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

public class VPX_ScanWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6540961732678062286L;

	private final JPanel contentPanel = new JPanel();

	private JTextField fromIP;

	private JTextField toIP;

	private VPX_Dual_ADT_RootWindow parent;

	/**
	 * Create the dialog.
	 */
	public VPX_ScanWindow(VPX_Dual_ADT_RootWindow parent) {

		super(parent);

		this.parent = parent;

		init();

		loadComponents();

		centerFrame();
	}

	private void centerFrame() {

		Dimension windowSize = getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		setLocation(dx, dy);
	}

	private void init() {

		setTitle("IP Scanning Window");

		setBounds(100, 100, 305, 199);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(null);
	}

	private void loadComponents() {

		JLabel lblNewLabel = new JLabel("From");

		lblNewLabel.setBounds(20, 11, 61, 29);

		contentPanel.add(lblNewLabel);

		JLabel lblTo = new JLabel("To");

		lblTo.setBounds(20, 51, 61, 29);

		contentPanel.add(lblTo);

		fromIP = new JTextField("172.17.1.27");

		fromIP.setBounds(89, 11, 191, 23);

		contentPanel.add(fromIP);

		fromIP.setColumns(10);

		toIP = new JTextField("172.17.1.30");

		toIP.setColumns(10);

		toIP.setBounds(89, 51, 191, 23);

		contentPanel.add(toIP);

		JSeparator separator = new JSeparator();

		separator.setBounds(0, 85, 329, 2);

		contentPanel.add(separator);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Keep old values");

		chckbxNewCheckBox.setBounds(0, 87, 232, 23);

		contentPanel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Revalidate old values");

		chckbxNewCheckBox_1.setBounds(0, 113, 232, 23);

		contentPanel.add(chckbxNewCheckBox_1);

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(new ScanAction("Scan"));

		buttonPane.add(okButton);

		JButton cancelButton = new JButton(new CancelAction("Cancel"));

		buttonPane.add(cancelButton);
	}

	class ScanAction extends AbstractAction {

		/**
		 * 
		 */

		public ScanAction(String name) {
			putValue(Action.NAME, name);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			VPX_ScanWindow.this.setVisible(false);

			new ScanStatusWindow(fromIP.getText(), toIP.getText());

		}

	}

	class CancelAction extends AbstractAction {

		/**
		 * 
		 */

		public CancelAction(String name) {
			putValue(Action.NAME, name);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			VPX_ScanWindow.this.dispose();

		}

	}

	class ScanStatusWindow extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8556421629676554896L;

		private final JPanel contentPanel = new JPanel();

		private String fromIP, toIP;

		private JLabel lblNewLabel;

		private PingerThread scan;

		private JProgressBar progressBar;

		private JLabel lblNewLabel_1;

		/**
		 * Create the dialog.
		 */
		public ScanStatusWindow(String from_ip, String to_ip) {

			super(parent);

			this.fromIP = from_ip;

			this.toIP = to_ip;

			init();

			loadComponents();

			centerFrame();

			scan = new PingerThread();

			scan.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent event) {

					switch (event.getPropertyName()) {
					case "progress":

						progressBar.setIndeterminate(false);

						progressBar.setValue((Integer) event.getNewValue());

						break;
					case "state":

						switch ((StateValue) event.getNewValue()) {

						case DONE:

							try {
								VPXSystem vpx = (VPXSystem) scan.get();

								parent.reloadProcessorTree(vpx);

								ScanStatusWindow.this.dispose();

							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}

							break;

						case STARTED:

						case PENDING:

							break;
						}
						break;
					}
				}
			});

			scan.execute();

			setVisible(true);
		}

		private void init() {

			setTitle("Processing on Scan");

			setType(Type.NORMAL);

			setAlwaysOnTop(true);

			setModal(true);

			setBounds(100, 100, 431, 166);

			getContentPane().setLayout(new BorderLayout());

			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

			getContentPane().add(contentPanel, BorderLayout.CENTER);
		}

		private void loadComponents() {

			contentPanel.setLayout(new MigLayout("", "[523.00px]", "[14px][][][][][]"));

			lblNewLabel_1 = new JLabel("Detecting Processors");

			contentPanel.add(lblNewLabel_1, "cell 0 0 1 2");

			lblNewLabel = new JLabel("");

			contentPanel.add(lblNewLabel, "cell 0 4,alignx left,aligny top");

			progressBar = new JProgressBar(0, 100);

			progressBar.setValue(0);

			progressBar.setStringPainted(true);

			contentPanel.add(progressBar, "cell 0 5,growx");

			JPanel buttonPane = new JPanel();

			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton cancelButton = new JButton("Cancel");

			cancelButton.setActionCommand("Cancel");

			buttonPane.add(cancelButton);

		}

		private void centerFrame() {

			Dimension windowSize = getSize();

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			Point centerPoint = ge.getCenterPoint();

			int dx = centerPoint.x - windowSize.width / 2;

			int dy = centerPoint.y - windowSize.height / 2;

			setLocation(dx, dy);
		}

		class PingerThread extends SwingWorker<VPXSystem, Long> {

			@Override
			protected VPXSystem doInBackground() throws Exception {

				Thread.sleep(500);

				long st = VPXUtilities.getLongFromIP(fromIP);

				long en = VPXUtilities.getLongFromIP(toIP);

				long size = en - st;

				int ii = 1;
				for (long i = st; i <= en; i++) {

					publish(i);

					String s = VPXTCPConnector.connet(VPXUtilities.getIPFromLong(i));
					
					if(s != null){
						System.out.println(s);
					}
					
					
					long p = (ii + 1) * 100 / size;

					if (p <= 100) {
						setProgress(Integer.parseInt("" + p));

						Thread.sleep(250);

						lblNewLabel_1.setVisible(!lblNewLabel_1.isVisible());

						ii++;
					}
				}

				return new VPXSystem();
			}

			@Override
			protected void done() {

			}

			@Override
			protected void process(List<Long> chunks) {
				for (final Long string : chunks) {
					lblNewLabel.setText("IP Address : " + VPXUtilities.getIPFromLong(string));
				}
			}
		}
	}

}
