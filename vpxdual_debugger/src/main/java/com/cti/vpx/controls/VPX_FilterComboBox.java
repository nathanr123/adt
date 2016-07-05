package com.cti.vpx.controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VPX_FilterComboBox extends JComboBox<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2249474895617722044L;

	private List<String> array;

	private int currentCaretPosition = 0;

	public VPX_FilterComboBox() {
	}

	private void addArray(List<String> array) {

		for (Iterator<String> iterator = array.iterator(); iterator.hasNext();) {

			addItem(iterator.next());
		}

		this.array = array;

		this.setEditable(true);

		final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();

		textfield.setFont(new JTextField().getFont());

		textfield.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent ke) {

				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {

					hidePopup();

				} else {

					SwingUtilities.invokeLater(new Runnable() {

						public void run() {

							currentCaretPosition = textfield.getCaretPosition();

							if (textfield.getSelectedText() == null) {

								textfield.setCaretPosition(0);

								comboFilter(textfield.getText());

								textfield.setCaretPosition(currentCaretPosition);
							}
						}
					});
				}
			}

		});

	}

	private void comboFilter(String enteredText) {

		List<String> filterArray = new ArrayList<String>();

		for (int i = 0; i < array.size(); i++) {

			if (array.get(i).toLowerCase().contains(enteredText.toLowerCase())) {

				filterArray.add(array.get(i));
			}
		}

		if (filterArray.size() > 0) {

			this.setModel(new DefaultComboBoxModel<Object>(filterArray.toArray()));

			this.setSelectedItem(enteredText);

			this.showPopup();
		} else {

			this.hidePopup();
		}
	}

	public void addMemoryVariables(Map<String, String> mem) {

		addArray(populateArray(mem));
	}

	public void updateMemoryVariables(Map<Integer, String> mem) {

		addArray(rePopulateArray(mem));
	}

	public void addMemoryVariables(List<String> mem) {

		addArray(mem);
	}

	public static List<String> rePopulateArray(Map<Integer, String> mem) {

		List<String> test = new ArrayList<String>();

		Iterator<Map.Entry<Integer, String>> entries = mem.entrySet().iterator();

		while (entries.hasNext()) {

			Map.Entry<Integer, String> entry = entries.next();

			test.add(entry.getValue());
		}

		return test;
	}

	private static List<String> populateArray(Map<String, String> mem) {

		List<String> test = new ArrayList<String>();

		Iterator<Map.Entry<String, String>> entries = mem.entrySet().iterator();

		while (entries.hasNext()) {

			Map.Entry<String, String> entry = entries.next();

			test.add(entry.getKey());

		}

		return test;
	}
}