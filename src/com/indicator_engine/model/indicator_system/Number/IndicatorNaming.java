package com.indicator_engine.model.indicator_system.Number;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class IndicatorNaming implements Serializable{

    @Size(min=3, max=50, message="Indicator Name must be between 3 and 50 characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="Indicator Name must be alphanumeric with no spaces")
    private String indicatorName;

    private final List<GenQuery> genQueries = new ArrayList<>();

    public IndicatorNaming(){
    }

    public List<GenQuery> getGenQueries() {
        return genQueries;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }
}
