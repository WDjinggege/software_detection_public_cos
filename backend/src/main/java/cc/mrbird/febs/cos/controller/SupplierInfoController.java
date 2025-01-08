package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SupplierInfo;
import cc.mrbird.febs.cos.service.ISupplierInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/supplier-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierInfoController {

    private final ISupplierInfoService supplierInfoService;

    /**
     * 分页获取测试企业信息
     *
     * @param page         分页对象
     * @param supplierInfo 测试企业信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SupplierInfo> page, SupplierInfo supplierInfo) {
        return R.ok(supplierInfoService.selectSupplierPage(page, supplierInfo));
    }

    /**
     * 测试企业信息详情
     *
     * @param id 测试企业ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierInfoService.getById(id));
    }

    /**
     * 测试企业信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(supplierInfoService.list());
    }

    /**
     * 新增测试企业信息
     *
     * @param supplierInfo 测试企业信息
     * @return 结果
     */
    @PostMapping
    public R save(SupplierInfo supplierInfo) {
        supplierInfo.setCode("SUP-" + System.currentTimeMillis());
        supplierInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(supplierInfoService.save(supplierInfo));
    }

    /**
     * 修改测试企业信息
     *
     * @param supplierInfo 测试企业信息
     * @return 结果
     */
    @PutMapping
    public R edit(SupplierInfo supplierInfo) {
        return R.ok(supplierInfoService.updateById(supplierInfo));
    }

    /**
     * 删除测试企业信息
     *
     * @param ids ids
     * @return 测试企业信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierInfoService.removeByIds(ids));
    }
}
