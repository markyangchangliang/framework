package com.markyang.framework.service.common;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用数据字典获取控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/28 10:55 上午 星期二
 */
@RestController
@RequestMapping("/generic/fieldDict")
@Slf4j
@AllArgsConstructor
public class GenericFieldDictController {

    private final FieldDictTranslatorService fieldDictTranslatorService;

    /**
     * 获取民族数据字典
     *
     * @return 民族数据字典
     */
    @GetMapping("/nation")
    public ResultVo<List<DictDto>> getNationDict() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictTranslatorService.getFieldDict(FrameworkConstants.REGIONAL_MEDICAL_APP_ID, "generic", "nation", true));
    }

    /**
     * 获取性别数据字典
     *
     * @return 性别数据字典
     */
    @GetMapping("/gender")
    public ResultVo<List<DictDto>> getGenderDict() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictTranslatorService.getFieldDict(FrameworkConstants.REGIONAL_MEDICAL_APP_ID, "generic", "gender", true));
    }

    /**
     * 获取职员职称数据字典
     *
     * @return 职员职称数据字典
     */
    @GetMapping("/workerPositional")
    public ResultVo<List<DictDto>> getWorkerPositionalDict() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictTranslatorService.getFieldDict(FrameworkConstants.REGIONAL_MEDICAL_APP_ID, "generic", "worker_positional", true));
    }

    /**
     * 获取职员默认排序数据字典
     *
     * @return 职员默认排序数据字典
     */
    @GetMapping("/workerSort")
    public ResultVo<List<DictDto>> workerSort() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictTranslatorService.getFieldDict(FrameworkConstants.REGIONAL_MEDICAL_APP_ID, "generic", "worker_sort", true));
    }
}
