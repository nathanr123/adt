package samples;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

public class CloseButtonTabbedPane extends JTabbedPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5034720011424102704L;

	public CloseButtonTabbedPane() {
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {

		super.addTab(title, icon, component, tip);

		if (getTabCount() > 1) {
			int count = this.getTabCount() - 1;

			setTabComponentAt(count, new CloseButtonTab(component, title, icon));
		}
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		addTab(title, icon, component, null);
	}

	@Override
	public void addTab(String title, Component component) {
		addTab(title, null, component);
	}

	public class CloseButtonTab extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -925039726902932471L;

		public CloseButtonTab(final Component tab, String title, Icon icon) {
			setOpaque(false);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
			setLayout(flowLayout);
			setVisible(true);

			JLabel jLabel = new JLabel(title);
			jLabel.setIcon(icon);
			add(jLabel);
			JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			button.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					JTabbedPane tabbedPane = (JTabbedPane) getParent().getParent();
					tabbedPane.remove(tab);
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				}

				public void mouseExited(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				}
			});
			add(button);
		}
	}
}