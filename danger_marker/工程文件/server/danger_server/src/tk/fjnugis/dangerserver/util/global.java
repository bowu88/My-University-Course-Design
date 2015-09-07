package tk.fjnugis.dangerserver.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Created by Xiangyang on 2014/12/4.
 */
public class Global {
    //public static Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public static Gson gson=new Gson();
    public static GeometryFactory geometryFactory=new GeometryFactory(new PrecisionModel(PrecisionModel.FIXED),4326);
}
