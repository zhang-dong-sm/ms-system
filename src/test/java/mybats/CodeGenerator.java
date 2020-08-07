package mybats;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeGenerator {

    private static final String DB_IP = "10.0.10.41"; //数据库地址
    private static final String DB_PORT = "3306"; //数据库端口
    private static final String DB_NAME = "shinemo_isv"; //数据库名称
    private static final String DB_USER = "root"; //用户名
    private static final String DB_PASSWORD = "shinemo123"; //密码
    private static final String ENTITY_PACKAGE_NAME = "com.shinemo.bi.ms.system.po";
    private static final String MAPPER_PACKAGE_NAME = "com.shinemo.bi.ms.system.dao";
    private static final String XML_PACKAGE_NAME = DB_NAME;

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=utf8", DB_IP, DB_PORT, DB_NAME));
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PASSWORD);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath);
        gc.setAuthor("shinemo");
        gc.setOpen(false);
        gc.setMapperName("%sDao");
        gc.setEntityName("%sPo");
        gc.setFileOverride(true);
        gc.setDateType(DateType.TIME_PACK);
        mpg.setGlobalConfig(gc);

        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity(ENTITY_PACKAGE_NAME);
        pc.setMapper(MAPPER_PACKAGE_NAME);
        pc.setXml(XML_PACKAGE_NAME);
        Map<String, String> pathInfo = new HashMap<>();
        pathInfo.put(ConstVal.ENTITY_PATH, joinPath(gc.getOutputDir() + "/src/main/java", pc.getEntity()));
        pathInfo.put(ConstVal.MAPPER_PATH, joinPath(gc.getOutputDir() + "/src/main/java", pc.getMapper()));
        pathInfo.put(ConstVal.XML_PATH, joinPath(gc.getOutputDir() + "/src/main/resources", pc.getXml()));
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setEntityColumnConstant(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}