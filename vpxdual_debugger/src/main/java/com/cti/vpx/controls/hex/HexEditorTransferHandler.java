package com.cti.vpx.controls.hex;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.cti.vpx.util.VPXLogger;

/**
 * The default transfer handler for <code>HexEditor</code>s.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class HexEditorTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;

	public boolean canImport(JComponent comp, DataFlavor[] flavors) {
		HexEditor editor = (HexEditor) comp;
		if (!editor.isEnabled()) {
			return false;
		}
		return getImportFlavor(flavors, editor) != null;
	}

	protected Transferable createTransferable(JComponent c) {

		byte[] array = null;

		int size = 0;

		int length = 1;

		int end = 0;

		int start = 0;

		ByteArrayTransferable bat = null;

		HexEditor e = (HexEditor) c;

		int start1 = e.getSmallestSelectionIndex();

		int end1 = e.getLargestSelectionIndex();

		int currentModel = e.getCurrentModel();

		switch (currentModel) {

		case HexEditor.HEX8:

			end = end1;

			start = start1;

			size = (end1 - start1 + 1) * 1;

			length = 1;

			break;

		case HexEditor.HEX16:
		case HexEditor.SINGNEDINT16:
		case HexEditor.UNSINGNEDINT16:

			end = (end1 * 2) + 1;

			start = start1 * 2;

			size = (end1 - start1 + 1) * 2;

			length = length * 2;

			break;

		case HexEditor.HEX32:
		case HexEditor.SINGNEDINT32:
		case HexEditor.UNSINGNEDINT32:
		case HexEditor.UNSINGNEDFLOAT32:

			end = (end1 * 4) + 3;

			start = start1 * 4;

			size = (end1 - start1 + 1) * 4;

			length = length * 4;

			break;

		case HexEditor.HEX64:

			end = (end1 * 8) + 7;

			start = start1 * 8;

			size = (end1 - start1 + 1) * 8;

			length = length * 8;

			break;
		}

		array = new byte[size];

		for (int i = end; i >= start; i--) {

			array[i - start] = e.getByte(i);
		}

		bat = new ByteArrayTransferable(start, array);

		return bat;
	}

	protected void exportDone(JComponent source, Transferable data, int action) {
		if (action == MOVE) {
			ByteArrayTransferable bat = (ByteArrayTransferable) data;
			int offs = bat.getOffset();
			HexEditor e = (HexEditor) source;
			e.removeBytes(offs, bat.getLength());
		}
	}

	private DataFlavor getImportFlavor(DataFlavor[] flavors, HexEditor e) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].equals(DataFlavor.stringFlavor)) {
				return flavors[i];
			}
		}
		return null;
	}

	/**
	 * Returns what operations can be done on a hex editor (copy and move, or
	 * just copy).
	 *
	 * @param c
	 *            The <code>HexEditor</code>.
	 * @return The permitted operations.
	 */
	public int getSourceActions(JComponent c) {
		HexEditor e = (HexEditor) c;
		return e.isEnabled() ? COPY_OR_MOVE : COPY;
	}

	/**
	 * Imports data into a hex editor component.
	 *
	 * @param c
	 *            The <code>HexEditor</code> component.
	 * @param t
	 *            The data to be imported.
	 * @return Whether the data was successfully imported.
	 */
	public boolean importData(JComponent c, Transferable t) {

		HexEditor e = (HexEditor) c;
		boolean imported = false;

		DataFlavor flavor = getImportFlavor(t.getTransferDataFlavors(), e);
		if (flavor != null) {
			try {
				Object data = t.getTransferData(flavor);
				if (flavor.equals(DataFlavor.stringFlavor)) {
					String text = (String) data;
					byte[] bytes = text.getBytes();
					e.replaceSelection(bytes);
				}
			} catch (UnsupportedFlavorException ufe) {
				VPXLogger.updateError(ufe);
				ufe.printStackTrace(); // Never happens.
			} catch (IOException ioe) {
				VPXLogger.updateError(ioe);
				ioe.printStackTrace();
			}
		}

		return imported;

	}

}