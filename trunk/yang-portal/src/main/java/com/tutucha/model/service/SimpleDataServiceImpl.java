package com.tutucha.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tutucha.model.entity.Area;
import com.tutucha.model.entity.AreaItem;
import com.tutucha.model.entity.Navigation;
import com.tutucha.model.entity.NavigationCategory;

public class SimpleDataServiceImpl implements DataService {

  private Map<NavigationCategory, List<Navigation>> navigations = new HashMap<NavigationCategory, List<Navigation>>();
  private List<Area> areas = new ArrayList<Area>();

  /**
   * 
   */
  public SimpleDataServiceImpl() {
    super();
    this.refresh();
  }

  protected void refresh() {
    // 加载分类
    put(new NavigationCategory("58", "名站", "6"));
    put(new NavigationCategory("59", "新闻", "6"));
    put(new NavigationCategory("60", "小说", "6"));
    put(new NavigationCategory("61", "体育", "6"));
    put(new NavigationCategory("62", "汽车", "6"));
    put(new NavigationCategory("63", "军事", "6"));
    put(new NavigationCategory("64", "财经", "6"));
    put(new NavigationCategory("65", "旅游", "6"));
    put(new NavigationCategory("66", "视频", "6"));
    put(new NavigationCategory("67", "影视", "6"));
    put(new NavigationCategory("68", "音乐", "6"));
    put(new NavigationCategory("69", "社交", "6"));
    put(new NavigationCategory("100", "网站应用", "6"));

    // 加载所有Navigation
    put(new Navigation("1", "百度", "http://www.baidu.com", "class=\"cy_baidu\"", "58"));
    put(new Navigation("2", "新浪", "http://www.baidu.com", "class=\"cy_chongwu\"", "58"));
    put(new Navigation("3", "搜狐", "http://www.baidu.com", "class=\"cy_sohu\"", "58"));
    put(new Navigation("4", "情侣", "http://www.baidu.com", "class=\"cy_love\"", "58"));
    put(new Navigation("5", "美白", "http://www.baidu.com", "class=\"cy_quce\"", "58"));
    put(new Navigation("6", "淘宝", "http://www.baidu.com", "class=\"cy_taobao\"", "58"));
    put(new Navigation("7", "凤凰", "http://www.baidu.com", "class=\"cy_ifeng\"", "58"));
    put(new Navigation("8", "军事", "http://www.baidu.com", "class=\"cy_junshi\"", "58"));
    put(new Navigation("9", "网易", "http://www.baidu.com", "class=\"cy_163\"", "58"));
    put(new Navigation("10", "京东", "http://www.baidu.com",
        "style=\"background:url(http://up.tutucha.com/upload/20140402/L2haLpbpKB.jpg);-webkit-background-size: 16px auto;background-size: 16px auto;\"", "58"));
    put(new Navigation("1a", "淘宝", "http://www.baidu.com", "class=\"cy_taobao\"", "58"));
    put(new Navigation("1b", "凤凰", "http://www.baidu.com", "class=\"cy_ifeng\"", "58"));

    put(new Navigation("11", "小说", "http://www.baidu.com", "", "59"));
    put(new Navigation("12", "阅读", "http://www.baidu.com", "", "59"));
    put(new Navigation("13", "书城", "http://www.baidu.com", "", "59"));
    put(new Navigation("14", "宜搜", "http://www.baidu.com", "", "59"));
    put(new Navigation("15", "起点", "http://www.baidu.com", "", "59"));
    put(new Navigation("16", "潇湘", "http://www.baidu.com", "", "59"));
    put(new Navigation("17", "言情小说", "http://www.baidu.com", "", "59"));
    put(new Navigation("18", "搜狐", "http://www.baidu.com", "", "59"));
    put(new Navigation("19", "17小说", "http://www.baidu.com", "", "59"));
    put(new Navigation("20", "故事会", "http://www.baidu.com", "", "59"));

    put(new Navigation("1001", "天气", "tianqi", "./static/images/114/tianqi.jpg", "100"));
    put(new Navigation("1002", "彩票开奖", "caipiao", "./static/images/114/caipiao.gif", "100"));
    put(new Navigation("1003", "万年历", "calendar", "./static/images/114/calendar.jpg", "100"));
    put(new Navigation("1004", "身份证", "idcard", "./static/images/114/idcard.jpg", "100"));
    put(new Navigation("1005", "邮编", "zipcode", "./static/images/114/zipcode.jpg", "100"));
    put(new Navigation("1006", "公交地铁", "train", "./static/images/114/liecheshike.jpg", "100"));
    put(new Navigation("1007", "公交地铁", "transit", "./static/images/114/gongjiao.jpg", "100"));
    put(new Navigation("1008", "长途汽车", "bus", "./static/images/114/bus.jpg", "100"));
    put(new Navigation("1009", "常用电话", "tel", "./static/images/114/tel.jpg", "100"));
    put(new Navigation("1010", "地图", "http://map.baidu.com/mobile/", "./static/images/114/ditu.jpg", "100"));
    put(new Navigation("1011", "汇率查询", "http://m.wap.soso.com/app/forex/index.jsp?g_ut=3&biz=newHome", "./static/images/114/huilv.jpg", "100"));
    put(new Navigation("1012", "股票助手", "http://m.wap.soso.com/app/stock/index.jsp?g_ut=3&biz=newHome", "./static/images/114/gupiao.jpg", "100"));
    put(new Navigation("1013", "手机归属", "shouji", "./static/images/114/shouji.jpg", "100"));
    put(new Navigation("1014", "航班查询", "http://m.wap.soso.com/app/flight/index.jsp?g_ut=3&biz=newHome", "./static/images/114/hangban.jpg", "100"));
    put(new Navigation("1015", "区号", "areacode", "./static/images/114/areacode.jpg", "100"));
    put(new Navigation("1016", "问医生", "http://m.soujibing.com/?src=46644", "./static/images/114/soujibing.jpg", "100"));
    put(new Navigation("1017", "计算器", "calculator", "./static/images/114/calculator.jpg", "100"));
    put(new Navigation("1018", "计算器", "http://m.zhuna.cn/?agent_id=2763582", "./static/images/114/hotel.jpg", "100"));
    put(new Navigation("1019", "网易新闻", "http://3g.163.com/", "./static/images/163.gif", "100"));
    put(new Navigation("1020", "搜房", "http://m.soufun.com/esf/hz/?sf_source=wapesf_hz_esf46644", "./static/images/114/sofun.jpg", "100"));

    {
      Area area = new Area("34", "名站导航");
      area.getItems().add(new AreaItem("170", this.getNavigationById("1")));
      area.getItems().add(new AreaItem("171", this.getNavigationById("2")));
      area.getItems().add(new AreaItem("172", this.getNavigationById("3")));
      area.getItems().add(new AreaItem("173", this.getNavigationById("4")));
      area.getItems().add(new AreaItem("174", this.getNavigationById("5")));
      area.getItems().add(new AreaItem("175", this.getNavigationById("6")));
      area.getItems().add(new AreaItem("176", this.getNavigationById("7")));
      area.getItems().add(new AreaItem("177", this.getNavigationById("8")));
      area.getItems().add(new AreaItem("178", this.getNavigationById("9")));
      area.getItems().add(new AreaItem("179", this.getNavigationById("10")));
      area.getItems().add(new AreaItem("180", this.getNavigationById("1a")));
      area.getItems().add(new AreaItem("181", this.getNavigationById("1b")));
      put(area);
    }

    {
      Area area = new Area("100", "网站应用");
      area.getItems().add(new AreaItem("1700", this.getNavigationById("1001")));
      area.getItems().add(new AreaItem("1701", this.getNavigationById("1002")));
      area.getItems().add(new AreaItem("1702", this.getNavigationById("1003")));
      area.getItems().add(new AreaItem("1703", this.getNavigationById("1004")));
      area.getItems().add(new AreaItem("1704", this.getNavigationById("1005")));
      area.getItems().add(new AreaItem("1705", this.getNavigationById("1006")));
      area.getItems().add(new AreaItem("1706", this.getNavigationById("1007")));
      area.getItems().add(new AreaItem("1707", this.getNavigationById("1008")));
      area.getItems().add(new AreaItem("1708", this.getNavigationById("1009")));
      area.getItems().add(new AreaItem("1709", this.getNavigationById("1010")));
      area.getItems().add(new AreaItem("1800", this.getNavigationById("1011")));
      area.getItems().add(new AreaItem("1801", this.getNavigationById("1012")));
      area.getItems().add(new AreaItem("1802", this.getNavigationById("1013")));
      area.getItems().add(new AreaItem("1803", this.getNavigationById("1014")));
      area.getItems().add(new AreaItem("1804", this.getNavigationById("1015")));
      area.getItems().add(new AreaItem("1805", this.getNavigationById("1016")));
      area.getItems().add(new AreaItem("1806", this.getNavigationById("1017")));
      area.getItems().add(new AreaItem("1807", this.getNavigationById("1018")));
      area.getItems().add(new AreaItem("1808", this.getNavigationById("1019")));
      area.getItems().add(new AreaItem("1809", this.getNavigationById("1020")));
      put(area);
    }
  }

  private void put(Area area) {
    areas.add(area);
  }

  private void put(Navigation navigation) {
    navigations.get(new NavigationCategory(navigation.getCategoryId())).add(navigation);
  }

  private void put(NavigationCategory navigationCategory) {
    navigations.put(navigationCategory, new ArrayList<Navigation>());
  }

  // ------- Implements DataService interface
  // -------------------------------------------------------------
  /*
   * (non-Javadoc)
   * 
   * @see com.tutucha.model.service.DataService#getAreaById(java.lang.String,
   * java.lang.String)
   */
  public Area getAreaById(String pageId, String areaId) {
    for (Area area : this.areas) {
      if (area.getId().equals(areaId)) {
        return area;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.tutucha.model.service.DataService#getNavigationsByCategory(java.lang
   * .String)
   */
  public List<Navigation> getNavigationsByCategory(String categoryId) {
    return this.navigations.get(new NavigationCategory(categoryId));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.tutucha.model.service.DataService#getCaategoryById(java.lang.String)
   */
  public NavigationCategory getCategoryById(String categoryId) {
    for (NavigationCategory c : this.navigations.keySet()) {
      if (c.getId().equals(categoryId)) {
        return c;
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.tutucha.model.service.DataService#getNavigationById(java.lang.String)
   */
  public Navigation getNavigationById(String id) {
    for (List<Navigation> navigationList : this.navigations.values()) {
      for (Navigation navigation : navigationList) {
        if (navigation.getId().equals(id)) {
          return navigation;
        }
      }
    }
    return null;
  }

}
