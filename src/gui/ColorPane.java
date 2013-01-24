package gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ColorPane extends JTextPane {

	public void appendNaive(Color c, String s) {
		SimpleAttributeSet aset = new SimpleAttributeSet();
		StyleConstants.setForeground(aset, c);
		int len = getText().length();
		setCaretPosition(len);
		setCharacterAttributes(aset, false);
		replaceSelection(s);
	}

	public void append(Color c, String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground, c);
		int len = getDocument().getLength();
		setCaretPosition(len);
		setCharacterAttributes(aset, false);
		replaceSelection(s);
	}
}
