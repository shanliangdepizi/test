package com.jt.manage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	// 本地文件路径
	private String localPath = "D:/upload/";
	private String urlPath = "http://image.jt.com/";
	/**
	 * 1.判断文件是否为图片 2.判断是否为恶意程序 3.分文件存储 4.保证文件不重名，uuid
	 * 
	 * @param uploadFile
	 * @return
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		
		PicUploadResult result = new PicUploadResult();
		// 1.获取图片的名称
		String fileName = uploadFile.getOriginalFilename();
		// 将字符全部小写
		fileName = fileName.toLowerCase();
		// 2.正则表达式
		if (!fileName.matches("^.*(jpg|png|gif)$")) {
			result.setError(1);
			return result;
		}
		// 3.判断是否是恶意程序
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if (width == 0 || height == 0) {
				result.setError(1);
				return result;
			}
			// 以年月日来分文件存储
			String dataDir = new SimpleDateFormat("yyyy/MM/dd")
					.format(new Date())
					.toString();
			// 创建文件夹
			String fileDir = localPath + dataDir;
			File dirFile = new File(fileDir);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			/*
			 * 5.防止文件重名
			 *    截取文件的后缀
			 *    使用uuid当文件名字+随机数(3位)
			 */
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			String uuidName = UUID.randomUUID().toString().replace("-", "");
			int randomNumber = new Random().nextInt(1000);
			String realFileName = uuidName+randomNumber+fileType;
			//6.实现文件的上传
			String realLocalPath = fileDir+"/"+realFileName;
			uploadFile.transferTo(new File(realLocalPath));
			result.setHeight(height+"");
			result.setWidth(width+"");
			
			//实现图片回显
			String realUrlPath = urlPath+dataDir+"/"+realFileName;
			result.setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		return result;
	}

}
