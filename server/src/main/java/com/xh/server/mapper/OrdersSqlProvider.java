package com.xh.server.mapper;

import com.xh.server.model.Orders;
import com.xh.server.model.OrdersExample.Criteria;
import com.xh.server.model.OrdersExample.Criterion;
import com.xh.server.model.OrdersExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class OrdersSqlProvider {

    public String countByExample(OrdersExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("orders");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(OrdersExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("orders");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Orders record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("orders");
        
        if (record.getUserid() != null) {
            sql.VALUES("userid", "#{userid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderid() != null) {
            sql.VALUES("orderid", "#{orderid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderdate() != null) {
            sql.VALUES("orderdate", "#{orderdate,jdbcType=DATE}");
        }
        
        if (record.getTotalprice() != null) {
            sql.VALUES("totalprice", "#{totalprice,jdbcType=REAL}");
        }
        
        return sql.toString();
    }

    public String selectByExample(OrdersExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("userid");
        } else {
            sql.SELECT("userid");
        }
        sql.SELECT("orderid");
        sql.SELECT("orderdate");
        sql.SELECT("totalprice");
        sql.FROM("orders");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Orders record = (Orders) parameter.get("record");
        OrdersExample example = (OrdersExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("orders");
        
        if (record.getUserid() != null) {
            sql.SET("userid = #{record.userid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderid() != null) {
            sql.SET("orderid = #{record.orderid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderdate() != null) {
            sql.SET("orderdate = #{record.orderdate,jdbcType=DATE}");
        }
        
        if (record.getTotalprice() != null) {
            sql.SET("totalprice = #{record.totalprice,jdbcType=REAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("orders");
        
        sql.SET("userid = #{record.userid,jdbcType=INTEGER}");
        sql.SET("orderid = #{record.orderid,jdbcType=INTEGER}");
        sql.SET("orderdate = #{record.orderdate,jdbcType=DATE}");
        sql.SET("totalprice = #{record.totalprice,jdbcType=REAL}");
        
        OrdersExample example = (OrdersExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Orders record) {
        SQL sql = new SQL();
        sql.UPDATE("orders");
        
        if (record.getOrderdate() != null) {
            sql.SET("orderdate = #{orderdate,jdbcType=DATE}");
        }
        
        if (record.getTotalprice() != null) {
            sql.SET("totalprice = #{totalprice,jdbcType=REAL}");
        }
        
        sql.WHERE("userid = #{userid,jdbcType=INTEGER}");
        sql.WHERE("orderid = #{orderid,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, OrdersExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}