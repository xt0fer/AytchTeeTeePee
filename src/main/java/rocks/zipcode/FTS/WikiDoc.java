package rocks.zipcode.FTS;

public class WikiDoc {
    private String title;
    private String url;
    private String text;

    public WikiDoc(String ti, String u, String tx) {
        this.title = ti;
        this.url = u;
        this.text = tx;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
