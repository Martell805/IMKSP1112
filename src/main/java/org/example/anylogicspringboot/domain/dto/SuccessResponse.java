package org.example.anylogicspringboot.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SuccessResponse")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SuccessResponse extends AbstractResponse {
    @XmlElement(name = "message")
    private String message;

    public SuccessResponse() {
    }

    public SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public ResponseTypes getType() {
        return ResponseTypes.SUCCESS;
    }
}
