public class CutCommand extends Command {
    public CutCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        String cut_text = this.editor.getSelectedText();

        if (cut_text == null || cut_text.isEmpty()) {
            return false;
        }

        this.backup();
        this.editor.getClipboard().setContent(cut_text);
        this.editor.replaceSelected("");
        this.editor.setStatus("Cut");

        return true;
    }

    @Override
    public String toString() {
        return "Cut";
    }
}