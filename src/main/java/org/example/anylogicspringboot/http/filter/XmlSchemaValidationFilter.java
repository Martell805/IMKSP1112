package org.example.anylogicspringboot.http.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.example.anylogicspringboot.domain.dto.AbstractResponse;
import org.example.anylogicspringboot.domain.dto.ErrorResponse;
import org.example.anylogicspringboot.domain.dto.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class XmlSchemaValidationFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Resource schemaResource;

    public XmlSchemaValidationFilter(@Value("classpath:schemas.xsd") Resource schemaResource) {
        this.schemaResource = schemaResource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!MediaType.APPLICATION_XML_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
            log.debug("Request not produce xml value. Skipping schema validation");
            filterChain.doFilter(request, response);
            return;
        }

        request = new HttpServletCacheWrapper(request);
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaResource.getFile());
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new BufferedInputStream(request.getInputStream())));
            filterChain.doFilter(request, response);
        } catch (SAXParseException e) {
            filterChain.doFilter(request, response);

        } catch (SAXException ignored) {
        }
    }

    private void writeResponse(String message, Writer writer) {
        try {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessages(List.of(message));

            JAXBContext jaxbContext = JAXBContext.newInstance(
                    ErrorResponse.class,
                    SuccessResponse.class
            );
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaResource.getFile());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setSchema(schema);
            jaxbMarshaller.marshal(errorResponse, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
