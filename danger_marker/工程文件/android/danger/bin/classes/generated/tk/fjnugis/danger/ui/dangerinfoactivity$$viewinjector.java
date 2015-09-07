// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DangerInfoActivity$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.DangerInfoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165245, "field 'backButton'");
    target.backButton = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131165248, "field 'titileTextView'");
    target.titileTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165253, "field 'tagsLinearLayout'");
    target.tagsLinearLayout = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131165256, "field 'rankRatingBar'");
    target.rankRatingBar = (android.widget.RatingBar) view;
    view = finder.findRequiredView(source, 2131165257, "field 'rankText'");
    target.rankText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165250, "field 'categoryTextView'");
    target.categoryTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165263, "field 'descriptionTextView'");
    target.descriptionTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165261, "field 'locationLatlngTextView'");
    target.locationLatlngTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165262, "field 'loactionAddressTextView'");
    target.loactionAddressTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165259, "field 'locationAddressDescTextView'");
    target.locationAddressDescTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165255, "field 'picturesTextView'");
    target.picturesTextView = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165264, "field 'gradView'");
    target.gradView = (tk.fjnugis.danger.ui.NoScrollGridView) view;
  }

  public static void reset(tk.fjnugis.danger.ui.DangerInfoActivity target) {
    target.backButton = null;
    target.titileTextView = null;
    target.tagsLinearLayout = null;
    target.rankRatingBar = null;
    target.rankText = null;
    target.categoryTextView = null;
    target.descriptionTextView = null;
    target.locationLatlngTextView = null;
    target.loactionAddressTextView = null;
    target.locationAddressDescTextView = null;
    target.picturesTextView = null;
    target.gradView = null;
  }
}
