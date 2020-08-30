package org.chelkatrao.varaq.enums;

import java.io.Serializable;

public enum Status implements Serializable {

    ACTIVE, PENDING, INACTIVE, REJECTED, DELETED;

    public String getStatus() {
        return this.name();
    }

}
