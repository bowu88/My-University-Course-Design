// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DangerListActivity$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.DangerListActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165267, "field 'swipeLayout'");
    target.swipeLayout = (android.support.v4.widget.SwipeRefreshLayout) view;
    view = finder.findRequiredView(source, 2131165268, "field 'listView'");
    target.listView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131165266, "field 'title'");
    target.title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165265, "field 'backButton'");
    target.backButton = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.DangerListActivity target) {
    target.swipeLayout = null;
    target.listView = null;
    target.title = null;
    target.backButton = null;
  }
}
