import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditorMouseAdapter extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        JTextArea textArea = (JTextArea) e.getSource();
        if (e.getButton() == MouseEvent.BUTTON3) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem copyItem = new JMenuItem("copy");
            copyItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent x) {
                    System.out.println("copy");
                    Object e = x.getSource();
                    System.out.println(textArea.getSelectedText());
                      StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
                      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                      clipboard.setContents(stringSelection, null);

                }
            });
            popupMenu.add(copyItem);
            JMenuItem pasteItem = new JMenuItem("paste");
            pasteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("paste");
                    textArea.paste();
                }
            });
            popupMenu.add(pasteItem);


            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
        }
    }
}
