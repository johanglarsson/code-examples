package se.samples.springcircuitbreakerexample.quotes;

import lombok.Data;

@Data
class Quote {

    private String type;

    private ValueType value;

}