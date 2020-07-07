package cn.canye365.mybatisplus.demo.mapper;

import cn.canye365.mybatisplus.demo.entity.Users;
import cn.canye365.mybatisplus.demo.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * UserMapper测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 普通查询
     */
    @Test
    public void testSelect() {
        List<Users> users = this.usersMapper.selectList(null);
        for(Users user : users){
            System.out.println(user);
        }
    }

    /**
     * 条件构造器Wrapper
     * 官网：https://mp.baomidou.com/guide/wrapper.html#abstractwrapper
     */
    @Test
    public void testSelect2() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 2);
        List<Users> users = this.usersMapper.selectList(queryWrapper);
        for(Users user : users){
            System.out.println(user);
        }
    }
    @Test
    public void testPage(){
        Page<Users> page = new Page<>(3, 2);
        IPage<Users> userIpage = this.usersMapper.selectPage(page, null);
        System.out.println("总条数------->  " + userIpage.getTotal());
        System.out.println("当前页数-----> " + userIpage.getCurrent());
        System.out.println("总页数-------> " + userIpage.getPages());
        System.out.println("当前每页显示的页数-----> " + userIpage.getSize());

        List<Users> users = userIpage.getRecords();
        for(Users user : users){
            System.out.println(user);
        }
    }
}
