import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    int startIndex=0;
    int select_start=-1;
    JLabel lab1;
    JTextField textF;
    JButton findBtn, findNext, cancel;
    private JTextArea txt;

    public Search(JTextArea text) {
        this.txt = text;

        lab1 = new JLabel("Find:");
        textF = new JTextField(30);
        findBtn = new JButton("Find");
        findNext = new JButton("Find Next");
        cancel = new JButton("Cancel");

        // Set Layout NULL
        setLayout(null);

        // Set the width and height of the label
        int labWidth = 80;
        int labHeight = 20;

        // Adding labels
        lab1.setBounds(10,10, labWidth, labHeight);
        add(lab1);
        textF.setBounds(10+labWidth, 10, 120, 20);
        add(textF);

        // Adding buttons
        findBtn.setBounds(10+labWidth, 42, 120, 20);
        add(findBtn);
        findBtn.addActionListener(this);

        findNext.setBounds(10+labWidth, 64, 120, 20);
        add(findNext);
        findNext.addActionListener(this);

        cancel.setBounds(10+labWidth, 86, 120, 20);
        add(cancel);
        cancel.addActionListener(this);


        // Set the width and height of the window
        int width = 260;
        int height = 160;

        // Set size window
        setSize(width,height);

        // center the frame on the frame
        setLocationRelativeTo(txt);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void find() {
        select_start = txt.getText().indexOf(textF.getText().toLowerCase());
        if(select_start == -1)
        {
            startIndex = 0;
            JOptionPane.showMessageDialog(null, "Could not find \"" + textF.getText() + "\"!");
            return;
        }
        if(select_start == txt.getText().lastIndexOf(textF.getText().toLowerCase()))
        {
            startIndex = 0;
        }
        int select_end = select_start + textF.getText().length();
        txt.select(select_start, select_end);
    }

    public void findNext() {
        String selection = txt.getSelectedText();
        try
        {
            selection.equals("");
        }
        catch(NullPointerException e)
        {
            selection = textF.getText();
            try
            {
                selection.equals("");
            }
            catch(NullPointerException e2)
            {
                selection = JOptionPane.showInputDialog("Find:");
                textF.setText(selection);
            }
        }
        try
        {
            int select_start = txt.getText() .indexOf(selection , startIndex);
            int select_end = select_start+selection.length();
            txt.select(select_start, select_end);
            startIndex = select_end+1;

            if(select_start == txt.getText().lastIndexOf(selection.toLowerCase()))
            {
                startIndex = 0;
            }
        }
        catch(NullPointerException e)
        {}
    }





    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == findBtn)
        {
            find();
        }
        else if(e.getSource() == findNext)
        {
            findNext();
        }
        else if(e.getSource() == cancel)
        {
            this.setVisible(false);
        }
    }
}
