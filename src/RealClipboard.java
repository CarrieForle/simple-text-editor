import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class RealClipboard implements ClipboardInterface {
    private Clipboard clipboard;

    private RealClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public static RealClipboard fromSystem() {
        return new RealClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
    }

    @Override
    public void setContent(String s) {
        StringSelection ss = new StringSelection(s);
        this.clipboard.setContents(ss, ss);
    }

    @Override
    public String getContent() {
        // Clipboard might not be available or the clipboard data is not text
        try {
            return (String)this.clipboard.getData(DataFlavor.stringFlavor);
        } catch(Exception e) {
            return null;
        }
    }
    
    @Override
    public void clear() {
        setContent("");
    }
}
