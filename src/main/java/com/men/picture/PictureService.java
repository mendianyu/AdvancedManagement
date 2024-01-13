package com.men.picture;

import com.alibaba.fastjson2.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.HttpUtil;
import com.men.common.model.Picture;
import com.men.common.model.User;

import java.net.URLEncoder;
import java.util.List;


/**
 * @author mendianyu
 */
public class PictureService
{

    private Picture dao = new Picture().dao();
    private User udao = new User().dao();

    /**
     * 分页
     */
    public Page<Picture> paginate(int pageNumber, int pageSize)
    {
//        String id = getSessionAttr("id");
        return dao.paginate(pageNumber, pageSize, "select *", "from picture order by time desc");
    }


    /**
     * 根据时间查找记录
     */
    public Picture findByTime(String time)
    {
        return dao.findFirst("select * from picture where time = ? ", time);
    }


    /**
     * 获取传过来的图片的转换之后的base64编码
     *
     * @param url 图片增强/特效的请求地址
     * @param src 原始图片的src
     * @return 返回转换后的图片的base64编码
     */
    public String getBase64Str(String url, String src)
    {
        // 人物动漫化请求url
        //String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime";
        try
        {
            String imgParam = URLEncoder.encode(src, "UTF-8");
            String param = "image=" + imgParam;
            //  System.out.println(param);
            String accessToken = "24.11bf0aeb3debfee1212eb74bb15ad01a.2592000.1703921333.282335-44036401";
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = com.alibaba.fastjson2.JSON.parseObject(result);
            String base64Str = "";
            if (jsonObject.getString("image") != null)
            {
                base64Str = jsonObject.getString("image");
            } else if (jsonObject.getString("result") != null)
            {
                base64Str = jsonObject.getString("result");
            } else if (jsonObject.getString("image_processed") != null)
            {
                base64Str = jsonObject.getString("image_processed");
            }
            // System.out.println(base64Str);
            return base64Str;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 模糊查询
     */
    public List<Record> searchRecords(String url, String date, String id)
    {
        String sql = "select * from picture where (type like ? or time like ?) and id=? order by id desc";
        date = "%" + date + "%";

        List<Record> pRecord = Db.find(sql, url, date, id);
        return pRecord;
    }

    /**
     * 根据id查找用户
     */
    public User findUserById(String id)
    {
        return udao.findFirst("select * from user where id = ? ", id);
    }
}
