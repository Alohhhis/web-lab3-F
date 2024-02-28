package com.example.weblab3.bean;

import com.example.weblab3.DB.DataBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Stateless
public class DataBaseEJB implements Serializable {
    @PersistenceContext(unitName = "ExamplePU")
    private EntityManager entityManager;
    private final int MIN_X = -3;
    private final int MAX_X = 3;
    private final int MIN_Y = -5;
    private final int MAX_Y = 3;
    private final float[] R_VALUES = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f,
            1.0f, 1.1f, 1.2f, 1.3f, 1.4f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f,
            2.0f, 2.1f, 2.2f, 2.3f, 2.4f, 2.5f, 2.6f, 2.7f, 2.8f, 2.9f,
            3.0f};
    private boolean isRInRange(float r) {
        for (float allowedR : R_VALUES) {
            if (allowedR == r) {
                return true;
            }
        }
        return false;
    }
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");



    public void add(String xStr, String yStr, String rStr) {
        try {
            float x = Float.parseFloat(xStr);
            float y = Float.parseFloat(yStr);
            float r = Float.parseFloat(rStr);
            long time = System.nanoTime();
            if (x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y && isRInRange(r)) {
                boolean result = AreaChecker.isHit(x, y, r);
                LocalTime currentTime = LocalTime.now();
                String curTime = currentTime.format(formatter);
                String scriptTime = String.format("%.2f", (double) (System.nanoTime() - time) * 0.0001);
                DataBase dataBase = new DataBase(x, y, r);
                dataBase.setResult(result);
                dataBase.setTime(curTime);
                dataBase.setScriptTime(scriptTime);
                entityManager.persist(dataBase);

            }
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("Неккоректные данные аргументов");
            throw new ValidatorException(message);
        }
    }
    public void addCanvas() {
        Map<String, String> values = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            float x = Float.parseFloat(values.get("x"));
            float y = Float.parseFloat(values.get("y"));
            float r = Float.parseFloat(values.get("r"));
            System.out.println("JAVA params:" + "x" + x + "y" + y + "r" + r);
            long time = System.nanoTime();
            if (x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y && isRInRange(r)) {
                boolean result = AreaChecker.isHit(x, y, r);
                LocalTime currentTime = LocalTime.now();
                String curTime = currentTime.format(formatter);
                String scriptTime = String.format("%.2f", (double) (System.nanoTime() - time) * 0.0001);
                DataBase dataBase = new DataBase(x, y, r);
                dataBase.setResult(result);
                dataBase.setTime(curTime);
                dataBase.setScriptTime(scriptTime);
                System.out.println("Before saving to DB");
                entityManager.persist(dataBase);
                System.out.println("After saving in DB");

            }
        } catch (NumberFormatException e) {
            System.out.println("---------->ERROR: Canvas ADD");
        }
    }

    public List<DataBase> getAllPoints() {
        Query query = entityManager.createQuery("SELECT point FROM DataBase point");
        return query.getResultList();
    }
    public void deleteAll() {
        Query query = entityManager.createQuery("DELETE  FROM DataBase");
        query.executeUpdate();
    }
    public void sendAllJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[]";
        try {
            json = objectMapper.writeValueAsString(getAllPoints());
            System.out.println(json);
            PrimeFaces.current().ajax().addCallbackParam("response", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            PrimeFaces.current().ajax().addCallbackParam("response", "[]");

        }
    }

}