package com.indicator_engine.datamodel;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 17-03-2015.
 */
@Entity
@Table(name = "gla_Operations")
public final class GLAOperations implements Serializable {

    @Id
    @Column(name = "operation_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(name = "operations", nullable = false, unique = true)
    private String operations;

    public GLAOperations() {}
    public GLAOperations(String operations) {
        this.operations = operations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }
}
