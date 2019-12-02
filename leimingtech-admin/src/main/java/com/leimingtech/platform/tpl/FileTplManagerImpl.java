package com.leimingtech.platform.tpl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.leimingtech.platform.tpl.Zipper.FileEntry;





public class FileTplManagerImpl implements TplManager {
	
	
	private static Logger log = LoggerFactory
			.getLogger(FileTplManagerImpl.class);

	public int delete(String[] names) {
		File f;
		int count = 0;
		for (String name : names) {
			f = new File(name);
			if (f.isDirectory()) {
				if (FileUtils.deleteQuietly(f)) {
					count++;
				}
			} else {
				if (f.delete()) {
					count++;
				}
			}
		}
		return count;
	}

	public Tpl get(String name) {
		File f = new File(TemplateContents.FRONT_PATH+name);
		if (f.exists()) {
			return new FileTpl(f, root);
		} else {
			return null;
		}
	}

	public List<Tpl> getListByPrefix(String prefix) {
		File f = new File(TemplateContents.FRONT_PATH+prefix);
		String name = prefix.substring(prefix.lastIndexOf("/") + 1);
		File parent;
		if (prefix.endsWith("/")) {
			name = "";
			parent = f;
		} else {
			parent = f.getParentFile();
		}
		if (parent.exists()) {
			File[] files = parent.listFiles(new PrefixFilter(name));
			if (files != null) {
				List<Tpl> list = new ArrayList<Tpl>();
				for (File file : files) {
					list.add(new FileTpl(file, root));
				}
				return list;
			} else {
				return new ArrayList<Tpl>(0);
			}
		} else {
			return new ArrayList<Tpl>(0);
		}
	}

	public List<String> getNameListByPrefix(String prefix) {
		List<Tpl> list = getListByPrefix(prefix);
		List<String> result = new ArrayList<String>(list.size());
		for (Tpl tpl : list) {
			result.add(tpl.getName());
		}
		return result;
	}

	public List<Tpl> getChild(String path) {
		File file = new File(TemplateContents.FRONT_PATH+path);
		
		File[] child = file.listFiles();
		if (child != null) {
			List<Tpl> list = new ArrayList<Tpl>(child.length);
			for (File f : child) {
				list.add(new FileTpl(f, root));
			}
			return list;
		} else {
			return new ArrayList<Tpl>(0);
		}
	}

	public void rename(String orig, String dist) {
	
		File of = new File(orig);
	
		File df = new File(dist);
		try {
			if (of.isDirectory()) {
				FileUtils.moveDirectory(of, df);
			} else {
				FileUtils.moveFile(of, df);
			}
		} catch (IOException e) {
			log.error("Move template error: " + orig + " to " + dist, e);
		}
	}

	public void save(String name, String source, boolean isDirectory) {
	
		File f = new File(name);
		if (isDirectory) {
			f.mkdirs();
		} else {
			try {
				FileUtils.writeStringToFile(f, source, "UTF-8");
			} catch (IOException e) {
				log.error("Save template error: " + name, e);
				throw new RuntimeException(e);
			}
		}
	}

	public void save(String path, MultipartFile file) {
		File f = new File(path, file
				.getOriginalFilename());
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			log.error("upload template error!", e);
		} catch (IOException e) {
			log.error("upload template error!", e);
		}
	}

	public void update(String name, String source) {
		String real = TemplateContents.FRONT_PATH+name;
		File f = new File(real);
		try {
			FileUtils.writeStringToFile(f, source, "UTF-8");
		} catch (IOException e) {
			log.error("Save template error: " + name, e);
			throw new RuntimeException(e);
		}
	}

	private String root;

	private RealPathResolver realPathResolver;

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
		root = realPathResolver.get("");
	}

	private static class PrefixFilter implements FileFilter {
		private String prefix;

		public PrefixFilter(String prefix) {
			this.prefix = prefix;
		}

		public boolean accept(File file) {
			String name = file.getName();
			return file.isFile() && name.startsWith(prefix);
		}
	}

	@Override
	public List<FileEntry> export(String root) {
		List<FileEntry> fileEntrys = new ArrayList<FileEntry>();
		File tpl = new File(root);
		fileEntrys.add(new FileEntry("", "", tpl));
		File res = new File(root);
		if (res.exists()) {
			for (File r : res.listFiles()) {
				fileEntrys.add(new FileEntry(root, r));
			}
		}
		return fileEntrys;
	}

	@Override
	public String[] getSolutions(String path) {
		File tpl = new File(TemplateContents.FRONT_PATH+path);
		return tpl.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		});
	}

	@Override
	public void downFile(String name,HttpServletResponse response) {
		try {
            // path是指欲下载的文件的路径。
            File file = new File(TemplateContents.FRONT_PATH+name);
            // 取得文件名。
            String filename = file.getName();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(TemplateContents.FRONT_PATH+name));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

	}

}
