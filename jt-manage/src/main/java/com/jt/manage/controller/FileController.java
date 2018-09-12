package com.jt.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
  //如果文件上传完成后，再次重定向到文件上传页面
	/**
	 * 重定向的方式：
	 *     1.利用response对象
	 *     2.利用关键字 redirect
	 * 文件上传的步骤：
	 *     1.先获取文件的名称
	 *     2.定义文件上传的路径
	 *     3.调用mvc的工具类  输出文件
	 * @param fileName 与页面中的input的name保持一致
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileName) throws IllegalStateException, IOException{
		//1.获取文件名称
		String name = fileName.getOriginalFilename();
		//2.定义文件上传的路径
		String path = "d:/upload";
		//3.判断路径是否存在
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		fileName.transferTo(new File(path+"/"+name));
		return "redirect:/file.jsp";
	}

	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile){
		return fileService.uploadFile(uploadFile);
	}
	
}
