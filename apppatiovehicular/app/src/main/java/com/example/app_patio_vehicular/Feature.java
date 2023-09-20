package com.example.app_patio_vehicular;

import com.google.gson.annotations.SerializedName;

public class Feature {
    @SerializedName("geometry")
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
