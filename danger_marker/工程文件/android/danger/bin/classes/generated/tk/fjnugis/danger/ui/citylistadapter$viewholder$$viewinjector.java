// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CityListAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.CityListAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165297, "field 'cityName'");
    target.cityName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165332, "field 'size'");
    target.size = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165333, "field 'downloadIV'");
    target.downloadIV = (android.widget.ImageView) view;
  }

  public static void reset(tk.fjnugis.danger.ui.CityListAdapter.ViewHolder target) {
    target.cityName = null;
    target.size = null;
    target.downloadIV = null;
  }
}
