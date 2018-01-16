package com.ruolan.kotlinserver.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
//首先使用注解@EnableTransactionManagement 开启事务支持后
//在Service方法上添加@Transactional就可以
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

    @Autowired
    //注入DataSourceConfiguration里面的dataSource 通过createDataSource获取
    private DataSource dataSource;

    /**
     * 关于事务管理  需要返回PlatformTransactionManager
     *
     * @return PlatformTransactionManager
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
