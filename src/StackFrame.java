import javax.swing.*;
import java.util.Stack;

public class StackFrame extends JFrame {
    private Stack<Command> history;
    private JList<Command> history_list;

    public StackFrame(Stack<Command> history) {
        this.history = history;
        this.history_list = new JList<>(this.history);

        JScrollPane scrollPane = new JScrollPane(this.history_list);
        scrollPane.setSize(400, 400);

        Timer t = new Timer(0, e -> {
            history_list.setListData(history);
        });

        t.start();

        add(scrollPane);
        pack();
        setTitle("Command History Stack");
        setLayout(null);
        setLocationRelativeTo(null);
    }
}