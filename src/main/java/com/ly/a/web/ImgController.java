package com.ly.a.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ly.service.base.DateUtil;
import com.ly.service.base.FileUtil;
import com.ly.service.base.ImageUtil;
import com.ly.service.base.UmImage;

import net.coobird.thumbnailator.Thumbnails;

/**
 * desc：图片处理Controller
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:56
 */
@Controller
@RequestMapping("/image/*")
public class ImgController{

	/***
	 * 存储公共图片
	 * 
	 * @param imgFile MultipartFile
	 * @return String
	 */
	@RequestMapping("/saveCommImg")
	@ResponseBody
	public String saveCommImg(@RequestParam MultipartFile imgFile,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String> commConfig = (Map<String, String>) request
				.getServletContext().getAttribute("commConfig");
		UmImage umImage = new UmImage();
		String newImageName = cutSaveImg(imgFile, commConfig.get("img_comm_path"), DateUtil.getYmd(), 1,1, 1, 1);
//				ImageUtil.saveImg(imgFile,commConfig.get("img_comm_path"), DateUtil.getYmd());
		if (newImageName.equals("")) {
			umImage.setState("uploadError");
			return JSON.toJSONString(umImage);
		} else {
			umImage.setName(newImageName);
			umImage.setState("uploadSuccess");
			umImage.setUrl(commConfig.get("img_comm_url") + newImageName);
			return JSON.toJSONString(umImage);
		}
	}

	
	public  String cutSaveImg(MultipartFile imgFile,String path,String s,int x,int y,int w,int h){
		String str=ImageUtil.getRandomName(imgFile.getOriginalFilename());
		File a;
		try {
			a = FileUtil.createFile(new File(path+s+str));
			Thumbnails.of(imgFile.getInputStream()).scale(1.0D).outputQuality(0.9d).toFile(a);
			//Thumbnails.of(imgFile.getInputStream()).sourceRegion(x, y, w,h).scale(1.0D).toFile(a);
			return s+str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	/***
	 * 存储umeditor图片
	 * 
	 * @param upfile MultipartFile
	 * @return String
	 */
	@RequestMapping("/saveEditorImg")
	@ResponseBody
	public String saveEditorImg(@RequestParam MultipartFile upfile,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String> commConfig = (Map<String, String>) request
				.getServletContext().getAttribute("commConfig");
		UmImage umImage = new UmImage();
		String newImageName = cutSaveImg(upfile, commConfig.get("img_editor_path"), DateUtil.getYmd(), 1,1, 1, 1);
	//	String newImageName = ImageUtil.saveImg(upfile,commConfig.get("img_editor_path"), DateUtil.getYmd());
		if (newImageName.equals("")) {
			umImage.setState("UNKNOWN");
			return JSON.toJSONString(umImage);
		} else {
			umImage.setName(newImageName);
			umImage.setState("SUCCESS");
			umImage.setUrl(commConfig.get("img_editor_url") + newImageName);
			umImage.setOriginalName(newImageName);
			umImage.setTitle("");
			return JSON.toJSONString(umImage);
		}
	}

	/***
	 * 存储商品图片
	 * 
	 * @param imgFile MultipartFile
	 * @return String
	 */
	@RequestMapping("/saveItemImg")
	@ResponseBody
	public String saveItemImg(@RequestParam MultipartFile imgFile,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String> commConfig = (Map<String, String>) request
				.getServletContext().getAttribute("commConfig");
		UmImage umImage = new UmImage();
		String newImageName = ImageUtil.saveImg(imgFile,
				commConfig.get("img_item_path"), DateUtil.getYmd());
		if (newImageName.equals("")) {
			umImage.setState("wERROR");
			return JSON.toJSONString(umImage);
		} else {
			umImage.setName(newImageName);
			umImage.setState("SUCCESS");
			umImage.setUrl(commConfig.get("img_item_url") + newImageName);
			return JSON.toJSONString(umImage);
		}
	}

	/***
	 * 存储商品图片,针对dropzone
	 * 
	 * @param file MultipartFile
	 * @return String
	 */
	@RequestMapping("/saveImg")
	@ResponseBody
	public String saveImg(@RequestParam MultipartFile file,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String> commConfig = (Map<String, String>) request
				.getServletContext().getAttribute("commConfig");
		UmImage umImage = new UmImage();
		String newImageName = ImageUtil.saveImg(file,
				commConfig.get("img_item_path"), DateUtil.getYmd());
		if (newImageName.equals("")) {
			umImage.setState("tERROR");
			return JSON.toJSONString(umImage);
		} else {
			umImage.setName(newImageName);
			umImage.setState("SUCCESS");
			umImage.setUrl(commConfig.get("img_item_url") + newImageName);
			return JSON.toJSONString(umImage);
		}
	}

	/***
	 * 存储多张商品图片,针对dropzone
	 * 
	 * @param file MultipartFile
	 * @return String
	 */
	@RequestMapping("/saveImgs")
	@ResponseBody
	public String saveImgs(
			@RequestParam(value = "file[]") MultipartFile[] file,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String> commConfig = (Map<String, String>) request
				.getServletContext().getAttribute("commConfig");
		List<UmImage> umList = new ArrayList<UmImage>();
		for (MultipartFile f : file) {
			UmImage umImage = new UmImage();
			String newImageName = ImageUtil.saveImg(f,
					commConfig.get("img_item_path"), DateUtil.getYmd());
			if (newImageName.equals("")) {
				umImage.setState("ERROR");
				return JSON.toJSONString(umImage);
			} else {
				umImage.setName(newImageName);
				umImage.setState("SUCCESS");
				umImage.setUrl(commConfig.get("img_item_url") + newImageName);
			}
			umList.add(umImage);
		}
		return JSON.toJSONString(umList);
	}
}
