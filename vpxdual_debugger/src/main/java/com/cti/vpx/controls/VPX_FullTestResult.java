package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP_COMMAND;
import com.cti.vpx.util.VPXUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VPX_FullTestResult extends JDialog {

	private static final long serialVersionUID = 622154430723814128L;

	private final JPanel contentPanel = new JPanel();

	TitledBorder border = new TitledBorder(null, "Full Test", TitledBorder.LEADING, TitledBorder.TOP, null, null);

	/**
	 * Launch the application.
	 */
	JLabel lblT1[] = new JLabel[24];

	private ATP_COMMAND atp;

	private String ip;

	private long start, end;

	private JLabel lblDateVal;

	private JLabel lblProcessorVal;

	private JLabel lblIPVal;

	private JLabel lblTimeVal;

	private JLabel lblTestTypeVal;

	private JLabel lblTestStarted;

	private JLabel lblTestCompleted;

	private JLabel lblTestDuration;

	private JLabel lblTestsPassedCount;

	private JLabel lblTestsFailedCount;

	private JLabel lblStatusDetail;

	private JLabel lblTotNoofTests;

	public static void main(String[] args) {
		try {
			VPX_FullTestResult dialog = new VPX_FullTestResult(null, null, 0, 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_FullTestResult(ATP_COMMAND cmd, String ip, long start, long end) {
		this.atp = cmd;

		this.ip = ip;

		this.start = start;

		this.end = end;

		init();

		loadComponents();

		loadLabels();
		
		centerFrame();

		setVisible(true);
	}

	private void init() {

		setBounds(100, 100, 658, 723);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setIconImage(VPXUtilities.getAppIcon());
	}

	private void loadComponents() {

		JPanel panel = new JPanel();
		panel.setBorder(border);
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 4));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Test Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setPreferredSize(new Dimension(10, 150));
		contentPanel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(null);

		JLabel lblTest = new JLabel("Test");

		lblTest.setPreferredSize(new Dimension(60, 20));
		lblTest.setBounds(10, 21, 103, 21);
		panel_1.add(lblTest);

		JLabel lblTestVal = new JLabel("Built In Self Test");

		lblTestVal.setPreferredSize(new Dimension(46, 20));
		lblTestVal.setBounds(123, 21, 162, 21);
		panel_1.add(lblTestVal);

		JLabel lblDate = new JLabel("Tested Date");

		lblDate.setBounds(316, 63, 103, 21);
		panel_1.add(lblDate);

		lblDateVal = new JLabel("");
		lblDateVal.setBounds(442, 63, 103, 21);
		panel_1.add(lblDateVal);

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setPreferredSize(new Dimension(46, 20));
		lblProcessor.setBounds(10, 63, 103, 21);
		panel_1.add(lblProcessor);

		lblProcessorVal = new JLabel("");
		lblProcessorVal.setPreferredSize(new Dimension(46, 20));
		lblProcessorVal.setBounds(123, 63, 103, 21);
		panel_1.add(lblProcessorVal);

		JLabel lblIP = new JLabel("IP Address");

		lblIP.setPreferredSize(new Dimension(46, 20));
		lblIP.setBounds(10, 105, 103, 21);
		panel_1.add(lblIP);

		lblIPVal = new JLabel("");
		lblIPVal.setPreferredSize(new Dimension(46, 20));
		lblIPVal.setBounds(123, 105, 103, 21);
		panel_1.add(lblIPVal);

		JLabel lblTime = new JLabel("Tested Time");

		lblTime.setBounds(316, 105, 103, 21);
		panel_1.add(lblTime);

		lblTimeVal = new JLabel("");
		lblTimeVal.setBounds(442, 105, 103, 21);
		panel_1.add(lblTimeVal);

		JLabel lblTestType = new JLabel("Test Type");

		lblTestType.setBounds(316, 21, 103, 21);
		panel_1.add(lblTestType);

		lblTestTypeVal = new JLabel("");
		lblTestTypeVal.setBounds(442, 21, 103, 21);
		panel_1.add(lblTestTypeVal);

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 150));
		panel_2.setBorder(new TitledBorder(null, "Result Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(null);

		JLabel lblTotalNoTests = new JLabel("Total No of Tests");
		lblTotalNoTests.setBounds(10, 20, 134, 21);
		panel_2.add(lblTotalNoTests);

		lblTotNoofTests = new JLabel("");
		lblTotNoofTests.setBounds(165, 20, 107, 21);
		panel_2.add(lblTotNoofTests);

		JLabel lblTestStartedAr = new JLabel("Test Started At");
		lblTestStartedAr.setBounds(10, 54, 134, 21);
		panel_2.add(lblTestStartedAr);

		lblTestStarted = new JLabel("");
		lblTestStarted.setBounds(165, 54, 107, 21);
		panel_2.add(lblTestStarted);

		JLabel lblTestCompletedAt = new JLabel("Test Completed At");
		lblTestCompletedAt.setBounds(10, 88, 134, 21);
		panel_2.add(lblTestCompletedAt);

		lblTestCompleted = new JLabel("");
		lblTestCompleted.setBounds(165, 88, 107, 21);
		panel_2.add(lblTestCompleted);

		JLabel lblTotalTestDuration = new JLabel("Total Test Duration");
		lblTotalTestDuration.setBounds(10, 122, 134, 21);
		panel_2.add(lblTotalTestDuration);

		lblTestDuration = new JLabel("");
		lblTestDuration.setBounds(165, 122, 465, 21);
		panel_2.add(lblTestDuration);

		JLabel lblNoOfTestsPassed = new JLabel("No of Tests Passed");
		lblNoOfTestsPassed.setBounds(287, 20, 134, 21);
		panel_2.add(lblNoOfTestsPassed);

		JLabel lblNoOfTestsFailed = new JLabel("No of Tests Failed");
		lblNoOfTestsFailed.setBounds(287, 54, 134, 21);
		panel_2.add(lblNoOfTestsFailed);

		JLabel lblTestStatus = new JLabel("Test Status");
		lblTestStatus.setBounds(287, 88, 134, 21);
		panel_2.add(lblTestStatus);

		lblTestsPassedCount = new JLabel("");
		lblTestsPassedCount.setBounds(447, 20, 107, 21);
		panel_2.add(lblTestsPassedCount);

		lblTestsFailedCount = new JLabel("");
		lblTestsFailedCount.setBounds(447, 54, 107, 21);
		panel_2.add(lblTestsFailedCount);

		lblStatusDetail = new JLabel("");
		lblStatusDetail.setBounds(447, 88, 107, 21);
		panel_2.add(lblStatusDetail);

		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		for (int i = 0; i < 24; i++) {
			lblT1[i] = new JLabel("");
			if (i % 2 != 0) {
				lblT1[i].setFont(f);

			}
			panel.add(lblT1[i]);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Close");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VPX_FullTestResult.this.dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

	}

	private void loadLabels() {
		// P2020

		int pass = 0;

		int fail = 0;

		if (atp.params.testType.get() == ATP.TEST_P2020_FULL) {

			setTitle("P2020 Full Test");

			border.setTitle("P2020 Full Test");

			lblDateVal.setText(VPXUtilities.getCurrentTime(1));

			lblTimeVal.setText(VPXUtilities.getCurrentTime(2));

			lblIPVal.setText(ip);

			lblProcessorVal.setText("P2020");

			lblTestTypeVal.setText("Full Test");

			lblT1[0].setText("CPU");

			if ((atp.params.testinfo.RESULT_P2020_PROCESSOR.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[1].setText("PASS");
				pass++;
			} else {
				lblT1[1].setText("FAIL");
				fail++;
			}
			lblT1[2].setText("Memory");

			if ((atp.params.testinfo.RESULT_P2020_MEMORY.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[3].setText("PASS");
				pass++;
			} else {
				lblT1[3].setText("FAIL");
				fail++;
			}

			lblT1[4].setText("Flash");

			if ((atp.params.testinfo.RESULT_P2020_FLASH.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[5].setText("PASS");
				pass++;
			} else {
				lblT1[5].setText("FAIL");
				fail++;
			}
			lblT1[6].setText("RTC");

			if ((atp.params.testinfo.RESULT_P2020_RTC.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[7].setText("PASS");
				pass++;
			} else {
				lblT1[7].setText("FAIL");
				fail++;
			}

			lblT1[8].setText("USB");

			if ((atp.params.testinfo.RESULT_P2020_USB.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[9].setText("PASS");
				pass++;
			} else {
				lblT1[9].setText("FAIL");
				fail++;
			}

			lblT1[10].setText("Ethernet");

			if ((atp.params.testinfo.RESULT_P2020_ETHERNET.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[11].setText("PASS");
				pass++;
			} else {
				lblT1[11].setText("FAIL");
				fail++;
			}

			lblT1[12].setText("List PCI");

			if ((atp.params.testinfo.RESULT_P2020_LISTPCI.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[13].setText("PASS");
				pass++;
			} else {
				lblT1[13].setText("FAIL");
				fail++;
			}

			lblT1[14].setText("Status");

			if ((atp.params.testinfo.RESULT_P2020_SATATUS.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[15].setText("PASS");
				pass++;
			} else {
				lblT1[15].setText("FAIL");
				fail++;
			}

			lblT1[16].setText("Audio");

			if ((atp.params.testinfo.RESULT_P2020_AUDIO.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[17].setText("PASS");
				pass++;
			} else {
				lblT1[17].setText("FAIL");
				fail++;
			}

			lblT1[18].setText("PMC XMC");

			if ((atp.params.testinfo.RESULT_P2020_PMCXMC.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[19].setText("PASS");
				pass++;
			} else {
				lblT1[19].setText("FAIL");
				fail++;
			}

			lblT1[20].setText("SRIO Loop");

			if ((atp.params.testinfo.RESULT_P2020_SRIOLOOP.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[21].setText("PASS");
				pass++;
			} else {
				lblT1[21].setText("FAIL");
				fail++;
			}

			lblT1[22].setText("Temprature");

			if ((atp.params.testinfo.RESULT_P2020_TEMP.get() == ATP.TEST_RESULT_PASS)) {
				lblT1[23].setText("PASS");
				pass++;
			} else {
				lblT1[23].setText("FAIL");
				fail++;
			}

			lblTestStarted.setText(VPXUtilities.getCurrentTime(3, start));

			lblTestCompleted.setText(VPXUtilities.getCurrentTime(3, end));

			System.out.println(end - start);
			lblTestDuration.setText(VPXUtilities.friendlyTimeDiff(end - start));

			lblTestsFailedCount.setText(fail + " Failed");

			lblTestsPassedCount.setText(pass + " Passed");

			lblTotNoofTests.setText((fail + pass) + " Tests");

			lblStatusDetail.setText("Completed !");
		} else {
			border.setTitle("DSP Full Test");
			lblT1[0].setText("");
			lblT1[1].setText("");
			lblT1[2].setText("");
			lblT1[3].setText("");
			lblT1[4].setText("");
			lblT1[5].setText("");
			lblT1[6].setText("");
			lblT1[7].setText("");
			lblT1[8].setText("");
			lblT1[9].setText("");
			lblT1[10].setText("");
			lblT1[11].setText("");
			lblT1[12].setText("");
			lblT1[13].setText("");
			lblT1[14].setText("");
			lblT1[15].setText("");
			lblT1[16].setText("");
			lblT1[17].setText("");
			lblT1[18].setText("");
			lblT1[19].setText("");
			lblT1[20].setText("");
			lblT1[21].setText("");
			lblT1[22].setText("");
			lblT1[23].setText("");
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
