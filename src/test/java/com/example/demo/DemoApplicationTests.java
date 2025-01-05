package com.example.demo;

import com.example.mapper.DemoMapper;
import com.example.model.entity.Demo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

class DemoApplicationTests {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        // mybatis匹配文件目录
        String resource = "mybatis-config.xml";
        // 获取配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 使用SqlSessionFactoryBuilder创建SqlSessionFactory
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、获取sqlSessionFactory对象:
     * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
     * 		注意：【MappedStatement】：代表一个增删改查的详细信息
     *
     * 2、获取sqlSession对象
     * 		返回一个DefaultSQlSession对象，包含Executor和Configuration;
     * 		这一步会创建Executor对象；
     *
     * 3、获取接口的代理对象（MapperProxy）
     * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
     * 		代理对象里面包含了，DefaultSqlSession（Executor）
     * 4、执行增删改查方法
     *
     * 总结：
     * 	1、根据配置文件（全局，sql映射）初始化出Configuration对象
     * 	2、创建一个DefaultSqlSession对象，
     * 		他里面包含Configuration以及
     * 		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
     *  3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
     *  4、MapperProxy里面有（DefaultSqlSession）；
     *  5、执行增删改查方法：
     *  		1）、调用DefaultSqlSession的增删改查（Executor）；
     *  		2）、会创建一个StatementHandler对象。
     *  			（同时也会创建出ParameterHandler和ResultSetHandler）
     *  		3）、调用StatementHandler预编译参数以及设置参数值;
     *  			使用ParameterHandler来给sql设置参数
     *  		4）、调用StatementHandler的增删改查方法；
     *  		5）、ResultSetHandler封装结果
     *  注意：
     *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象（一个sqlSession对象代表和数据库的一次会话   ）
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            Demo demo = mapper.getById(1);
            System.out.println(mapper);
            System.out.println(demo);
        } finally {
            openSession.close();
        }
    }



    // ================ 一级缓存测试  =====================


    // 一级缓存测试
    @Test
    public void testFirstLevelCache() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象（一个sqlSession对象代表和数据库的一次会话   ）
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            Demo demo1 = mapper.getById(1);
            System.out.println("demo1:" + demo1);

            // 再次查询
            Demo demo2 = mapper.getById(1);
            System.out.println("demo2:" + demo2);
            System.out.println(demo1 == demo2);
        } finally {
            openSession.close();
        }
    }

    // 一级缓存失效（不同SqlSession，即不同会话）
    @Test
    public void testFirstLevelCache2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        Demo demo1, demo2;
        try {
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            demo1 = mapper.getById(1);
            System.out.println("demo1:" + demo1);
        } finally {
            openSession.close();
        }

        SqlSession openSession2 = sqlSessionFactory.openSession();

        try {
            DemoMapper mapper = openSession2.getMapper(DemoMapper.class);
            // 再次查询
            demo2 = mapper.getById(1);
            System.out.println("demo2:" + demo2);
        } finally {
            openSession2.close();
        }
        System.out.println(demo1 == demo2);
    }


    // 一级缓存失效(SqlSession相同，查询条件不同)
    @Test
    public void testFirstLevelCache3() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象（一个sqlSession对象代表和数据库的一次会话   ）
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            Demo demo1 = mapper.getById(1);
            System.out.println("demo1:" + demo1);

            // 再次查询
            Demo demo2 = mapper.getById(2);
            System.out.println("demo2:" + demo2);
            System.out.println(demo1 == demo2);
        } finally {
            openSession.close();
        }
    }


    // 一级缓存失效(SqlSession相同，两次查询之间执行了增删改查)
    @Test
    public void testFirstLevelCache4() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象（一个sqlSession对象代表和数据库的一次会话   ）
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            Demo demo1 = mapper.getById(1);
            System.out.println("demo1:" + demo1);

            // 新增操作
            Demo demo = new Demo();
            demo.setId(99);
            demo.setName("hahha");
            mapper.addDemo(demo);

            // 再次查询
            Demo demo2 = mapper.getById(1);
            System.out.println("demo2:" + demo2);
            System.out.println(demo1 == demo2);

        } finally {
            openSession.commit();
            openSession.close();
        }
    }

    // 一级缓存失效(SqlSession相同，手动清除一级缓存)
    @Test
    public void testFirstLevelCache5() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象（一个sqlSession对象代表和数据库的一次会话   ）
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            DemoMapper mapper = openSession.getMapper(DemoMapper.class);
            Demo demo1 = mapper.getById(1);
            System.out.println("demo1:" + demo1);

            // 手动清除一级缓存
            openSession.clearCache();

            // 再次查询
            Demo demo2 = mapper.getById(1);
            System.out.println("demo2:" + demo2);
            System.out.println(demo1 == demo2);

        } finally {
            openSession.close();
        }
    }



}
