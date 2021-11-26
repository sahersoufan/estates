package com.springproject.estates.domain;


import javax.persistence.*;

@Entity
@Table(name = "estates")
public class EstateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Id;
    @Column(name = "name")
    String Name;
    @Column(name = "price")
    long Price;
    @Column(name = "number_shares")
    long NumberShares;
    @Column(name = "buyer_name")
    String BuyerName;
    @Column(name = "sale",columnDefinition = "boolean default false")
    Boolean sale;
    @Column(name = "selling_price")
    long SellingPrice;

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }



    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getNumberShares() {
        return NumberShares;
    }

    public void setNumberShares(long numberShares) {
        NumberShares = numberShares;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public long getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(long sellingPrice) {
        SellingPrice = sellingPrice;
    }



}
