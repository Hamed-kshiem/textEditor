import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;


public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    private StyledDocument styledDocument;

    private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl();

    private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);

    JScrollPane scroller ;


    public TextEditor() {
        // Set up the UI
        textArea = new JTextArea();
        UndoManager manager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(manager);
        scroller = new JScrollPane(textArea);
        scroller.setRowHeaderView(lineNumberComponent);
        this.getContentPane().add(scroller);
        lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.addMouseListener(new EditorMouseAdapter());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
/*        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }
            @Override
            public void removeUpdate(DocumentEvent arg0) {
                lineNumberComponent.adjustWidth();
            }
        });*/


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openFile();
            }
        });
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                saveFile();
            }
        });
        fileMenu.add(saveMenuItem);

        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                saveAsFile();
            }
        });
        fileMenu.add(saveAsMenuItem);

        JMenu fontMenu = new JMenu("Font");
        menuBar.add(fontMenu);

        JMenuItem fontSizeMenuItem = new JMenuItem("Font Size");
        fontSizeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontSize();
            }
        });
        fontMenu.add(fontSizeMenuItem);

        JMenuItem fontTypeMenuItem = new JMenuItem("Font Type");
        fontTypeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontType();
            }
        });
        fontMenu.add(fontTypeMenuItem);

        JMenuItem fontColorMenuItem = new JMenuItem("Font Color");
        fontColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontColor();
            }
        });
        fontMenu.add(fontColorMenuItem);

        JMenuItem fontBackgroundColorMenuItem = new JMenuItem("Font Background Color");
        fontBackgroundColorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontBackgroundColor();
            }
        });
        fontMenu.add(fontBackgroundColorMenuItem);

        JMenuItem fontBoldMenuItem = new JMenuItem("Font Bold");
        fontBoldMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontBold();
            }
        });
        fontMenu.add(fontBoldMenuItem);

        JMenuItem fontFontFamilyMenuItem = new JMenuItem("Font Family");
        fontFontFamilyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setFontFamily();
            }
        });
        fontMenu.add(fontFontFamilyMenuItem);

        JMenu searchMenu = new JMenu("Search");
        JMenuItem searchMenuItem = new JMenuItem("Advanced search");

        searchMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new Search(TextEditor.this.textArea);
            }
        });
        searchMenu.add(searchMenuItem);

        menuBar.add(searchMenu);

        JMenu EditMenu = new JMenu("Edit");
        JMenuItem EditMenuItem = new JMenuItem("Copy");
        EditMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textArea.copy();
            }
        });
        EditMenu.add(EditMenuItem);
        menuBar.add(EditMenu);

        JMenuItem EditMenuItem2 = new JMenuItem("Paste");
        EditMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textArea.paste();
            }
        });
        EditMenu.add(EditMenuItem2);

        JMenuItem EditMenuItem3 = new JMenuItem("Cut");
        EditMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textArea.cut();
            }
        });
        EditMenu.add(EditMenuItem3);

        JMenuItem EditMenuItem4 = new JMenuItem("Undo");
        EditMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                manager.undo();
            }
        });
        EditMenu.add(EditMenuItem4);

        JMenuItem EditMenuItem5 = new JMenuItem("Redo");
        EditMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                manager.redo();
            }
        });
        EditMenu.add(EditMenuItem5);

        JMenuItem EditMenuItem6 = new JMenuItem("Select All");
        EditMenuItem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textArea.selectAll();
            }
        });
        EditMenu.add(EditMenuItem6);

        JMenuItem EditMenuItem7 = new JMenuItem("Clear");
        EditMenuItem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textArea.setText("");
            }
        });
        EditMenu.add(EditMenuItem7);

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        // Set an higlighter to the JTextArea
        textArea.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                setTitle(new StringBuilder().append("TEXT EDITOR LVA SS | ").append("      Length: ").append(textArea.getText().length()).append("    Lines: ").append((textArea.getText() + "|").split("\n").length).append("    Words: ").append(textArea.getText().trim().split("\\s+").length).append(" ").toString());
            }


        });
    }



    private void saveAsFile() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter out = new FileWriter(file);
                out.write(textArea.getText());
                out.close();
            } catch (IOException e) {
            }
        }
    }

    private void setFontBold() {
        Font font = textArea.getFont();
        Map attributes = font.getAttributes();
        if (font.isBold()) {
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
        } else {
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        }
        textArea.setFont(font.deriveFont(attributes));
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                textArea.read(new FileReader(file.getAbsolutePath()), null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                textArea.write(new FileWriter(file.getAbsolutePath()));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setFontSize() {
        String[] fontSizes = {"8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"};
        String fontSize = (String) JOptionPane.showInputDialog(this,
                "Choose a font size", "Font Size",
                JOptionPane.QUESTION_MESSAGE, null, fontSizes, fontSizes[0]);
        if (fontSize != null) {
            Font font = textArea.getFont();
            textArea.setFont(new Font(font.getName(), font.getStyle(), Integer.parseInt(fontSize)));
        }

    }

    private void setFontType() {
        String fontType = JOptionPane.showInputDialog(this, "Enter font type");
        try {
            textArea.setFont(new Font(fontType, Font.PLAIN, 12));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid font type", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFontColor() {
        Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
        textArea.setForeground(newColor);
    }

    private void setFontBackgroundColor() {
        Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
        textArea.setBackground(newColor);
        }

    private void setFontFamily() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String font = (String) JOptionPane.showInputDialog(this, "Choose font", "Font", JOptionPane.PLAIN_MESSAGE, null, fonts, fonts[0]);
        try {
            textArea.setFont(new Font(font, Font.PLAIN, 12));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid font", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void paste() {
        textArea.paste();
    }

    private void copy() {
        textArea.copy();
    }

    private void cut() {
        textArea.cut();
    }

    private void selectAll() {
        textArea.selectAll();
    }


    private class LineNumberModelImpl implements LineNumberModel{


        @Override
        public int getNumberLines() {
            if (textArea == null){
                return 1;
            }
            return textArea.getLineCount();

        }

        @Override

        public Rectangle getLineRect(int line) {

            try{

                return textArea.modelToView(textArea.getLineStartOffset(line));

            }catch(BadLocationException e){

                e.printStackTrace();

                return new Rectangle();

            }

        }

    }


}
