package samples;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class OffsetTest {
  public static void main(String[] args) {
    JTextArea ta = new JTextArea();
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(ta);

    // Add three lines of text to the JTextArea.
    ta.append("The first line.\n");
    ta.append("Line Two!\n");
    ta.append("This is the 3rd line of this document.");

    // Layout . . .
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(scroll, java.awt.BorderLayout.CENTER);
    f.setSize(150, 150);
    f.setVisible(true);
  }
}