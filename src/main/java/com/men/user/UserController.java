package com.men.user;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.model.User;

import java.util.List;

@Before(UserInterceptor.class)
public class UserController extends Controller
{

    @Inject
    UserService service = new UserService();

    /**
     * 主界面
     */
    public void index()
    {
        if (getSession().getAttribute("loginUser") == null)
        {
            redirect("/");
        } else
        {
            render("list.html");
        }
    }

    /**
     * 用户列表
     */
    public void list()
    {
        setAttr("list", service.paginate(getParaToInt(0, 1), 5));
        render("/user/list.html");
    }

    /**
     * 添加学生
     */
    public void add()
    {
        render("add.html");
    }

    public void save()
    {
        getModel(User.class).save();
        redirect("/user/list");
    }


    /**
     * 更新学生信息
     */
    public void edit()
    {
        setAttr("user", service.findById(getPara()));
        render("update.html");
    }

    public void update()
    {
        String id = getPara("user.id");
        getModel(User.class).update();
        redirect("/user/self");
    }

    /**
     * 删除学生信息
     */
    public void delete()
    {
        service.deleteById(getPara());
        redirect("/user/list");
    }

    /**
     * 模糊查询
     */
    public void search()
    {
        String param = getPara("param");
        if (param != null && !param.isEmpty())
        {
            // 执行模糊查询操作
            List<Record> users = service.searchUsers(param);

            // 将查询结果传递到页面
            setAttr("users", users);
            setAttr("param", param);
        } else
        {
            //如果没有参数,显示所有用户更列表
            List<User> allUsers = service.findAll();
            setAttr("users", allUsers);
        }

        // 渲染结果到页面
        render("/user/search.html");
    }

    /**
     * 个人信息
     */
    public void self()
    {
        String id = getSessionAttr("id");
        // 执行模糊查询操作
        User user = service.findUserByNameAndPassword(id);

        // 将查询结果传递到页面
        setAttr("user", user);
        // 渲染结果到页面
        render("/user/self.html");
    }
//    public void search() {
//        String param = getPara("param"); // 从请求参数中获取关键字，默认为空字符串
//
//        if (param != null) {
//            // 调用带有模糊查询的分页方法
//            Page<User> list = service.paginate(getParaToInt("pageNumber", 1), 5, param);
//
//            setAttr("list", list);
//            setAttr("param", param);
//        } else {
//            // 如果没有参数，显示所有学生列表
//            List<User> allUsers = service.findAll();
//            setAttr("list", allUsers);
//        }
//
//        render("/User/search.html");
//    }

}


