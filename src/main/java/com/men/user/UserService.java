package com.men.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.men.common.model.User;

import java.util.List;


/**
 * @author mendianyu
 */
public class UserService
{

    private User dao = new User().dao();

    /**
     * 遍历用户列表
     */
    public Page<User> paginate(int pageNumber, int pageSize)
    {
        return dao.paginate(pageNumber, pageSize, "select *", "from user order by id asc");
    }

    /**
     * 根据id查找
     */
    public User findById(String id)
    {
        return dao.findById(id);
    }

    /**
     * 根据id删除
     */
    public void deleteById(String id)
    {
        dao.deleteById(id);
    }

    /**
     * 用户登录
     */
    public User findUserByNameAndPassword(String id)
    {
        return dao.findFirst("select * from user where id = ? ", id);
    }

    /**
     * 模糊查询
     */
    public List<Record> searchUsers(String keyword)
    {
        String sql = "select * from user where id like ? or isAdmin like ? or checked like ?";
        keyword = "%" + keyword.trim() + "%";

        List<Record> Users = Db.find(sql, keyword, keyword, keyword);
        return Users;
    }

    public List<User> findAll()
    {
        return dao.findAll();
    }


//    public Page<User> paginate(int pageNumber, int pageSize, String keyword)
//    {
//        String select = "SELECT *";
//        String from = "FROM User";
//        String where = "WHERE id LIKE ? OR name LIKE ? OR classes LIKE ? OR age LIKE ? OR sex LIKE ? OR tel LIKE ?";
//        String orderBy = "ORDER BY id ASC";
//
//        // 处理关键字，如果为 null，则设为空字符串
//        keyword = (keyword != null) ? "%" + keyword.trim() + "%" : "";
//
//        // 构造带有条件的分页查询语句
//        String sql = select + " " + from + " " + where + " " + orderBy;
//
//        // 构造模糊查询的参数
//        Object[] params = {keyword, keyword, keyword, keyword, keyword, keyword};
//
//        // 调用 JFinal 的 paginate 方法进行分页查询
//        return dao.paginate(pageNumber, pageSize, select, from + " " + where, params, orderBy);
//    }

}
