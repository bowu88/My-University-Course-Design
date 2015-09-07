// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ListFragment$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.ListFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165267, "field 'swipeLayout'");
    target.swipeLayout = (android.support.v4.widget.SwipeRefreshLayout) view;
    view = finder.findRequiredView(source, 2131165268, "field 'listView'");
    target.listView = (android.widget.ListView) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.ListFragment target) {
    target.swipeLayout = null;
    target.listView = null;
  }
}
