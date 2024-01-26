package com.example.weblab3.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class RBean implements Serializable {
    private String value = "0";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public void validateR(FacesContext facesContext, UIComponent uiComponent, Object object){
        if (object == null){
            FacesMessage message = new FacesMessage("Укажите R");
            throw new ValidatorException(message);
        }
        String strObj = object.toString().trim();
        if (!strObj.matches("-?\\d+(\\.\\d+)?")) {
            FacesMessage message = new FacesMessage("R должен быть числом");
            throw new ValidatorException(message);
        }
        float r = Float.parseFloat(strObj);
        if (r < 0.1|| r > 3){
            FacesMessage message = new FacesMessage("R должен быть в диапазоне [0.1;3]");
            throw new ValidatorException(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RBean)) return false;

        RBean rBean = (RBean) o;

        return getValue() != null ? getValue().equals(rBean.getValue()) : rBean.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}


