package hung.com.bookmanager.model;

public class Bill {
    public String id;
    public long date;

    public Bill(){

    }

    public Bill(String id, long date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
