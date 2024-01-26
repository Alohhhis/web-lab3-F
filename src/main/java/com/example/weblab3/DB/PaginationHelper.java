package com.example.weblab3.DB;

import jakarta.faces.model.DataModel;
//TODO СВОЯ ПИСЯ НЕ ВОНЯЕТ
public abstract class PaginationHelper {
    private int pageSize;
    private int page;
    public PaginationHelper(int pageSize){
        this.pageSize = pageSize;
    }
    public abstract int getItemsCount();
    public abstract DataModel createPageDataModel();
    public int getPageFirstItem(){
        return page*pageSize;
    }
}
