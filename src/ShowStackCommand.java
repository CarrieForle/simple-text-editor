import javax.swing.JFrame;

public class ShowStackCommand extends Command {
    private JFrame frame;
    
    public ShowStackCommand(Editor editor, JFrame frame) {
        super(editor);
        this.frame = frame;
    }

    @Override
    public boolean execute() {
        frame.setVisible(true);

        return false;
    }
}