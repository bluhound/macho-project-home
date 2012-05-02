package com.npower.dm.hibernate.entity;

import com.npower.dm.core.ProfileConfig;



/**
 * AutoJobProfileConfig generated by MyEclipse - Hibernate Tools
 */
public class AutoJobProfileConfig extends AbstractAutoJobProfileConfig implements java.io.Serializable, Comparable<AutoJobProfileConfig> {

    // Constructors

    /** default constructor */
    public AutoJobProfileConfig() {
    }

	/** minimal constructor */
    public AutoJobProfileConfig(AutoJobProfileConfigId id) {
        super(id);        
    }
    
    /** full constructor */
    public AutoJobProfileConfig(AutoJobProfileConfigId id, ProfileConfig profileConfig) {
        super(id, profileConfig);        
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(AutoJobProfileConfig other) {
      if (other == null) {
         return 1;
      }
      return (int)(this.getId().getProfileIndex()- other.getId().getProfileIndex());
    }
   
}