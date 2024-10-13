package com.wusuowei.jwtspringboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wusuowei.jwtspringboot.constant.Constants;
import com.wusuowei.jwtspringboot.model.po.User;
import com.wusuowei.jwtspringboot.model.vo.UserVo;
import com.wusuowei.jwtspringboot.service.UserService;
import com.wusuowei.jwtspringboot.utils.JWTUtils;
import com.wusuowei.jwtspringboot.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @description 登录
     * @param user 用户
     * @return {@link R }
     */
    @PostMapping("/login")
    public com.wusuowei.jwtspringboot.utils.R login(@RequestBody User user){
        User userDB = userService.login(user);
        if(userDB==null){
            return R.error(Constants.CODE_500,"没有该用户");
        }

        UserVo userVo = new UserVo();
        userVo.setUsername(userDB.getUsername());
        String token = JWTUtils.getToken(userDB.getId().toString(), userDB.getPassword());
        userVo.setToken(token);
        return R.ok("登录成功").setData(userVo);

    }

    /**
     * @description 注册
     * @param user 用户
     * @return {@link R }
     */
    @PostMapping("/register")
    public com.wusuowei.jwtspringboot.utils.R regiseter(@RequestBody User user){
        userService.register(user);
        return R.ok("注册成功").setData(user);
    }

    /**
     * @description 检查用户名
     * @param username 用户名
     * @return {@link R }
     */
    @GetMapping("/check")
    public com.wusuowei.jwtspringboot.utils.R countByUsername(@RequestParam String username) {
        long count = userService.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if(count!=0){
            return R.error("用户名已存在");
        }
        return R.ok();
    }

    /**
     * @description 身份验证
     *
     * @return {@link R }
     */
    @PostMapping("/authentication")
    public com.wusuowei.jwtspringboot.utils.R authentication(){
        return R.ok();
    }

    @GetMapping("/getUserInfo")
    public com.wusuowei.jwtspringboot.utils.R getUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname","orange");
        map.put("qq","2673152463");
        map.put("address","浙江省");
        map.put("url","");
        map.put("text","在笑大学牲");
        return R.ok().setData(map);
    }
}
