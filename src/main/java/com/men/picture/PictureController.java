package com.men.picture;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.model.Picture;
import com.men.common.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Before(PictureInterceptor.class)
public class PictureController extends Controller
{

    @Inject
    PictureService service = new PictureService();

    /**
     * 主界面
     */
    public void index()
    {
        render("/picture/convert.html");
    }

    /**
     * 图片转换并将记录保存到数据库
     */
    public void convert()
    {
        String src1 = getPara("imageSrc");
        String src2 = null;
        if (src1 != null)
        {
            setAttr("imageSrc1", src1);
            String base64 = src1.split(",")[1];

            String url = "";
            String url1 = getPara("url1");
            String url2 = getPara("url2");
            String urlType = "";
            String type = "";
            if (url1 != null)
            {
                url = url1;

            } else
            {
                url = url2;
            }
            //获取url的最后四位，判断是什么转换类型
            urlType = url.substring(url.length() - 4);
            switch (urlType)
            {
                case "nime":
                    type = "人物动漫化";
                    break;
                case "rize":
                    type = "黑白图像上色";
                    break;
                case "haze":
                    type = "图像去雾";
                    break;
                case "tore":
                    type = "拉伸图像修复";
                    break;
                case "ance":
                    type = "图像清晰度增强";

                    break;
                case "oire":
                    type = "图像去摩尔纹";
                    break;
                case "pair":
                    type = "文档图片去底纹";
                    break;
                case "oise":
                    type = "图像去噪";
                    break;
                default:
                    break;
            }

            //System.out.println("url:" + url);
            //System.out.println(base64);
            //转换后的图片的base64编码
            // System.out.println(service.getBase64Str(url, base64));
            src2 = "data:image/jpeg;base64," + service.getBase64Str(url, base64);
            setAttr("imageSrc2", src2);
            //System.out.println(src2);

            //保存到数据库
            //获取session中保存的id
            String id = getSessionAttr("id");
            // 获取当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();
            // 定义日期时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            // 将当前时间格式化为字符串
            String formattedDateTime = currentDateTime.format(formatter);

            Picture picture = new Picture();
            picture.setId(id);
            picture.setTime(formattedDateTime);
            picture.setType(type);
            picture.setSrc1(src1);
            picture.setSrc2(src2);
            picture.save();

            refreshPictureTimes();
        }

        render("/picture/convert.html");
    }

    /**
     * 更新图片转换使用次数
     */
    public void refreshPictureTimes()
    {
        String id = getSessionAttr("id");

        Integer pUsed = service.findUserById(id).getPUsed() + 1;
        Integer pLast = service.findUserById(id).getPLast() - 1;
        User user = service.findUserById(id);
        user.setPUsed(pUsed);
        user.setPLast(pLast);
        user.update();
    }

    /**
     * 图片处理记录
     */
    public void list()
    {
        setAttr("list", service.paginate(getParaToInt(0, 1), 5));
        render("/picture/list.html");
    }

    /**
     * 图片处理详细内容
     */
    public void detail()
    {
        setAttr("details", service.findByTime(getPara()));
        render("/picture/details.html");
    }

    /**
     * 模糊查询
     */
    public void search()
    {
        String url = "";
        String url1 = getPara("url1");
        String url2 = getPara("url2");
        String urlType = "";
        String type = "";
        if (url1 != null)
        {
            url = url1;

        } else
        {
            url = url2;
        }
        String date = getPara("date");
        String id = getSessionAttr("id");
        if ((url != null && !url.isEmpty()) || (date != null && !date.isEmpty()))
        {
            // 执行模糊查询操作
            List<Record> pRecord = service.searchRecords(url, date, id);
            setAttr("date", date);

            // 将查询结果传递到页面
            setAttr("pRecord", pRecord);
        } else
        {
            //如果没有参数,显示所有翻译记录
            List<Record> pRecord = service.searchRecords("", "", id);
            setAttr("pRecord", pRecord);
        }

        // 渲染结果到页面
        render("/picture/search.html");
    }
}


