package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 内部检验
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IncomeCheckInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 检验单编号
     */
    private String code;

    /**
     * 检验单名称
     */
    private String name;

    /**
     * 项目模块ID
     */
    private Integer materielId;

    /**
     * 项目模块编码
     */
    private String materielCode;

    /**
     * 项目模块名称
     */
    private String materielName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 测试企业ID
     */
    private Integer supplierId;

    /**
     * 测试企业简称
     */
    private String abbreviation;

    /**
     * 测试企业批次号
     */
    private String batchNumber;

    /**
     * 接收数量
     */
    private Integer receiveQuantity;

    /**
     * 检测数量
     */
    private Integer checkQuantity;

    /**
     * 检测人员
     */
    private Integer staffId;

    /**
     * 内部日期
     */
    private String incomingDate;

    /**
     * 不合格数量
     */
    private Integer unqualifiedQuantity;

    /**
     * 检测日期
     */
    private String checkDate;

    /**
     * 检测结果（0.等待检测 1.通过 2.不通过）
     */
    private String checkStatus;

    /**
     * 致命缺陷率
     */
    private BigDecimal fatalDefectRate;

    /**
     * 严重缺陷率
     */
    private BigDecimal criticalDefectRate;

    /**
     * 轻微缺陷率
     */
    private BigDecimal minorDefectRate;

    /**
     * 致命缺陷数量
     */
    private Integer fatalDefectNum;

    /**
     * 严重缺数量
     */
    private Integer criticalDefectNum;

    /**
     * 轻微缺数量
     */
    private Integer minorDefectNum;

    /**
     * 单据状态（0.已创建 1.检测中 2.已完成）
     */
    private String receiptStatus;

    /**
     * 备注
     */
    private String content;

    @TableField(exist = false)
    private String detectionCheck;
}
