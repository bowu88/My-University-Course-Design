// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ListFragment$DangerListAdapter$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.ListFragment.DangerListAdapter target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099768, "field 'icon'");
    target.icon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131099747, "field 'title'");
    target.title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131099754, "field 'category'");
    target.category = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131099770, "field 'rank'");
    target.rank = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131099740, "field 'distance'");
    target.distance = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131099769, "field 'ratingBar'");
    target.ratingBar = (android.widget.RatingBar) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.ListFragment.DangerListAdapter target) {
    target.icon = null;
    target.title = null;
    target.category = null;
    target.rank = null;
    target.distance = null;
    target.ratingBar = null;
  }
}
