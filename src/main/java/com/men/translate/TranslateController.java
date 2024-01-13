package com.men.translate;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.model.Translate;
import com.men.common.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author mendianyu
 */
@Before(TranslateInterceptor.class)
public class TranslateController extends Controller
{

    @Inject
    TranslateService service = new TranslateService();

    public void index()
    {
        render("/translate/translation.html");
    }


    /**
     * 翻译文本并将结果存到数据库
     */
    public void fanyi()
    {
        //从输入获取参数
        String from = getPara("from");
        String to = getPara("to");
        String q = getPara("q");

        //调用翻译方法
        String result = service.textTrans(from, to, q);
        //用于将内容显示回界面
        setAttr("q", q);
        setAttr("result", result);
        //获取session中保存的id
        String id = getSessionAttr("id");

        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        // 将当前时间格式化为字符串
        String formattedDateTime = currentDateTime.format(formatter);

        Translate translate = new Translate();
        translate.setId(id);
        translate.setTime(formattedDateTime);
        translate.setFrom1(from);
        translate.setTo1(to);
        translate.setQ(q);
        translate.setResult(result);

        translate.save();

        refreshTranslateTimes();
        render("/translate/translation.html");
    }

    /**
     * 更新翻译使用字符量
     */
    public void refreshTranslateTimes()
    {
        String q = getAttr("q");
        //忽略空格和标点符号
        int amounts = q.replaceAll("[\\s\\p{P}]", "").length();
        String id = getSessionAttr("id");

        Integer tUsed = service.findUserById(id).getTUsed() + amounts;
        Integer tLast = service.findUserById(id).getTLast() - amounts;
        User user = service.findUserById(id);
        user.setTUsed(tUsed);
        user.setTLast(tLast);
        user.update();
    }

    /**
     * 翻译记录列表
     */
    public void list()
    {
        setAttr("list", service.paginate(getParaToInt(0, 1), 5));
        render("/translate/list.html");
    }

    /**
     * 翻译记录具体内容
     */
    public void detail()
    {
        setAttr("details", service.findByTime(getPara()));
        render("/translate/details.html");
    }

    /**
     * 模糊查询
     */
    public void search()
    {
        String from = getPara("from");
        String to = getPara("to");
        String date = getPara("date");
        String id = getSessionAttr("id");
        if ((from != null && !from.isEmpty()) || (to != null && !to.isEmpty()) || (date != null && !date.isEmpty()))
        {
            // 执行模糊查询操作
            List<Record> translations = service.searchRecords(from, to, date, id);
            setAttr("date", date);

            // 将查询结果传递到页面
            setAttr("translations", translations);
        } else
        {
            //如果没有参数,显示所有翻译记录
            List<Record> allTranslations = service.searchRecords("", "", "", id);
            setAttr("translations", allTranslations);
        }

        // 渲染结果到页面
        render("/translate/search.html");
    }
}


