# common

个人通用基础库,沉淀所有项目都要重复写的样板代码。遵循《阿里巴巴Java开发手册》与整洁代码原则(Clean Code)。

## 模块

| 模块 | 内容 | 依赖 |
|---|---|---|
| `common-core` | `Result` 统一响应、`PageResult` 分页、`ErrorCode`/`CommonErrorCode` 错误码体系、`BizException` 业务异常、`BusinessAssert` 业务断言 | Hutool 5.8.36 |
| `common-web-starter` | `GlobalExceptionHandler` 全局异常处理、跨域自动配置、Knife4j 接口文档(`/doc.html`) | Spring Boot Web、Knife4j 4.5.0(springdoc 已强制升级 2.8.6) |
| `common-mybatis-starter` | MyBatis Plus 分页插件(单页上限 500)、`BaseEntity` 实体基类(雪花主键 + 审计字段自动填充) | MyBatis Plus 3.5.7 |

所有第三方版本统一在 `common-parent` 的 `dependencyManagement` 收口。

## 新项目如何使用

1. `pom.xml` 的 `dependencyManagement` 中 import 本工程(版本收口),并声明 JitPack 仓库:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.github.word1230.common</groupId>
            <artifactId>common-parent</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

2. 引入 starter,全局异常处理与跨域即自动生效,无需任何配置类:

```xml
<dependency>
    <groupId>com.github.word1230.common</groupId>
    <artifactId>common-web-starter</artifactId>
</dependency>
```

3. 业务代码中直接使用:

```java
// 抛业务异常,全局异常处理器会统一翻译成 Result
BusinessAssert.isTrue(order != null, OrderErrorCode.ORDER_NOT_FOUND);

// 返回统一响应
return Result.success(data);
```

4. 项目专属错误码:定义枚举实现 `ErrorCode`(参考 `project-template` 的 `ExampleErrorCode`)。

### MyBatis Plus 使用

- 实体继承 `BaseEntity`(自带雪花 id、createTime/updateTime 自动填充),Mapper 继承 `BaseMapper<T>` 即可单表 CRUD;
- 分页用 `Page<T>`,分页插件已自动装配,单页上限 500 条;
- 数据库类型默认 MySQL,可通过 `common.mybatis.db-type` 调整;
- 数据库驱动自带 MySQL,用其他数据库时在项目里替换依赖即可。

### 接口文档

引入 starter 后 Knife4j 已自动生效,启动项目访问 `http://localhost:8080/doc.html`。

### 跨域配置(application.yml)

```yaml
common:
  web:
    cors:
      enabled: true                    # 默认 true,设为 false 可整体关闭
      allowed-origin-patterns: ["https://www.example.com"]   # 生产环境务必收紧
      allow-credentials: false
      max-age-seconds: 3600
```

## 发布(JitPack)

本库通过 [JitPack](https://jitpack.io) 分发,任何机器 clone 业务项目后直接构建,无需本地 install:

1. 推送本仓库到 GitHub(仓库名 `common`);
2. 打 tag 并推送:`git tag 1.0.0 && git push origin 1.0.0`;
3. JitPack 首次构建需在 https://jitpack.io/com/github/word1230/common/1.0.0 手动触发(之后自动);
4. 升级版本:改各 pom 的 `revision`/版本号,再打对应 tag。

## 本地开发

- 构建:`mvn install -DskipTests`,装进本地仓库供未发布版本先行调试。
