// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DangerListAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.DangerListAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165340, "field 'icon'");
    target.icon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131165277, "field 'title'");
    target.title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165342, "field 'rank'");
    target.rank = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165285, "field 'distanceTextView'");
    target.distanceTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165341, "field 'ratingBar'");
    target.ratingBar = (android.widget.RatingBar) view;
  }

  public static void reset(tk.fjnugis.danger.ui.DangerListAdapter.ViewHolder target) {
    target.icon = null;
    target.title = null;
    target.rank = null;
    target.distanceTextView = null;
    target.ratingBar = null;
  }
}
