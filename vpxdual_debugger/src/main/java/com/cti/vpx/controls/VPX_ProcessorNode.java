/**
 * 
 */
package com.cti.vpx.controls;

import javax.swing.tree.DefaultMutableTreeNode;

import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.util.VPXUtilities;

/**
 * @author nathanr_kamal
 *
 */
public class VPX_ProcessorNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */

	public static final int SYSTEM_NODE = 0;

	public static final int SUBSYSTEM_NODE = 1;

	public static final int PROCESSOR_NODE = 2;

	private static final String P2020_NODE_NAME = "P2020";

	private static final String DSP1_NODE_NAME = "DSP1";

	private static final String DSP2_NODE_NAME = "DSP2";

	private PROCESSOR_LIST nodeType = null;

	private String nodeTypeString = "";

	private String nodeIP = "";

	private String nodeName = "";

	private String nodeUserObject = "";

	private String subSystemName = "";

	private boolean isRootNode = false;

	private boolean isSubSytemNode = false;

	private boolean isProcessorNode = false;

	private boolean isAlive = false;

	private boolean isAmplitude = false;

	private long respondedTime = 0;

	private long upTime = 0;

	private static final long serialVersionUID = -2910762841638542391L;

	public VPX_ProcessorNode(int level, String name) {

		setNodeLevel(level);

		setNodeName(name);

		setUserObject(name);

	}

	public VPX_ProcessorNode(String ip, String subSystem, PROCESSOR_LIST pType, boolean isAlive, boolean isAmplitude) {

		setNodeLevel(PROCESSOR_NODE);

		setSubSystemName(subSystem);

		setNodeIP(ip);

		setNodeType(pType);

		setAmplitude(isAmplitude);

		setStatus(isAlive);

	}

	public VPX_ProcessorNode(String ip, String subSystem, PROCESSOR_LIST pType, boolean isAlive) {

		this(ip, subSystem, pType, isAlive, false);

	}

	public void setNodeLevel(int leval) {

		if (leval == SYSTEM_NODE) {

			isRootNode = true;

			isSubSytemNode = false;

			isProcessorNode = false;

			setAllowsChildren(true);

		} else if (leval == SUBSYSTEM_NODE) {

			isRootNode = false;

			isSubSytemNode = true;

			isProcessorNode = false;

			setAllowsChildren(true);

		} else if (leval == PROCESSOR_NODE) {

			isRootNode = false;

			isSubSytemNode = false;

			isProcessorNode = true;

			setAllowsChildren(false);

		}

	}

	public PROCESSOR_LIST getNodeType() {

		return nodeType;
	}

	public void setNodeType(PROCESSOR_LIST nodeType) {

		this.nodeType = nodeType;

		if (nodeType == PROCESSOR_LIST.PROCESSOR_P2020) {

			this.nodeTypeString = P2020_NODE_NAME;

		} else if (nodeType == PROCESSOR_LIST.PROCESSOR_DSP1) {

			this.nodeTypeString = DSP1_NODE_NAME;

		} else if (nodeType == PROCESSOR_LIST.PROCESSOR_DSP2) {

			this.nodeTypeString = DSP2_NODE_NAME;
		}

		updateNodeName();

	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeUserObject() {
		return nodeUserObject;
	}

	public void setNodeUserObject(String nodeUserObject) {
		this.nodeUserObject = nodeUserObject;
	}

	public boolean isRootNode() {
		return isRootNode;
	}

	public void setRootNode(boolean isRootNode) {

		setNodeLevel(SYSTEM_NODE);
	}

	public boolean isSubSytemNode() {
		return isSubSytemNode;
	}

	public void setSubSytemNode(boolean isSubSytemNode) {

		setNodeLevel(SUBSYSTEM_NODE);
	}

	public boolean isProcessorNode() {
		return isProcessorNode;
	}

	public void setProcessorNode(boolean isProcessorNode) {

		setNodeLevel(PROCESSOR_NODE);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setStatus(boolean isAlive) {

		this.isAlive = isAlive;

		updateNodeStatus();

	}

	public void setStatus(boolean isAlive, boolean amplitude) {

		this.isAlive = isAlive;

		this.isAmplitude = amplitude;

		updateNodeStatus();

	}

	public String getNodeTypeString() {

		return nodeTypeString;
	}

	public void setNodeTypeString(String nodeTypeString) {
		this.nodeTypeString = nodeTypeString;
	}

	public boolean isAmplitude() {
		return isAmplitude;
	}

	public void setAmplitude(boolean isAmplitude) {

		this.isAmplitude = isAmplitude;

		updateNodeStatus();
	}

	public String getSubSystemName() {
		return subSystemName;
	}

	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}

	public long getRespondedTime() {
		return respondedTime;
	}

	public void setRespondedTime(long respondedTime) {
		this.respondedTime = respondedTime;

		if (this.upTime == 0) {

			this.upTime = respondedTime;
		}
	}

	private void updateNodeStatus() {

		createUserObject();

		setUserObject(nodeUserObject);

		// setToolTip();
	}

	public String getToolTip() {

		String str = "";

		if (isProcessorNode) {

			str = String.format(
					"<html>Node : Processor Node<br>" + "Processor : %s<br>" + "IP Address : %s<br>"
							+ "Sub System : %s<br>" + "Status  : %s<br>" + "Up Time : %s<br>"
							+ "Last Responded : %s</html>",
					nodeTypeString, nodeIP, subSystemName, getProcStatus(),
					VPXUtilities.getCurrentTime(5, (System.currentTimeMillis() - upTime)),
					VPXUtilities.getCurrentTime(5, respondedTime));
		}

		return str;
	}

	private String getProcStatus() {

		String sub = "";

		if (isAlive) {

			sub = sub + "<font face='Tahomaa' size='2.5' color='green'> Alive </font>" + "&nbsp;&nbsp;";

		} else {

			sub = sub + "<font face='Tahoma' size='2.5' color='red'> Not Alive</font>" + "&nbsp;&nbsp;";

		}

		return sub;

	}

	private void createUserObject() {

		if (nodeType == PROCESSOR_LIST.PROCESSOR_P2020) {

			nodeUserObject = createP2020NodeUserObject();

		} else if (nodeType == PROCESSOR_LIST.PROCESSOR_DSP1 || nodeType == PROCESSOR_LIST.PROCESSOR_DSP2) {

			nodeUserObject = createDSPNodeUserObject();
		}
	}

	private String createDSPNodeUserObject() {

		String sub = "<html>";

		if (isAlive) {

			sub = sub + "<font face='Tahomaa' size='2.5' color='green'>" + nodeName + "</font>" + "&nbsp;&nbsp;";

			if (isAmplitude) {

				sub = sub + "<font face='Tahomaa' size='2' color='green'>A</font>" + "&nbsp;&nbsp;";

			} else {

				sub = sub + "<font face='Tahomaa' size='2' color='red'>A</font>" + "&nbsp;&nbsp;";
			}

		} else {

			sub = sub + "<font face='Tahoma' size='2.5' color='red'>" + nodeName + "</font>" + "&nbsp;&nbsp;";

			sub = sub + "<font face='Tahomaa' size='2' color='red'>A</font>" + "&nbsp;&nbsp;";
		}

		sub = sub + "</html>";

		return sub;
	}

	private String createP2020NodeUserObject() {

		String sub = "<html>";

		if (isAlive) {

			sub = sub + "<font face='Tahoma' size='2.5' color='green'>" + nodeName + "</font>" + "&nbsp;&nbsp;";

		} else {

			sub = sub + "<font face='Tahoma' size='2.5' color='red'>" + nodeName + "</font>" + "&nbsp;&nbsp;";
		}

		sub = sub + "</html>";

		return sub;
	}

	private void updateNodeName() {

		this.nodeName = String.format("(%s)%s", nodeTypeString, nodeIP);

	}
}
