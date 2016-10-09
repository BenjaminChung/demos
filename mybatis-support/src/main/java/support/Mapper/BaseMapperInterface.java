package support.Mapper;


import support.query.BaseEntity;
import support.query.Page;
import support.query.Query;
import support.query.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * 通用接口
 * Created by benjaminchung on 16/9/26.
 */
public interface BaseMapperInterface<E extends Serializable, T extends BaseEntity> {

    String PO_KEY = "po";

    String ORDER_KEY = "tableSorts";

    /**
     * @param entity
     * @return 返回主键
     */
    E saveOrUpdate(T entity);

    /**
     * 提供新增方法，适合主键由外部提供的新增方法
     *
     * @param entity
     * @return
     */
    E save(T entity);

    /**
     * 查询
     *
     * @param pk
     * @return
     */
    T findOne(E pk);

    /**
     * 删除
     *
     * @param pk
     * @return 返回影响行数
     */
    Integer deleteOne(E pk);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteMulti(List<E> ids);

    /**
     * 删除所有记录
     *
     * @return
     */
    Integer flushAll();

    /**
     * 列表查询
     *
     * @param query
     * @param sorts
     * @return
     */
    List<T> selectList(Query query, Sort sorts);

    /**
     * 分页查询
     *
     * @param pager
     * @param query
     * @param sorts
     * @return
     */
    Page<T> getPage(Page<T> pager, Query query, Sort sorts);

}
