package cn.canye365.demo.service.impl;

import cn.canye365.demo.domain.User;
import cn.canye365.demo.dto.UserDto;
import cn.canye365.demo.domain.UserExample;
import cn.canye365.demo.dto.PageDto;
import cn.canye365.demo.mapper.UserMapper;
import cn.canye365.demo.service.UserService;
import cn.canye365.demo.utils.CopyUtil;
import cn.canye365.demo.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
/**
 *
 * @author CanYe
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @SuppressWarnings("all")
    private UserMapper userMapper;

    /**
    * 列表查询
    */
    @Override
    public void list(PageDto<UserDto> pageDto) {
        PageHelper.startPage(pageDto.getCurrentPage(), pageDto.getPageSize());  //第一页就是第一页，没有0 => 相当于limit
        // startPage后，会向下找到第一个select语句并进行分页
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = new ArrayList<>();
        for (int i = 0 ; i < userList.size(); i++) {
            User user = userList.get(i);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }
        pageDto.setList(userDtoList);
        // 这个地方不返回了，因为数据都存在了pageDto当中。
        //return ;
    }

    @Override
    public UserDto findById(long id){
        User user = userMapper.selectByPrimaryKey(id);
        UserDto userDao = CopyUtil.copy(user, UserDto.class);
        return userDao;
    }

    /**
     * 保存，有id更新，无id插入
     * @param userDto
     */
    @Override
    public void save(UserDto userDto){
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(user.getId())){
            this.insert(user);
        } else{
            this.update(user);
        }
    }

    private void insert(User user) {
        Date now = new Date();
        user.setId(null);
        userMapper.insert(user);
    }

    private void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void delete(long id) {
        userMapper.deleteByPrimaryKey(id);
    }

}
