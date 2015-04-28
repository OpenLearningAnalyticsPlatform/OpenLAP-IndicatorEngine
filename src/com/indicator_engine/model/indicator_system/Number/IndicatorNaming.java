package com.indicator_engine.model.indicator_system.Number;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class IndicatorNaming implements Serializable{

    @Size(min=3, max=50, message="Indicator Name must be between 3 and 20 characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="Indicator Name must be alphanumeric with no spaces")
    private String indicatorName;

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }
}
