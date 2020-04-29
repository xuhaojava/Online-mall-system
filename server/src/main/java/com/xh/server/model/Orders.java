package com.xh.server.model;

import java.util.Date;

public class Orders extends OrdersKey {
    private Date orderdate;

    private Float totalprice;

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }
}