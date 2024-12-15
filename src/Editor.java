import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class Editor extends JFrame implements KeyListener {
    private ClipboardInterface clipboard = RealClipboard.fromSystem();
    private JLabel status = new JLabel("");
    private JTextArea textArea = new JTextArea();
    private JButton copyButton = new JButton("Copy (F1)");
    private JButton cutButton = new JButton("Cut (F2)");
    private JButton pasteButton = new JButton("Paste (F3)");

    public Editor() {
        setTitle("Editor");
        setLayout(new BorderLayout());
        setSize(500, 400);

        this.status.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollpane = new JScrollPane(this.textArea);
        JPanel buttons = new JPanel();

        this.textArea.addKeyListener(this);
        this.textArea.setFont(this.textArea.getFont().deriveFont(20f));

        buttons.add(copyButton);
        buttons.add(cutButton);
        buttons.add(pasteButton);

        add(this.status, BorderLayout.NORTH);
        add(scrollpane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void replaceSelected(String s) {
        int start = this.textArea.getSelectionStart();
        int end = this.textArea.getSelectionEnd();

        this.textArea.replaceRange(s, start, end);
    }

    public void insertAfterCursor(String s) {
        this.textArea.insert(s, this.textArea.getCaretPosition());
    }

    public void setText(String s) {
        this.textArea.setText(s);
    }
    
    public String getText() {
        return this.textArea.getText();
    }

    public String getSelectedText() {
        return this.textArea.getSelectedText();
    }

    public void setStatus(String s) {
        String status = String.format("Status: %s", s);
        this.status.setText(status);
    }

    public ClipboardInterface getClipboard() {
        return this.clipboard;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F1:
                String copied_text = this.getSelectedText();

                if (copied_text == null || copied_text.isEmpty()) {
                    return;
                }

                this.clipboard.setContent(copied_text);
                this.setStatus("Copy");
                break;
            case KeyEvent.VK_F2:
                String cut_text = this.getSelectedText();

                if (cut_text == null || cut_text.isEmpty()) {
                    return;
                }

                this.getClipboard().setContent(cut_text);
                this.replaceSelected("");
                this.setStatus("Cut");
                break;
            case KeyEvent.VK_F3:
                String pasted_text = this.clipboard.getContent();

                if (pasted_text == null || pasted_text.isEmpty()) {
                    return;
                }

                this.insertAfterCursor(pasted_text);
                this.setStatus("Paste");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
}

class CopyButton extends JButton implements ActionListener {
    private Editor editor;

    public CopyButton(Editor editor) {
        this.editor = editor;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String copied_text = this.editor.getSelectedText();
    
        if(copied_text==null||copied_text.isEmpty()) {
            return;
        }
    
        this.editor.getClipboard().setContent(copied_text);this.editor.setStatus("Copy");
    
        return;
    }
}

class PasteButton extends JButton implements ActionListener {
    private Editor editor;

    public PasteButton(Editor editor) {
        this.editor = editor;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pasted_text = this.editor.getClipboard().getContent();

        if (pasted_text == null || pasted_text.isEmpty()) {
            return;
        }

        this.editor.insertAfterCursor(pasted_text);
        this.editor.setStatus("Paste");
    }
}

class CutButton extends JButton implements ActionListener {
    private Editor editor;

    public CutButton(Editor editor) {
        this.editor = editor;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cut_text = this.editor.getSelectedText();

        if (cut_text == null || cut_text.isEmpty()) {
            return;
        }

        this.editor.getClipboard().setContent(cut_text);
        this.editor.replaceSelected("");
        this.editor.setStatus("Cut");
    }
}