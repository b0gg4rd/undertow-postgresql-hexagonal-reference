package com.gfb.reference.hexagonal.infrastructure.adapter.out.transformation.jsoniter;

import com.gfb.reference.hexagonal.application.port.out.transformation.JsonTransformationPortOut;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsoniterSpi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsoniterJsonTransformationAdapter implements JsonTransformationPortOut {

  public JsoniterJsonTransformationAdapter() {

    JsoniterSpi.registerTypeEncoder(
      LocalDateTime.class,
      (obj, stream) -> stream.writeVal(((LocalDateTime) obj).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

    JsoniterSpi.registerTypeDecoder(
      LocalDateTime.class,
      iter -> LocalDateTime.parse(iter.readString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    JsoniterSpi.registerTypeEncoder(
      LocalDate.class,
      (obj, stream) -> stream.writeVal(((LocalDate) obj).format(DateTimeFormatter.ISO_LOCAL_DATE)));

    JsoniterSpi.registerTypeDecoder(
      LocalDate.class,
      iter -> LocalDate.parse(iter.readString(), DateTimeFormatter.ISO_LOCAL_DATE));

  }

  @Override
  public <T> String toJson(T value) {

    return JsonStream.serialize(value);

  }

  @Override
  public <T> T fromJson(String value, Class<T> valueType) {

    return JsonIterator.deserialize(value, valueType);

  }

  @Override
  public <T> T fromJson(byte[] value, Class<T> valueType) {

    return JsonIterator.deserialize(value, valueType);

  }

}
