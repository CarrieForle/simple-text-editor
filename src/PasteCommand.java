public class PasteCommand extends Command {
    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        String pasted_text = this.editor.getClipboard().getContent();

        if (pasted_text == null || pasted_text.isEmpty()) {
            return false;
        }

        this.backup();
        this.editor.insertAfterCursor(pasted_text);
        this.editor.setStatus("Paste");

        return true;
    }
}