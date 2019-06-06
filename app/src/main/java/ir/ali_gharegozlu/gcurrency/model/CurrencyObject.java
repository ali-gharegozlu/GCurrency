package ir.ali_gharegozlu.gcurrency.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CurrencyObject {

    @Id private long id;

    private String name;
    private String abbr;
    private String price;
    public boolean isCoin = false;

    public CurrencyObject(String name, String abbr, String price, boolean isCoin){
        //this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.price = price;
        this.isCoin = isCoin;
    }

    public CurrencyObject(){
        this.name = "نام ارز";
        this.abbr = "CUR";
        this.price = "000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
