package cn.canye365.mybatisplus.demo.service.impl;

import cn.canye365.mybatisplus.demo.entity.Users;
import cn.canye365.mybatisplus.demo.mapper.UsersMapper;
import cn.canye365.mybatisplus.demo.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author canye
 * @since 2020-07-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
