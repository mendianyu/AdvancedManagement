package com.men.index;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.men.common.model.User;
import com.men.user.UserService;

/**
 * @author mendianyu
 */
public class IndexController extends Controller
{

    UserService service = new UserService();

    //前台渲染登录界面
    public void index()
    {
        render("index.html");
    }

    /**
     * 用户登录验证
     */
    @ActionKey("/login")
    public void login()
    {
        //获取用户输入的账号和密码
        String id = getPara("id");
        String password = getPara("password");

        //从数据库中获取数据
        User userMassage = service.findUserByNameAndPassword(id);

        //验证用户名和密码是否正确
        if (userMassage != null)
        {
            if (password.equals(userMassage.getStr("password")))
            {
                setSessionAttr("loginUser", userMassage);
                setSessionAttr("id", userMassage.getId());
                redirect("/user/self");
            } else
            {
                setAttr("errorMsg", "用户名或密码错误,请重新输入。");
                render("index.html");
            }
        } else
        {
            setAttr("errorMsg", "该用户名不存在,请重新输入。");
            render("index.html");
        }
    }

    /**
     * 用户退出
     */
    @ActionKey("logout")
    public void logout()
    {
        getSession().removeAttribute("loginUser");
        render("index.html");
    }

    /**
     * 用户注册
     */
    @ActionKey("register")
    public void register()
    {
        String id = getPara("id");
        String password = getPara("password");
        User user = service.findUserByNameAndPassword(id);
        //用户名存在
        if (user != null)
        {
            setAttr("error","该用户名已经存在，换一个吧");
            render("register.html");
        } else
        {
            User user1 = new User();
            user1.setId(id);
            user1.setPassword(password);
            user1.setIsAdmin(0);
            user1.setChecked(1);
            user1.setTUsed(0);
            user1.setTLast(5000);
            user1.setPUsed(0);
            user1.setPLast(50);
            user1.save();
            render("index.html");
        }
    }

}



