import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Stack;

public class Editor extends JFrame implements KeyListener {
    private StackFrame stackFrame;
    private ClipboardInterface clipboard = RealClipboard.fromSystem();
    private JLabel status = new JLabel("");
    private JTextArea textArea = new JTextArea();
    private JButton copyButton = new JButton("Copy (F1)");
    private JButton cutButton = new JButton("Cut (F2)");
    private JButton pasteButton = new JButton("Paste (F3)");
    private JButton undoButton = new JButton("Undo (F4)");
    private JButton showStackButton = new JButton("History (F5)");
    private Stack<Command> history = new Stack<>();

    public Editor() {
        setTitle("Editor");
        setLayout(new BorderLayout());
        setSize(500, 400);

        this.stackFrame = new StackFrame(this.history);

        this.status.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollpane = new JScrollPane(this.textArea);
        JPanel buttons = new JPanel();

        this.copyButton.addActionListener((e) -> {
            this.executeCommand(new CopyCommand(this));
        });

        this.cutButton.addActionListener((e) -> {
            this.executeCommand(new CutCommand(this));
        });

        this.pasteButton.addActionListener((e) -> {
            this.executeCommand(new PasteCommand(this));
        });

        this.undoButton.addActionListener((e) -> {
            this.undo();
        });

        this.showStackButton.addActionListener((e) -> {
            this.executeCommand(new ShowStackCommand(this, this.stackFrame));
        });

        this.textArea.addKeyListener(this);
        this.textArea.setFont(this.textArea.getFont().deriveFont(20f));

        buttons.add(copyButton);
        buttons.add(cutButton);
        buttons.add(pasteButton);
        buttons.add(undoButton);
        buttons.add(showStackButton);

        add(this.status, BorderLayout.NORTH);
        add(scrollpane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeCommand(Command command) {
        if (command.execute()) {
            this.history.push(command);
        }
    }

    private void undo() {
        if (this.history.isEmpty()) {
            this.setStatus("Cannot undo further");
            return;
        }

        Command last_command = this.history.pop();
        last_command.undo();

        this.setStatus("Undo");
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
                this.executeCommand(new CopyCommand(this));
                break;
            case KeyEvent.VK_F2:
                this.executeCommand(new CutCommand(this));
                break;
            case KeyEvent.VK_F3:
                this.executeCommand(new PasteCommand(this));
                break;
            case KeyEvent.VK_F4:
                undo();
                break;
            case KeyEvent.VK_F5:
                this.executeCommand(new ShowStackCommand(this, this.stackFrame));
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