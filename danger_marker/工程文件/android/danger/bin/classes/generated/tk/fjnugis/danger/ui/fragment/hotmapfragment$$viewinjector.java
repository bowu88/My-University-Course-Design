// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HotMapFragment$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.HotMapFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165280, "field 'button'");
    target.button = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131165276, "field 'categorySpinner'");
    target.categorySpinner = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131165278, "field 'subCategorySpinner'");
    target.subCategorySpinner = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131165279, "field 'tagsET'");
    target.tagsET = (android.widget.EditText) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.HotMapFragment target) {
    target.button = null;
    target.categorySpinner = null;
    target.subCategorySpinner = null;
    target.tagsET = null;
  }
}
