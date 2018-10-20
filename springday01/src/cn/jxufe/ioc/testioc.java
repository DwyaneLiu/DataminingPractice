package cn.jxufe.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.TestExecutionListeners;

public class testioc {

    @Test
    public void testUser(){

        //1.加载spring配置文件，根据配置文件创建对象
        ApplicationContext context  = new ClassPathXmlApplicationContext("bean1.xml");
        //2.得到配置创建的对象,括号里
        User user = (User)context.getBean("user");
        user.add();
    }
}
