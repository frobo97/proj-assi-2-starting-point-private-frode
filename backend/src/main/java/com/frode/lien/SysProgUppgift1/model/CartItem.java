package com.frode.lien.SysProgUppgift1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem {
    @JsonProperty
    public int productId;
    @JsonProperty
    public String title;
    @JsonProperty
    public String color;
    @JsonProperty
    public String size;
    @JsonProperty
    public String previewImage;
    @JsonProperty
    public int quantity;

    public CartItem() {
    }

    public CartItem(int productId, String title, String color, String size, String previewImage) {
        this.productId = productId;
        this.title = title;
        this.color = color;
        this.size = size;
        this.previewImage = previewImage;
    }

    public CartItem(int productId, String title, String color, String size, String previewImage, int quantity) {
        this.productId = productId;
        this.title = title;
        this.color = color;
        this.size = size;
        this.previewImage = previewImage;
        this.quantity = quantity;
    }
}
