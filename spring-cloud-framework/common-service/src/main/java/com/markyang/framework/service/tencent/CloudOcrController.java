package com.markyang.framework.service.tencent;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.tencent.IdentityCardInfoDto;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * OCR识别控制器
 *
 * @author yangchangliang
 * @version 1
 */
@RestController
@RequestMapping("/cloud/ocr")
@Slf4j
@AllArgsConstructor
public class CloudOcrController {

    private final CloudOcrService cloudOcrService;

    /**
     * 解析身份证图片信息
     * @param file 身份证文件对象
     * @param side 身份证正反面
     * @return 结果对象
     */
    @PostMapping(value = "/identityCard/{side}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<IdentityCardInfoDto> recognizeIdentityCard(@RequestParam MultipartFile file, @PathVariable String side) {
        return this.cloudOcrService.recognizeIdentityCard(file, StringUtils.equals("front", side))
            .map(identityCardInfoDto -> ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, identityCardInfoDto))
            .orElse(ResultVo.error("身份证解析失败"));
    }
}
