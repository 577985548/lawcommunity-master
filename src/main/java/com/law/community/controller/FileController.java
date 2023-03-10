package com.law.community.controller;


import com.law.community.dto.FileDTO;
import com.law.community.enums.PlatformEnum;
import com.law.community.utils.AliyunUtil;
import com.law.community.properties.AliyunProperties;
import com.law.community.properties.ArticleProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class FileController {

  @Autowired
  private ArticleProperties articleProperties;

  @RequestMapping("/file/upload")
  @ResponseBody
  public FileDTO upload(HttpServletRequest request) {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile file = multipartRequest.getFile("editormd-image-file");// html 的name
    // 获取阿里云 OSS 相关配置信息
    AliyunProperties aliyun = articleProperties.getAliyun();

    String url = AliyunUtil.uploadFileToOss(PlatformEnum.ARTICEL, file, aliyun);
    if(StringUtils.isNotBlank(url)){
      // 上传success
      FileDTO fileDTO = new FileDTO();
      fileDTO.setSuccess(1);
      fileDTO.setUrl(url);
      return fileDTO;
    }else {
          FileDTO fileDTO = new FileDTO();
          fileDTO.setSuccess(0);
          fileDTO.setMessage("上传失败111");
          return fileDTO;
    }

  }
}


//    try {
//            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(1);
//            fileDTO.setUrl(fileName);
//            return fileDTO;
//            } catch (Exception e) {
//            e.printStackTrace();
//            log.error("upload error", e);
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(0);
//            fileDTO.setMessage("上传失败");
//            return fileDTO;
//            }