package Model;

/**
 * Created by Jose Mario on 22/05/2017.
 */


public class Noticia
{
    private String _name;
    private String _newsImageURL;
    private String _url;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_newsImageURL() {
        return _newsImageURL;
    }

    public void set_newsImageURL(String _newsImageURL) {
        this._newsImageURL = _newsImageURL;
    }

    public String get_url()
    {
        return _url;
    }

    public void set_url(String _url)
    {
        this._url = _url;
    }
}
