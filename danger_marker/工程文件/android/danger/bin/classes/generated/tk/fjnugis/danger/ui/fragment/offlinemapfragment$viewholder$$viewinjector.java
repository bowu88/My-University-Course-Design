// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class OfflineMapFragment$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.OfflineMapFragment.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165295, "field 'tabHost'");
    target.tabHost = (android.widget.TabHost) view;
    view = finder.findRequiredView(source, 2131165300, "field 'cityListView'");
    target.cityListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131165303, "field 'localMapListView'");
    target.localMapListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131165297, "field 'cityNameView'");
    target.cityNameView = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131165299, "field 'searchResultListView'");
    target.searchResultListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131165298, "field 'searchButton'");
    target.searchButton = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.OfflineMapFragment.ViewHolder target) {
    target.tabHost = null;
    target.cityListView = null;
    target.localMapListView = null;
    target.cityNameView = null;
    target.searchResultListView = null;
    target.searchButton = null;
  }
}
