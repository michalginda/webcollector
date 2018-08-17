package com.mginda.webcollector.domain.product.serialization.filtering;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.mginda.webcollector.domain.product.ProductItem;

public class ZeroValueFilter implements PropertyFilter
{
    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer)
            throws Exception
    {
        if (pojo instanceof ProductItem && "kcal_per_100g".equals(writer.getName()) && isKcalZero((ProductItem) pojo))
        {
            return;
        }
        writer.serializeAsField(pojo, gen, prov);
    }

    private boolean isKcalZero(ProductItem product)
    {
        return product.getKcal_per_100g() == 0;
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator gen, SerializerProvider prov,
            PropertyWriter writer) throws Exception
    {
        writer.serializeAsField(elementValue, gen, prov);
    }

    @Deprecated
    @Override
    public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider)
            throws JsonMappingException
    {
        writer.depositSchemaProperty(propertiesNode, provider);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor,
            SerializerProvider provider) throws JsonMappingException
    {
        writer.depositSchemaProperty(objectVisitor, provider);
    }
}
