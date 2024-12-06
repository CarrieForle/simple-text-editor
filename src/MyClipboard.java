public class MyClipboard implements ClipboardInterface {
    private String content;
    
    @Override
    public void setContent(String s) {
        this.content = s;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void clear() {
        this.content = "";
    }
}
