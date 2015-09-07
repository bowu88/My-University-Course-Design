// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HeatMapActivity$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.HeatMapActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165270, "field 'mMapView'");
    target.mMapView = (com.baidu.mapapi.map.MapView) view;
    view = finder.findRequiredView(source, 2131165269, "field 'backButton'");
    target.backButton = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.HeatMapActivity target) {
    target.mMapView = null;
    target.backButton = null;
  }
}
