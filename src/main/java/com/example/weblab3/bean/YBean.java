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
public class YBean implements Serializable {
    private String value = "0";
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public void validateY(FacesContext facesContext, UIComponent uiComponent, Object object){
        if (object == null){
            FacesMessage message = new FacesMessage("Укажите Y");
            throw new ValidatorException(message);
        }
        String strObj = object.toString().trim();
        if (!strObj.matches("-?\\d+(\\.\\d+)?")) {
            FacesMessage message = new FacesMessage("Y должен быть числом");
            throw new ValidatorException(message);
        }
        float y = Float.parseFloat(strObj);
        if (y < -3 || y > 5){
            FacesMessage message = new FacesMessage("Y должен быть в диапазоне [-3;5]");
            throw new ValidatorException(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBean)) return false;

        YBean yBean = (YBean) o;

        return getValue() != null ? getValue().equals(yBean.getValue()) : yBean.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}

