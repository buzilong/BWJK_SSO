package util;

import com.alibaba.fastjson.JSONObject;
import com.bwjk.sso.common.config.Constant;
import com.bwjk.sso.common.util.JwtUtil;
import com.bwjk.sso.model.request.LoginRequestDTO;
import io.jsonwebtoken.Claims;
import org.testng.annotations.Test;

/**
 * Created by DIY on 2017/7/13.
 */
public class JwtTest {

    @Test
    public void createJwtTest_1() {

        try {
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            JwtUtil jwt = new JwtUtil();
            loginRequest.setUserName("Zhangxl");
            String subject = JwtUtil.generalSubject(loginRequest);
            String token = jwt.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);
            String refreshToken = jwt.createJWT(Constant.JWT_ID, subject, Constant.JWT_REFRESH_TTL);

            JSONObject jo = new JSONObject();
            jo.put("token", token);
            jo.put("refreshToken", refreshToken);

            System.out.println(jo.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void praseJwtTest_1() {
        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE0OTk5MzE0NzMsInN1YiI6IntcInVzZXJOYW1lXCI6XCJaaGFuZ3hsXCJ9IiwiZXhwIjoxNDk5OTM1MDczfQ.iA2oFq1ahLjHs-ScvAI7vCbvI9Ne_b2xTUdeQvYt1yo";
            JwtUtil jwt = new JwtUtil();
            Claims claims = jwt.parseJWT(token);
            String json = claims.getSubject();
            LoginRequestDTO loginRequest = JSONObject.parseObject(json, LoginRequestDTO.class);

            System.out.println("userName:" + loginRequest.getUserName());

            String subject = JwtUtil.generalSubject(loginRequest);
            String refreshToken = jwt.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);

            System.out.println("refreshToken:" + refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
        }
    }
}