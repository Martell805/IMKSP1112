package org.example.anylogicspringboot.domain.dto;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "ErrorResponse")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ErrorResponse extends AbstractResponse {
    @XmlElement(name = "message")
    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public ResponseTypes getType() {
        return ResponseTypes.ERROR;
    }
}
