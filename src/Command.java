public abstract class Command {
    protected Editor editor;
    private String old_state;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public void backup() {
        this.old_state = this.editor.getText();
    }

    public void undo() {
        this.editor.setText(this.old_state);
    }

    public abstract boolean execute();
}