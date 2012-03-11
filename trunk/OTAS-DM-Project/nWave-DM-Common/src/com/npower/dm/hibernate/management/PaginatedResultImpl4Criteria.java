/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/PaginatedResultImpl4Criteria.java,v 1.2 2008/08/27 03:58:55 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/27 03:58:55 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.hibernate.management;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;

import com.npower.dm.management.PaginatedResult;
import com.npower.dm.util.BeanHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/27 03:58:55 $
 */
public class PaginatedResultImpl4Criteria extends AbstractPaginatedResult implements PaginatedResult {

  protected Criteria criteria = null;
  /**
   * 
   */
  protected PaginatedResultImpl4Criteria() {
    super();
  }

  /**
   * 
   */
  public PaginatedResultImpl4Criteria(Criteria criteria, int pageNumber, int objectsPerPage) {
    super(pageNumber, objectsPerPage);
    this.setCriteria(criteria);
    // Load Data
    this.load();
  }

  public Criteria getCriteria() {
    return criteria;
  }

  /**
   * @param criteria
   *          the criteria to set
   */
  public void setCriteria(Criteria criteria) {
    this.criteria = criteria;
  }

  // protected methods
  // ------------------------------------------------------------------------------------
  protected void load() {
    // http://www.blogjava.net/iamtin/archive/2006/06/06/50702.aspx
    // �Ȱ�Projection��OrderBy����ȡ����,���������ִ��Count����
    CriteriaImpl impl = (CriteriaImpl) this.criteria;
    Projection projection = impl.getProjection();
    List<OrderEntry> orderEntries;
    try {
      orderEntries = (List<OrderEntry>) BeanHelper.getPrivateProperty(impl, "orderEntries");
      BeanHelper.setPrivateProperty(impl, "orderEntries", new ArrayList<OrderEntry>());
    } catch (Exception e) {
      throw new InternalError(" Runtime Exception impossibility throw ");
    }
    // Execute and get query for counting.
    Projection project4Count = Projections.rowCount();
    this.fullListSize = (Integer) criteria.setProjection(project4Count).uniqueResult();
    
    // ��֮ǰ��Projection��OrderBy�����������ȥ
    criteria.setProjection(projection);
    if (projection == null) {
       criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
       // ������DISTINCT_ROOT_ENTITYȥ���ظ��ļ�¼, ���ַ�ʽ�޷�������ɷ�ҳ����
       // DISTINCT_ROOT_ENTITY ����ȥ���ظ��ļ�¼, ��ȥ���ظ���¼�Ĺ��̲��������ݿ����, ���Ӱ�췵�صĽ������. 
       //criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    try {
      BeanHelper.setPrivateProperty(impl, "orderEntries", orderEntries);
    } catch (Exception e) {
      throw new InternalError("Runtime Exception impossibility throw ");
    }
    int firstResult = this.getObjectsPerPage() * (this.getPageNumber() - 1);
    criteria.setFirstResult(firstResult);
    criteria.setMaxResults(this.getObjectsPerPage());
    this.results = criteria.list();

    // Load Records
    // criteria.setProjection(null);
    // int firstResult = this.getObjectsPerPage() * (this.getPageNumber() - 1);
    // criteria.setFirstResult(firstResult);
    // criteria.setMaxResults(this.getObjectsPerPage());
    // this.results = criteria.list();

    // Countting records
    // criteria.setProjection(Projections.rowCount());
    // ���������������һ�Σ����´ε���ʱ���������ʲôԭ�򻹲����
    // criteria.setFirstResult(0);
    // criteria.setMaxResults(Integer.MAX_VALUE);
    // this.fullListSize = ((Integer)criteria.list().get(0)).intValue();
  }

}
