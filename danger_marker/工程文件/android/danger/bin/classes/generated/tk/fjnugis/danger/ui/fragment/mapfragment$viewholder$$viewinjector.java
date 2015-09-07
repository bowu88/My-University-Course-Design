// Generated code from Butter Knife. Do not modify!
package tk.fjnugis.danger.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MapFragment$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final tk.fjnugis.danger.ui.fragment.MapFragment.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165281, "field 'mapView'");
    target.mapView = (com.baidu.mapapi.map.MapView) view;
    view = finder.findRequiredView(source, 2131165282, "field 'requestLocButton'");
    target.requestLocButton = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131165283, "field 'dangerInfoWindow'");
    target.dangerInfoWindow = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131165284, "field 'infoTitle'");
    target.infoTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165286, "field 'infoRatingBar'");
    target.infoRatingBar = (android.widget.RatingBar) view;
    view = finder.findRequiredView(source, 2131165287, "field 'infoRank'");
    target.infoRank = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165288, "field 'infoCategory'");
    target.infoCategory = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165289, "field 'infoDescription'");
    target.infoDescription = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165290, "field 'infoButton'");
    target.infoButton = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131165291, "field 'uploadWindow'");
    target.uploadWindow = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131165293, "field 'uploadAddress'");
    target.uploadAddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131165294, "field 'uploadButton'");
    target.uploadButton = (android.widget.Button) view;
  }

  public static void reset(tk.fjnugis.danger.ui.fragment.MapFragment.ViewHolder target) {
    target.mapView = null;
    target.requestLocButton = null;
    target.dangerInfoWindow = null;
    target.infoTitle = null;
    target.infoRatingBar = null;
    target.infoRank = null;
    target.infoCategory = null;
    target.infoDescription = null;
    target.infoButton = null;
    target.uploadWindow = null;
    target.uploadAddress = null;
    target.uploadButton = null;
  }
}
