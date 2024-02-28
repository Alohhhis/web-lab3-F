package com.example.weblab3.bean;

import com.example.weblab3.DB.DataBase;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.model.DataModel;
import jakarta.inject.Named;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private XBean xBean;
    private YBean yBean;
    private RBean rBean;
    private int selectedItemIndex;
    private DataModel dtmdl = null;

    @EJB
    private DataBaseEJB dataBaseEJB;

    public void addRow(String x, String y, String r) {
        dataBaseEJB.add(x, y, r);
    }

    public void addRowCanvas() {
        dataBaseEJB.addCanvas();
    }

    public List<DataBase> getResults() {
        return dataBaseEJB.getAllPoints();
    }

    public void deleteResults() {
        dataBaseEJB.deleteAll();
    }

    public void sendAllPoints() {
        dataBaseEJB.sendAllJson();
    }

}