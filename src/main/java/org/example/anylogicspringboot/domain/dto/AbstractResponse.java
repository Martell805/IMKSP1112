package org.example.anylogicspringboot.domain.dto;

import jakarta.xml.bind.annotation.XmlElement;


public abstract class AbstractResponse {
    @XmlElement(name = "type")
    private final ResponseTypes type = getType();

    public abstract ResponseTypes getType();
}
