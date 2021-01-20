package com.vetologic.medbill.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public class FileUploader {

	public boolean uploadFiles(MultipartFile file, String id, String subFolderName) {
		try {
			if ((file != null) && (!file.isEmpty())) {
				byte[] bytes = file.getBytes();

				Path rootPath = FileSystems.getDefault().getPath("").toAbsolutePath();
				File dir = new File(rootPath + File.separator + "Uploads" + File.separator + "Item-Image"
						+ File.separator + id + File.separator + subFolderName);

				if (!dir.exists())
					dir.mkdirs();

				File serverFile = new File(
						dir.getAbsolutePath() + File.separator + id + "_" + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
