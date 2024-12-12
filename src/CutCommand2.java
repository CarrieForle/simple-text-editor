public class CutCommand2 extends Command {
    private CopyCommand copyCommand;

    public CutCommand2(Editor editor) {
        super(editor);
        this.copyCommand = new CopyCommand(editor);
    }

    @Override
    public boolean execute() {
        this.copyCommand.execute();
        this.editor.replaceSelected("");
        this.editor.setStatus("Cut");

        return true;
    }

    @Override
    public String toString() {
        return "Cut";
    }
}