package com.men.translate;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.HttpUtil;
import com.men.common.model.Translate;
import com.men.common.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author mendianyu
 */
public class TranslateService
{
    private Translate dao = new Translate().dao();
    private User udao = new User().dao();

    public String textTrans(String from, String to, String q)
    {
        // 请求url
        String url = "https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1";
        try
        {
            Map<String, Object> map = new HashMap<>();
            map.put("from", from);
            map.put("to", to);
            map.put("q", q);

            String param = com.alibaba.fastjson2.JSON.toJSONString(map);

            String accessToken = "24.d093c94cec01f789c67706665690eb9a.2592000.1703815127.282335-43853160";

            String result = HttpUtil.post(url, accessToken, "application/json", param);

            //将HTTP响应的JSON字符串解析为一个JSONObject对象
            JSONObject jsonObject = com.alibaba.fastjson2.JSON.parseObject(result);
            //从解析后的JSON对象中获取名为 "result" 的子对象
            JSONObject resultObject = jsonObject.getJSONObject("result");
            //从"result"对象中获取名为"trans_result"的JSON数组
            JSONArray transResultArray = resultObject.getJSONArray("trans_result");
            //从transResultArray数组中获取第一个翻译结果
            JSONObject transResultObject = transResultArray.getJSONObject(0);
            String dstValue = transResultObject.getString("dst");
            //输出翻译结果
            System.out.println("翻译结果为: " + dstValue);
            return dstValue;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public Page<Translate> paginate(int pageNumber, int pageSize)
    {
        return dao.paginate(pageNumber, pageSize, "select *", "from translate order by time desc");
    }


    /**
     * 根据时间查找记录
     */
    public Translate findByTime(String time)
    {
        return dao.findFirst("select * from translate where time = ? ", time);
    }

    /**
     * 模糊查询
     */
    public List<Record> searchRecords(String from, String to, String date, String id)
    {
        String sql = "select * from translate where (from1 like ? or to1 like ? or time like ?) and id=? order by id desc";
        date = "%" + date + "%";

        List<Record> translations = Db.find(sql, from, to, date, id);
        return translations;
    }

    /**
     * 根据id查找用户
     */
    public User findUserById(String id)
    {
        return udao.findFirst("select * from user where id = ? ", id);
    }
}
