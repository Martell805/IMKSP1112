package org.example.anylogicspringboot.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataRequest")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class DataRequest {
    @XmlElement(required = true, nillable = false)
    public Integer scenarioNumber;

    public Integer getScenarioNumber() {
        return scenarioNumber;
    }

    public void setScenarioNumber(Integer scenarioNumber) {
        this.scenarioNumber = scenarioNumber;
    }
}
