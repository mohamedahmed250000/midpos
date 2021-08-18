package com.example.midpos;


public class Prodect {
    private String prodect_name;
    private String prodect_barcode;
    private String prodect_quntity;
    private String prodect_price;
    private String prodect_total_price;

    public Prodect(String prodect_name, String prodect_barcode, String prodect_quntity, String prodect_price, String prodect_total_price) {
        this.prodect_name = prodect_name;
        this.prodect_barcode = prodect_barcode;
        this.prodect_quntity = prodect_quntity;
        this.prodect_price = prodect_price;
        this.prodect_total_price = prodect_total_price;
    }

    public String getProdect_photo() {
        return prodect_total_price;
    }

    public String getProdect_total_price() {
        return prodect_total_price;
    }

    public void setProdect_total_price(String prodect_total_price) {
        this.prodect_total_price = prodect_total_price;
    }

    public void setProdect_photo(String prodect_photo) {
        this.prodect_total_price = prodect_photo;
    }

    public String getProdect_name() {
        return prodect_name;
    }

    public void setProdect_name(String prodect_name) {
        this.prodect_name = prodect_name;
    }

    public String getProdect_barcode() {
        return prodect_barcode;
    }

    public void setProdect_barcode(String prodect_barcode) {
        this.prodect_barcode = prodect_barcode;
    }

    public String getProdect_quntity() {
        return prodect_quntity;
    }

    public void setProdect_quntity(String prodect_quntity) {
        this.prodect_quntity = prodect_quntity;
    }

    public String getProdect_price() {
        return prodect_price;
    }

    public void setProdect_price(String prodect_price) {
        this.prodect_price = prodect_price;
    }
}

