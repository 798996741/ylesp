package com.yulun.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.Const;
import com.fh.util.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

@RequestMapping
@Component
public class ApiController {
    Logger log = Logger.getLogger(ApiController.class);
    static Properties prop = new Properties();
    static {
        InputStream in = ApiController.class.getClassLoader().getResourceAsStream("api.properties");

        try {
            prop.load(in);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    HttpServletRequest mRequest;

    @org.springframework.web.bind.annotation.ModelAttribute
    public void initRequest(HttpServletRequest request) {
        mRequest = request;
    }

    /**
     * API调用主 请求
     *
     * @param indata
     *            按照接口语法定义的JSON结构体
     * @param t
     *            请求时间 long 类型
     * @return 输出 JSON 按照接口语法定义的JSON结构体
     * 解决跨域 allowedHeaders = "*", allowCredentials = "true"
     */
    @RequestMapping(value = "/api", produces = "application/json; charset=utf-8")
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String index(@RequestParam(name = "data", required = true, defaultValue = "{}") String indata,
                        @RequestParam(name = "t", required = false, defaultValue = "0") String t) {
        System.out.println(indata);
    	JSONObject data = JSONObject.parseObject(indata);
        JSONObject result = new JSONObject();

        String api = data.getString("api");
        log.info("收到客户端请求 " + api + " " + data.toString());
        if (prop.get(api) != null) {
            String className = prop.getProperty(api);
            result = getResultbyClassName(className, data);
        } else {
            log.info("请求API  [" + api + "] ERROR 未配置 ");
            result.put("statusCode", 300);
            result.put("message", "请求API  [" + api + "] ERROR 未配置 ");
        }
        return result.toString();

    }
    
    
    
    

    /**
     * 主处理过程
     *
     * @param className
     *            业务实现类
     * @param data
     *            前端请求内容
     * @return
     */
    private JSONObject getResultbyClassName(String className, JSONObject data) {
        JSONObject result = null;
        log.info("API ("+className+") 业务操作准备开始");
        try {
            Class callClass = Class.forName(className);

            Object callObject = callClass.newInstance();
            Field[] fields = callClass.getDeclaredFields();
            if (fields!=null && fields.length>0){
                log.info("API 业务操作准备开始-处理注释注入");
            }
            for (Field item : fields) {
                if (item.isAnnotationPresent(org.springframework.beans.factory.annotation.Autowired.class)) {
                    try {
                        String beanname = item.getType().getSimpleName();
                        Object bean = Const.WEB_APP_CONTEXT.getBean(WordUtils.uncapitalize(beanname));
                        if (bean == null) {

                            log.error("API 业务操作准备开始-处理注释注入-未实现"+beanname+" Bean");
                            continue;
                        }
                        boolean boor = item.isAccessible();
                        item.setAccessible(true);
                        item.set(this, bean);
                        item.setAccessible(boor);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
                if (item.isAnnotationPresent(Resource.class)) {
                    String beanname="";
                    try {
                        beanname = ((Resource) item.getAnnotations()[0]).name();
                        if (StringUtils.isEmpty(beanname)) {
                            beanname = item.getType().getSimpleName();
                        }
                        Object bean = Const.WEB_APP_CONTEXT.getBean(WordUtils.uncapitalize(beanname));
                        if (bean == null) {
                            log.error("API 业务操作准备开始-处理注释注入-未实现"+beanname+" Bean");
                            continue;
                        }
                        boolean boor = item.isAccessible();
                        item.setAccessible(true);
                        item.set(callObject, bean);
                        item.setAccessible(boor);
                    } catch (Exception ee) {
                        log.error("API 业务操作准备开始-处理注释注入>注入"+beanname+" Bean 失败>"+ee.getMessage());
                        ee.printStackTrace();
                    }
                }
            }
            CommonIntefate co = (CommonIntefate) callObject;
            result = co.execute(data, mRequest);
            return result;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            log.error("业务实现类需要无参构造函数例 class M {public M(){}}", e);

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("业务实现类访问无法访问private类型", e);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            result.put("statusCode", 300);
            result.put("message", "API业务操作类实现");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            result.put("statusCode", 300);
            result.put("message", "出现了未知的错误");
        } finally {
            log.info("API ("+className+") 业务操作结束");
        }
        return result;
    }
}
