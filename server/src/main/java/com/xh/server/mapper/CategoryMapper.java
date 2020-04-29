package com.xh.server.mapper;

import com.xh.server.model.Category;
import com.xh.server.model.CategoryExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CategoryMapper {
    @SelectProvider(type= CategorySqlProvider.class, method="countByExample")
    long countByExample(CategoryExample example);

    @DeleteProvider(type= CategorySqlProvider.class, method="deleteByExample")
    int deleteByExample(CategoryExample example);

    @Delete({
        "delete from category",
        "where catid = #{catid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer catid);

    @Insert({
        "insert into category (catid, catno, ",
        "name, desn)",
        "values (#{catid,jdbcType=INTEGER}, #{catno,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{desn,jdbcType=VARCHAR})"
    })
    int insert(Category record);

    @InsertProvider(type= CategorySqlProvider.class, method="insertSelective")
    int insertSelective(Category record);

    @SelectProvider(type= CategorySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="catno", property="catno", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="desn", property="desn", jdbcType=JdbcType.VARCHAR)
    })
    List<Category> selectByExample(CategoryExample example);

    @Select({
        "select",
        "catid, catno, name, desn",
        "from category",
        "where catid = #{catid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="catno", property="catno", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="desn", property="desn", jdbcType=JdbcType.VARCHAR)
    })
    Category selectByPrimaryKey(Integer catid);

    @UpdateProvider(type= CategorySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    @UpdateProvider(type= CategorySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    @UpdateProvider(type= CategorySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Category record);

    @Update({
        "update category",
        "set catno = #{catno,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "desn = #{desn,jdbcType=VARCHAR}",
        "where catid = #{catid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Category record);
}