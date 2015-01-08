package myhanbok.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class cFTPClient {
	private FTPClient mftp = null;


	public boolean ftpConnect(String host, String username, String password,
			int port) {
		try {
			mftp = new FTPClient();
			mftp.connect(host, port);

			if (FTPReply.isPositiveCompletion(mftp.getReplyCode())) {

				boolean status = mftp.login(username, password);

				mftp.setFileType(FTP.BINARY_FILE_TYPE);
				mftp.enterLocalPassiveMode();

				return status;
			}
		} catch (Exception e) {
			 Log.d("TAG", "Error: could not connect to host " + host);
		}

		return false;
	}

	public boolean ftpDisconnect() {
		try {
			mftp.logout();
			mftp.disconnect();
			return true;
		} catch (Exception e) {
			Log.d("TAG", "Error occurred while disconnecting from ftp server.");
		}

		return false;
	}

	public String ftpGetCurrentWorkingDirectory() {
		try {
			String workingDir = mftp.printWorkingDirectory();
			return workingDir;
		} catch (Exception e) {
			Log.d("TAG", "Error: could not get current working directory.");
		}

		return null;
	}

	public void ftpPrintFilesList(String dir_path) {
		try {
			FTPFile[] ftpFiles = mftp.listFiles(dir_path);
			int length = ftpFiles.length;

			for (int i = 0; i < length; i++) {
				String name = ftpFiles[i].getName();
				boolean isFile = ftpFiles[i].isFile();

				if (isFile) {
					Log.i("TAG", "File : " + name);
				} else {
					Log.i("TAG", "Directory : " + name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean ftpChangeDirectory(String directory_path) {
		try {
			mftp.changeWorkingDirectory(directory_path);
		} catch (Exception e) {
			Log.d("TAG", "Error: could not change directory to " + directory_path);
		}

		return false;
	}

	public boolean ftpMakeDirectory(String new_dir_path) {
		try {
			boolean status = mftp.makeDirectory(new_dir_path);
			return status;
		} catch (Exception e) {
			Log.d("TAG", "Error: could not create new directory named "
					+ new_dir_path);
		}

		return false;
	}

	public boolean ftpRemoveDirectory(String dir_path) {
		try {
			boolean status = mftp.removeDirectory(dir_path);
			return status;
		} catch (Exception e) {
			Log.d("TAG", "Error: could not remove directory named " + dir_path);
		}

		return false;
	}

	public boolean ftpRemoveFile(String filePath) {
		try {
			boolean status = mftp.deleteFile(filePath);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean ftpRenameFile(String from, String to) {
		try {
			boolean status = mftp.rename(from, to);
			return status;
		} catch (Exception e) {
			Log.d("TAG", "Could not rename file: " + from + " to: " + to);
		}

		return false;
	}

	public boolean ftpDownload(String srcFilePath, String desFilePath) {
		boolean status = false;
		try {
			FTPFile[] files = mftp.listFiles();
			for(FTPFile file : files){
				file.getName();
			}
			for(int i = 0; i < files.length; i++){
				status = mftp.retrieveFile(files[i].getName(), new FileOutputStream(desFilePath + files[i].getName()));
			}
			

			return status;
		} catch (Exception e) {
			Log.d("TAG", "download failed");
		}

		return status;
	}
	
	public int getFileNum(String path){
		ftpChangeDirectory(path);
		FTPFile[] files;
		try {
			files = mftp.listFiles();
			return files.length;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
/*
	public boolean ftpUpload(String srcFilePath, String desFileName,
			String desDirectory) {
		boolean status = false;
		try {
			File file = new File(srcFilePath);
			FileInputStream srcFileStream = new FileInputStream(file);

			// change working directory to the destination directory
			if (ftpChangeDirectory(desDirectory)) {
				status = mftp.storeFile(desFileName, srcFileStream);
			}

			srcFileStream.close();
			return status;
		} catch (Exception e) {
			Log.d("TAG", "upload failed");
		}

		return status;
	}*/
	
	public boolean ftpfileUpload(String src){
		File path = new File(src); // ???ε? ?? ?????? ??? ???
		
		boolean up;
		try {
			FileInputStream fis = new FileInputStream(path);

			try {
				String str = path.getName();
				FTPFile[] files = mftp.listFiles();
				for(FTPFile file : files){
					file.getName();
					if(str.equals("profile.jpg")){
						mftp.deleteFile("profile.jpg");
					}
				}
				
				mftp.rest(path.getName());
				//mftp.rnto(path.getName());
				up = mftp.appendFile(path.getName(), fis);
				
				
				return up;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	return false;
	   /* if (path.listFiles().length > 0) { // 
	          for (File file : path.listFiles()) {
	               if (file.isFile()) {
	                    FileInputStream ifile;
						try {
							ifile = new FileInputStream(file);
							
		                    try {
		                    	mftp.rest(file.getName());  
								mftp.appendFile(file.getName(), ifile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

	                   
	               }
	          }
	     }*/

	}
}
