package com.example.apifinalproject.enums;

public enum CategoryTypes {
    populationServices("خدمات السكان"),
    salaries("رواتب"),
    purchases("مشتريات"),
    maintenance("صيانة");
      public final String value ;

    CategoryTypes(String value) {
        this.value = value;
    }
}
