package com.npower.dm.hibernate.entity;

import com.npower.dm.core.AutomaticProvisionJob;



/**
 * AutoJobProfileConfigId generated by MyEclipse - Hibernate Tools
 */
public class AutoJobProfileConfigId extends AbstractAutoJobProfileConfigId implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public AutoJobProfileConfigId() {
    }

    
    /** full constructor */
    public AutoJobProfileConfigId(AutomaticProvisionJob autoProvisionJob, Long profileIndex) {
        super(autoProvisionJob, profileIndex);        
    }
   
}