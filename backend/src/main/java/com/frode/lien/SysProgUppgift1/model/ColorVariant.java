package com.frode.lien.SysProgUppgift1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorVariant {

    @JsonProperty
    public String colorName;
    @JsonProperty
    public List<String> images;
    @JsonProperty
    public List<SizeContainer> sizes;

    public ColorVariant(String colorName, List<SizeContainer> sizes, List<String> images) {
        this.colorName = colorName;
        this.sizes = sizes;
        this.images = images;
    }

    public ColorVariant() {
        this.images = new ArrayList<>();
        this.sizes = new ArrayList<>();
    }

    // NOTE: you can leave this method as it is; it's used in ProductRepository.java
    public void setImagesFromCSV(String csv) throws Exception {
        images = new ArrayList<>(Arrays.asList(csv.split(",")));
    }
}
