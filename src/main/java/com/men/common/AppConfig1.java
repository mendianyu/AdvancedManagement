package com.men.common;

import com.jfinal.config.*;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.men.common.model._MappingKit;
import com.men.index.IndexController;
import com.men.picture.PictureController;
import com.men.translate.TranslateController;
import com.men.user.UserController;

public class AppConfig1 extends JFinalConfig
{
    static Prop p;

    /**
     * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
     */
    public static void main(String[] args)
    {
        UndertowServer.start(AppConfig1.class);
    }

    /**
     * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
     * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
     */
    static void loadConfig()
    {
        if (p == null)
        {
            p = PropKit.useFirstFound("demo-config-pro.txt", "demo-config-dev.txt");
        }
    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me)
    {
        loadConfig();

        me.setDevMode(p.getBoolean("devMode", false));

        /**
         * 支持 Controller、Interceptor、Validator 之中使用 @Inject 注入业务层，并且自动实现 AOP
         * 注入动作支持任意深度并自动处理循环注入
         */
        me.setInjectDependency(true);

        // 配置对超类中的属性进行注入
        me.setInjectSuperClass(true);
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me)
    {
        // 第三个参数为该Controller的视图存放路径
        me.add("/", IndexController.class, "/index");
        // 第三个参数省略时默认与第一个参数值相同，在此即为 "/user
        me.add("/user", UserController.class);
        me.add("/translate", TranslateController.class);
        me.add("/picture", PictureController.class);
    }

    @Override
    public void configEngine(Engine me)
    {
        me.addSharedFunction("/common/_layout.html");
        me.addSharedFunction("/common/_paginate.html");
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me)
    {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("username"), p.get("password").trim());
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        me.add(arp);
    }

    public static DruidPlugin createDruidPlugin()
    {
        loadConfig();

        return new DruidPlugin(p.get("jdbcUrl"), p.get("username"), p.get("password").trim());
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me)
    {
        me.add(new SessionInViewInterceptor());
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me)
    {

    }
}
