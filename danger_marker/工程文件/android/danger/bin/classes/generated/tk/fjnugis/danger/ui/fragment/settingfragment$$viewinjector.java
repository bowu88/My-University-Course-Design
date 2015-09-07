// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingFragment$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.SettingFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165304, "field 'root'");
    target.root = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131165305, "field 'saveButton'");
    target.saveButton = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.SettingFragment target) {
    target.root = null;
    target.saveButton = null;
  }
}
