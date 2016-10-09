package support.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import yonyou.mss.mybatis.support.query.Page;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * 结果集拦截器
 * Created by benjaminchung on 16/9/26.
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultSetInterceptor implements Interceptor {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object intercept(Invocation invocation) throws Throwable {
        Object obj = invocation.proceed();

        Page p = PageInterceptor.getPage();
        if (p != null) {
            p.setResult((List) obj);
        }
        return obj;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
