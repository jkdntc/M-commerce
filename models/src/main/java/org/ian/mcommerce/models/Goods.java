package org.ian.mcommerce.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NamedQuery(name = "Goods.findAll", query = "SELECT b FROM Goods b")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "请填写标题")
    private String title;
    @NotNull(message = "请填写描述")
    private String description;
    @NotNull(message = "请填写作者")
    private String author;

    @OneToMany(mappedBy = "goods")
    private List<GoodsOrder> goodsOrders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<GoodsOrder> getGoodsOrders() {
        return goodsOrders;
    }

    public void setGoodsOrders(List<GoodsOrder> goodsOrders) {
        this.goodsOrders = goodsOrders;
    }
}