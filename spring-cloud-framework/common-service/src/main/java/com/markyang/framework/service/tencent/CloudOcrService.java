package com.markyang.framework.service.tencent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import com.markyang.framework.pojo.dto.tencent.IdentityCardInfoDto;
import com.markyang.framework.util.DateTimeUtils;
import com.markyang.framework.util.IoOperationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * OCR图像识别服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
@AllArgsConstructor
public class CloudOcrService {

    private final OcrClient ocrClient;

    /**
     * 识别身份证照片
     * @param file 身份证照片文件
     * @param isFront 是否是正面照片
     * @return 识别结果对象Optional
     */
    public Optional<IdentityCardInfoDto> recognizeIdentityCard(MultipartFile file, boolean isFront) {
        IDCardOCRRequest request = new IDCardOCRRequest();
        try {
            request.setImageBase64(IoOperationUtils.convertImageToBase64(FilenameUtils.getExtension(file.getOriginalFilename()), file.getInputStream()).orElse(""));
        } catch (IOException e) {
            log.error("图片转换BASE64失败：{}",e.getMessage());
            return Optional.empty();
        }
        request.setCardSide(isFront ? "FRONT" : "BACK");
        try {
            IDCardOCRResponse response = this.ocrClient.IDCardOCR(request);
            // 返回信息
            return Optional.of(
                IdentityCardInfoDto.builder()
                    .name(response.getName())
                    .sex(response.getSex())
                    .address(response.getAddress())
                    .birth(StringUtils.isNotBlank(response.getBirth()) ? DateTimeUtils.parseLocalDate(response.getBirth(), "yyyy/M/d") : null)
                    .nation(response.getNation())
                    .identityCardNumber(response.getIdNum())
                    .authority(response.getAuthority())
                    .validDate(response.getValidDate())
                    .build()
            );
        } catch (TencentCloudSDKException e) {
            log.error("识别身份证图片[{}]出现异常：{}",file.getOriginalFilename(), e.getMessage());
            return Optional.empty();
        }
    }
}
