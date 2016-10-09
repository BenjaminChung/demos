package support.Mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import support.interceptor.PageInterceptor;
import support.interceptor.ReflectHelper;
import support.query.BaseEntity;
import support.query.Page;
import support.query.Query;
import support.query.Sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用Mapper
 * Created by benjaminchung on 16/9/26.
 */
public abstract class AbstractBaseMapper<E extends Serializable,T extends BaseEntity> implements BaseMapperInterface<E,T> {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;


    @Override
    public E saveOrUpdate(T entity) {
        //断言getSqlNamespace()是否实现
        checkSqlNamespace();
        if (entity.getId() == null) {
            this.save(entity);
        } else {
            sqlSessionTemplate.update(this.getSqlNamespace() + ".update", entity);
        }
        return (E)entity.getId();
    }

    @Override
    public E save(T entity) {
        checkSqlNamespace();
        sqlSessionTemplate.insert(this.getSqlNamespace() + ".save", entity);
        return (E)entity.getId();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findOne(E pk) {
        checkSqlNamespace();
        return (T) sqlSessionTemplate.selectOne(this.getSqlNamespace() + ".findOne", pk);
    }

    public Integer deleteOne(E pk) {
        //断言getSqlNamespace()是否实现
        checkSqlNamespace();
        return sqlSessionTemplate.delete(this.getSqlNamespace() + ".deleteOne", pk);
    }

    public Integer deleteMulti(List<E> ids){
        return sqlSessionTemplate.delete(this.getSqlNamespace()+".deleteMulti",ids);
    }

    @Override
    public Integer flushAll(){
        return sqlSessionTemplate.delete(this.getSqlNamespace()+".flushAll");
    }


    @Override
    public List<T> selectList(Query query, Sort sorts) {
        //断言getSqlNamespace()是否实现
        checkSqlNamespace();
        Map<String, Object> parameter = new HashMap<String, Object>();
        Map<String, Object> tmpMap = ReflectHelper.getObjectAsMap(query);
        parameter.putAll(tmpMap);
        parameter.put(ORDER_KEY, null == sorts ? null : sorts.getTabelSort());
        return sqlSessionTemplate.selectList(this.getSqlNamespace() + ".selectList", parameter);
    }


    @Override
    public Page<T> getPage(@Param(PageInterceptor.PAGE_KEY) Page<T> page, Query query, Sort sorts) {
        //断言getSqlNamespace()是否实现
        checkSqlNamespace();
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(PageInterceptor.PAGE_KEY, page);
        Map<String, Object> tmpMap = ReflectHelper.getObjectAsMap(query);
        parameter.putAll(tmpMap);
        parameter.put(ORDER_KEY, null == sorts ? null : sorts.getTabelSort());
        sqlSessionTemplate.selectList(this.getSqlNamespace() + ".getPageList", parameter);
        return page;
    }

    /**
     * 指定命名空间
     *
     * @return
     */
    public abstract String getSqlNamespace();


    /**
     * 检查各子类是否实现sqlNamespace()
     */
    protected void checkSqlNamespace() {
        if (null == this.getSqlNamespace() || this.getSqlNamespace().trim().isEmpty()) {
            throw new RuntimeException("no implement getSqlNamespace(),can't find the sqlId.");
        }
    }
}