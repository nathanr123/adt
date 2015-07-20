package com.cti.vpx.model;

import java.io.Serializable;

import com.cti.vpx.util.VPXUtilities;

public class BIST implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5873062208817223775L;

	// Test Detail
	private String test = "Built In Self Test";

	private String testType = "Full Test";

	private String testSubSystem = "Sub1";

	private String testP2020IP = "172.17.1.1";

	private String testDSP1IP = "172.17.1.2";

	private String testDSP2IP = "172.17.1.3";

	private String testDate = VPXUtilities.getCurrentTime(1);

	private String testTime = VPXUtilities.getCurrentTime(2);

	// Result detail
	private String resultTestNoofTests = "16";

	private String resultTestPassed = "11";

	private String resultTestFailed = "5";

	private String resultTestStartedAt = VPXUtilities.getCurrentTime(2);

	private String resultTestCompletedAt = VPXUtilities.getCurrentTime(2);

	private String resultTestDuration = "2 Minutes";

	private String resultTestStatus = "Success!";

	// P2020 Test Result
	private String resultP2020Processor = "PASS";

	private String resultP2020DDR3 = "PASS";

	private String resultP2020NORFlash = "PASS";

	private String resultP2020Ethernet = "PASS";

	private String resultP2020PCIe = "PASS";

	private String resultP2020SRIO = "PASS";

	private String resultP2020Temprature1 = "45";

	private String resultP2020Temprature2 = "46";

	private String resultP2020Temprature3 = "47";

	private String resultP2020Voltage1 = "3.3 V";

	private String resultP2020Voltage2 = "3.3 V";

	private String resultP2020Voltage3 = "3.3 V";

	private String resultP2020Voltage4 = "3.3 V";

	private String resultP2020Voltage5 = "3.3 V";

	private String resultP2020Voltage6 = "3.3 V";

	private String resultP2020Voltage7 = "3.3 V";

	// DSP 1 Test Result
	private String resultDSP1Processor = "PASS";

	private String resultDSP1DDR3 = "PASS";

	private String resultDSP1NAND = "PASS";

	private String resultDSP1NOR = "PASS";

	// DSP 2 Test Result
	private String resultDSP2Processor = "PASS";

	private String resultDSP2DDR3 = "PASS";

	private String resultDSP2NAND = "PASS";

	private String resultDSP2NOR = "PASS";

	public BIST() {
	}

	public BIST(String test, String testType, String testSubSystem, String testP2020IP, String testDSP1IP,
			String testDSP2IP, String testDate, String testTime, String resultNoofTests, String resultTestPassed,
			String resultTestFailed, String resultTestStartedAt, String resultTestCompletedAt,
			String resultTestDuration, String resultTestStatus, String resultresultP2020020Processor,
			String resultP2020DDR3, String resultP2020NORFlash, String resultP2020Ethernet, String resultP2020PCIe,
			String resultP2020SRIO, String resultP2020Temprature1, String resultP2020Temprature2,
			String resultP2020Temprature3, String resultP2020Voltage1, String resultP2020Voltage2,
			String resultP2020Voltage3, String resultP2020Voltage4, String resultP2020Voltage5,
			String resultP2020Voltage6, String resultP2020Voltage7, String resultDSP1Processor, String resultDSP1DDR3,
			String resultDSP1BAND, String resultDSP1NOR, String resultDSP2Processor, String resultDSP2DDR3,
			String resultDSP2BAND, String resultDSP2NOR) {
		super();
		this.test = test;
		this.testType = testType;
		this.testSubSystem = testSubSystem;
		this.testP2020IP = testP2020IP;
		this.testDSP1IP = testDSP1IP;
		this.testDSP2IP = testDSP2IP;
		this.testDate = testDate;
		this.testTime = testTime;
		this.resultTestNoofTests = resultNoofTests;
		this.resultTestPassed = resultTestPassed;
		this.resultTestFailed = resultTestFailed;
		this.resultTestStartedAt = resultTestStartedAt;
		this.resultTestCompletedAt = resultTestCompletedAt;
		this.resultTestDuration = resultTestDuration;
		this.resultTestStatus = resultTestStatus;
		this.resultP2020Processor = resultresultP2020020Processor;
		this.resultP2020DDR3 = resultP2020DDR3;
		this.resultP2020NORFlash = resultP2020NORFlash;
		this.resultP2020Ethernet = resultP2020Ethernet;
		this.resultP2020PCIe = resultP2020PCIe;
		this.resultP2020SRIO = resultP2020SRIO;
		this.resultP2020Temprature1 = resultP2020Temprature1;
		this.resultP2020Temprature2 = resultP2020Temprature2;
		this.resultP2020Temprature3 = resultP2020Temprature3;
		this.resultP2020Voltage1 = resultP2020Voltage1;
		this.resultP2020Voltage2 = resultP2020Voltage2;
		this.resultP2020Voltage3 = resultP2020Voltage3;
		this.resultP2020Voltage4 = resultP2020Voltage4;
		this.resultP2020Voltage5 = resultP2020Voltage5;
		this.resultP2020Voltage6 = resultP2020Voltage6;
		this.resultP2020Voltage7 = resultP2020Voltage7;
		this.resultDSP1Processor = resultDSP1Processor;
		this.resultDSP1DDR3 = resultDSP1DDR3;
		this.resultDSP1NAND = resultDSP1BAND;
		this.resultDSP1NOR = resultDSP1NOR;
		this.resultDSP2Processor = resultDSP2Processor;
		this.resultDSP2DDR3 = resultDSP2DDR3;
		this.resultDSP2NAND = resultDSP2BAND;
		this.resultDSP2NOR = resultDSP2NOR;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestSubSystem() {
		return testSubSystem;
	}

	public void setTestSubSystem(String testSubSystem) {
		this.testSubSystem = testSubSystem;
	}

	public String getTestP2020IP() {
		return testP2020IP;
	}

	public void setTestP2020IP(String testP2020IP) {
		this.testP2020IP = testP2020IP;
	}

	public String getTestDSP1IP() {
		return testDSP1IP;
	}

	public void setTestDSP1IP(String testDSP1IP) {
		this.testDSP1IP = testDSP1IP;
	}

	public String getTestDSP2IP() {
		return testDSP2IP;
	}

	public void setTestDSP2IP(String testDSP2IP) {
		this.testDSP2IP = testDSP2IP;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getResultTestNoofTests() {
		return resultTestNoofTests;
	}

	public void setResultTestNoofTests(String resultTestNoofTests) {
		this.resultTestNoofTests = resultTestNoofTests;
	}

	public String getResultTestPassed() {
		return resultTestPassed;
	}

	public void setResultTestPassed(String resultTestPassed) {
		this.resultTestPassed = resultTestPassed;
	}

	public String getResultTestFailed() {
		return resultTestFailed;
	}

	public void setResultTestFailed(String resultTestFailed) {
		this.resultTestFailed = resultTestFailed;
	}

	public String getResultTestStartedAt() {
		return resultTestStartedAt;
	}

	public void setResultTestStartedAt(String resultTestStartedAt) {
		this.resultTestStartedAt = resultTestStartedAt;
	}

	public String getResultTestCompletedAt() {
		return resultTestCompletedAt;
	}

	public void setResultTestCompletedAt(String resultTestCompletedAt) {
		this.resultTestCompletedAt = resultTestCompletedAt;
	}

	public String getResultTestDuration() {
		return resultTestDuration;
	}

	public void setResultTestDuration(String resultTestDuration) {
		this.resultTestDuration = resultTestDuration;
	}

	public String getResultTestStatus() {
		return resultTestStatus;
	}

	public void setResultTestStatus(String resultTestStatus) {
		this.resultTestStatus = resultTestStatus;
	}

	public String getResultP2020Processor() {
		return resultP2020Processor;
	}

	public void setResultP2020Processor(String resultP2020Processor) {
		this.resultP2020Processor = resultP2020Processor;
	}

	public String getResultP2020DDR3() {
		return resultP2020DDR3;
	}

	public void setResultP2020DDR3(String resultP2020DDR3) {
		this.resultP2020DDR3 = resultP2020DDR3;
	}

	public String getResultP2020NORFlash() {
		return resultP2020NORFlash;
	}

	public void setResultP2020NORFlash(String resultP2020NORFlash) {
		this.resultP2020NORFlash = resultP2020NORFlash;
	}

	public String getResultP2020Ethernet() {
		return resultP2020Ethernet;
	}

	public void setResultP2020Ethernet(String resultP2020Ethernet) {
		this.resultP2020Ethernet = resultP2020Ethernet;
	}

	public String getResultP2020PCIe() {
		return resultP2020PCIe;
	}

	public void setResultP2020PCIe(String resultP2020PCIe) {
		this.resultP2020PCIe = resultP2020PCIe;
	}

	public String getResultP2020SRIO() {
		return resultP2020SRIO;
	}

	public void setResultP2020SRIO(String resultP2020SRIO) {
		this.resultP2020SRIO = resultP2020SRIO;
	}

	public String getResultP2020Temprature1() {
		return resultP2020Temprature1;
	}

	public void setResultP2020Temprature1(String resultP2020Temprature1) {
		this.resultP2020Temprature1 = resultP2020Temprature1;
	}

	public String getResultP2020Temprature2() {
		return resultP2020Temprature2;
	}

	public void setResultP2020Temprature2(String resultP2020Temprature2) {
		this.resultP2020Temprature2 = resultP2020Temprature2;
	}

	public String getResultP2020Temprature3() {
		return resultP2020Temprature3;
	}

	public void setResultP2020Temprature3(String resultP2020Temprature3) {
		this.resultP2020Temprature3 = resultP2020Temprature3;
	}

	public String getResultP2020Voltage1() {
		return resultP2020Voltage1;
	}

	public void setResultP2020Voltage1(String resultP2020Voltage1) {
		this.resultP2020Voltage1 = resultP2020Voltage1;
	}

	public String getResultP2020Voltage2() {
		return resultP2020Voltage2;
	}

	public void setResultP2020Voltage2(String resultP2020Voltage2) {
		this.resultP2020Voltage2 = resultP2020Voltage2;
	}

	public String getResultP2020Voltage3() {
		return resultP2020Voltage3;
	}

	public void setResultP2020Voltage3(String resultP2020Voltage3) {
		this.resultP2020Voltage3 = resultP2020Voltage3;
	}

	public String getResultP2020Voltage4() {
		return resultP2020Voltage4;
	}

	public void setResultP2020Voltage4(String resultP2020Voltage4) {
		this.resultP2020Voltage4 = resultP2020Voltage4;
	}

	public String getResultP2020Voltage5() {
		return resultP2020Voltage5;
	}

	public void setResultP2020Voltage5(String resultP2020Voltage5) {
		this.resultP2020Voltage5 = resultP2020Voltage5;
	}

	public String getResultP2020Voltage6() {
		return resultP2020Voltage6;
	}

	public void setResultP2020Voltage6(String resultP2020Voltage6) {
		this.resultP2020Voltage6 = resultP2020Voltage6;
	}

	public String getResultP2020Voltage7() {
		return resultP2020Voltage7;
	}

	public void setResultP2020Voltage7(String resultP2020Voltage7) {
		this.resultP2020Voltage7 = resultP2020Voltage7;
	}

	public String getResultDSP1Processor() {
		return resultDSP1Processor;
	}

	public void setResultDSP1Processor(String resultDSP1Processor) {
		this.resultDSP1Processor = resultDSP1Processor;
	}

	public String getResultDSP1DDR3() {
		return resultDSP1DDR3;
	}

	public void setResultDSP1DDR3(String resultDSP1DDR3) {
		this.resultDSP1DDR3 = resultDSP1DDR3;
	}

	public String getResultDSP1NAND() {
		return resultDSP1NAND;
	}

	public void setResultDSP1NAND(String resultDSP1NAND) {
		this.resultDSP1NAND = resultDSP1NAND;
	}

	public String getResultDSP2NAND() {
		return resultDSP2NAND;
	}

	public void setResultDSP2NAND(String resultDSP2NAND) {
		this.resultDSP2NAND = resultDSP2NAND;
	}

	public String getResultDSP1NOR() {
		return resultDSP1NOR;
	}

	public void setResultDSP1NOR(String resultDSP1NOR) {
		this.resultDSP1NOR = resultDSP1NOR;
	}

	public String getResultDSP2Processor() {
		return resultDSP2Processor;
	}

	public void setResultDSP2Processor(String resultDSP2Processor) {
		this.resultDSP2Processor = resultDSP2Processor;
	}

	public String getResultDSP2DDR3() {
		return resultDSP2DDR3;
	}

	public void setResultDSP2DDR3(String resultDSP2DDR3) {
		this.resultDSP2DDR3 = resultDSP2DDR3;
	}

	public String getResultDSP2NOR() {
		return resultDSP2NOR;
	}

	public void setResultDSP2NOR(String resultDSP2NOR) {
		this.resultDSP2NOR = resultDSP2NOR;
	}
}
