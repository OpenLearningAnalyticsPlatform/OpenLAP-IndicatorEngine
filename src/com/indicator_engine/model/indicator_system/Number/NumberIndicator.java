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
public class NumberIndicator implements Serializable{

    @Size(min=3, max=50, message="Indicator Name must be between 3 and 50 characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="Indicator Name must be alphanumeric with no spaces")
    private String indicatorName;
    private long indicator_id ;

    private List<GenQuery> genQueries = new ArrayList<>();
    private GenIndicatorProps genIndicatorProps;

    public NumberIndicator(){
    }

    public void reset(){
        // Reset everything to your default values
        this.indicatorName = null;
        this.genIndicatorProps = null;
        this.getGenQueries().clear();
    }

    public long getIndicator_id() {
        return indicator_id;
    }

    public void setIndicator_id(long indicator_id) {
        this.indicator_id = indicator_id;
    }

    public GenIndicatorProps getGenIndicatorProps() {
        return genIndicatorProps;
    }

    public void setGenQueries(List<GenQuery> genQueries) {
        this.genQueries = genQueries;
    }

    public void setGenIndicatorProps(GenIndicatorProps genIndicatorProps) {
        this.genIndicatorProps = genIndicatorProps;
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
