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
public class XBean implements Serializable {
    private String value = "0";
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public void validateX(FacesContext facesContext, UIComponent uiComponent, Object object){
        if (object == null){
            FacesMessage message = new FacesMessage("Укажите X");
            throw new ValidatorException(message);
        }
        String strObj = object.toString().trim();
        if (!strObj.matches("-?\\d+(\\.\\d+)?")) {
            FacesMessage message = new FacesMessage("x должен быть числом");
            throw new ValidatorException(message);
        }
        float x = Float.parseFloat(strObj);
        if (x < -3 || x > 3){
            FacesMessage message = new FacesMessage("X должен быть в диапазоне [-3;3]");
            throw new ValidatorException(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XBean)) return false;

        XBean xBean = (XBean) o;

        return getValue() != null ? getValue().equals(xBean.getValue()) : xBean.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
