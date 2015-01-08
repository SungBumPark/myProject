package myhanbok.activity;


import myhanbok.server.cFTPClient;

public class commandFTPmgr {
	private static commandFTPmgr m_Instance = null;
	private static final int UPLOAD = 0;
	private static final int CHANGEDIRECTORY = 1;
	private static cFTPClient m_FtpClient = null;
	private static String m_DirectoryPath = null;
	private static String m_FilePath = null;
	public static commandFTPmgr GetInstance(){
		if(m_Instance == null){
			m_Instance = new commandFTPmgr();
			m_FtpClient = new cFTPClient();
			return m_Instance;
		}
		return m_Instance;
	}
	
	public void setDirectoryPath(String path){
		m_DirectoryPath = path;
	}
	
	public void setFilePath(String path){
		m_FilePath = path;
	}
	
	
	public void commandFTP(int commandnum){
		Thread thread = new Thread(new workingThread(commandnum));
		thread.start();
	}
	
	
	class workingThread extends Thread{
		int commandnum;

		
		workingThread(int commandnum){
			this.commandnum = commandnum;
		}
		
		public void run(){
			if(!(m_FtpClient.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)))
				return;
			switch(commandnum){
			case UPLOAD:
				boolean a = m_FtpClient.ftpChangeDirectory(m_DirectoryPath);
				boolean b = m_FtpClient.ftpfileUpload(m_FilePath);
				break;
				
			case CHANGEDIRECTORY:
				m_FtpClient.ftpChangeDirectory(m_DirectoryPath);
				break;
			}
			
			m_FtpClient.ftpDisconnect();
		}
	}
	
	
	
	
}
