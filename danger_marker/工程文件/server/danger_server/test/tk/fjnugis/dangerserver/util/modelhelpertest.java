package tk.fjnugis.dangerserver.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Xiangyang on 2015/1/16.
 */
public class ModelHelperTest {
    @Test
    public void testJoinString(){
        String[] arr={"aa","bb","cc"};
        String str ="__";
        String s=ModelHelper.getInstance().joinString(arr,str);
        Assert.assertEquals(s,"aa__bb__cc");

    }
}
