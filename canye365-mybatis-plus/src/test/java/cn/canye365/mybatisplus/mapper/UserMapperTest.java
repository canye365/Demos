package cn.canye365.mybatisplus.mapper;

import cn.canye365.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 普通查询
     */
    @Test
    public void testSelect() {
        List<User> users = this.userMapper.selectList(null);
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 条件构造器Wrapper
     * 官网：https://mp.baomidou.com/guide/wrapper.html#abstractwrapper
     */
    @Test
    public void testSelect2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 2);
        List<User> users = this.userMapper.selectList(queryWrapper);
        for(User user : users){
            System.out.println(user);
        }
    }
    @Test
    public void testPage(){
        Page<User> page = new Page<>(3, 2);
        IPage<User> userIpage = this.userMapper.selectPage(page, null);
        System.out.println("总条数------->  " + userIpage.getTotal());
        System.out.println("当前页数-----> " + userIpage.getCurrent());
        System.out.println("总页数-------> " + userIpage.getPages());
        System.out.println("当前每页显示的页数-----> " + userIpage.getSize());

        List<User> users = userIpage.getRecords();
        for(User user : users){
            System.out.println(user);
        }
    }
}
