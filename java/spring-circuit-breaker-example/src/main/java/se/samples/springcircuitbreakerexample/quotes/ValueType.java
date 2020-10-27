package se.samples.springcircuitbreakerexample.quotes;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("Value")
class ValueType {

    private Long id;

    private String quote;

}
