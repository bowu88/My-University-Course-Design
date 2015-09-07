// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TagsCloudFragment$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.TagsCloudFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165309, "field 'keywordsFlow'");
    target.keywordsFlow = (tk.fjnugis.danger.ui.KeywordsFlow) view;
    view = finder.findRequiredView(source, 2131165307, "field 'btnIn'");
    target.btnIn = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131165308, "field 'btnOut'");
    target.btnOut = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.TagsCloudFragment target) {
    target.keywordsFlow = null;
    target.btnIn = null;
    target.btnOut = null;
  }
}
