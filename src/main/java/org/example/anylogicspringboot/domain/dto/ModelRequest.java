package org.example.anylogicspringboot.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ModelRequest")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ModelRequest {
    @XmlElement(required = true, nillable = false)
    public Integer scenarioNumber;
    @XmlElement(required = true, nillable = false)
    public Double drillingRate;
    @XmlElement(required = true, nillable = false)
    public Double oilPrice;
    @XmlElement(required = true, nillable = false)
    public Double exchangeRate;

    public Integer getScenarioNumber() {
        return scenarioNumber;
    }

    public void setScenarioNumber(Integer scenarioNumber) {
        this.scenarioNumber = scenarioNumber;
    }

    public Double getDrillingRate() {
        return drillingRate;
    }

    public void setDrillingRate(Double drillingRate) {
        this.drillingRate = drillingRate;
    }

    public Double getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(Double oilPrice) {
        this.oilPrice = oilPrice;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
