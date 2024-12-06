public class CopyCommand extends Command {
    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        String copied_text = this.editor.getSelectedText();

        if (copied_text == null || copied_text.isEmpty()) {
            return false;
        }

        this.backup();
        this.editor.getClipboard().setContent(copied_text);
        this.editor.setStatus("Copy");

        return false;
    }
}