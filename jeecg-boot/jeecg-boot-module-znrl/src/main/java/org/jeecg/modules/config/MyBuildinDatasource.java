package org.jeecg.modules.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
public class MyBuildinDatasource implements BuildinDatasource {


    private static final String NAME = "MyDataSource";

    @Autowired
    private DataSource dataSource;

    /**
     * 数据源名称
     **/
    @Override
    public String name() {
        return NAME;
    }

    /**
     * 获取连接
     **/
    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.info("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }


}

