package com.cheems.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MyBatis Plus 配置项,前缀为 common.mybatis。
 */
@ConfigurationProperties(prefix = "common.mybatis")
public class CommonMybatisProperties {

    /** 数据库类型,决定分页插件生成的方言 SQL,默认 MySQL */
    private DbType dbType = DbType.MYSQL;

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }
}
