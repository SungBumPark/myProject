import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandle extends Thread {

	// public static String[] client_name;
	public static ArrayList<String> client_name;
	// client_name.add(" ");
	Socket soc;
	PrintWriter spw;
	BufferedReader sbr;

	// ������
	SocketHandle() {
		client_name = new ArrayList<String>();
	};

	SocketHandle(Socket s) {
		this.soc = s;
		if (client_name == null) {
			client_name = new ArrayList<String>(); //
			client_name.add(" ");
		}
		System.out.println("list �ʱ�ȭ �Ϸ�.....");
		try {
			spw = new PrintWriter(soc.getOutputStream(), true);
			sbr = new BufferedReader(
					new InputStreamReader(soc.getInputStream()));
			System.out.println("spw,sbr �ʱ�ȭ �Ϸ�.....");
			// client_name.add(" ");
		} catch (Exception e) {
		}
	}

	public void run() {
		while (true)// ���� �� ������ ��� ������ ����
		{
			try {
				String information = sbr.readLine();
				if (information.indexOf("_add") != -1) {
					System.out.println("Add List");
					// ���ӽ� add list
					String[] realinfo = information.split("=");
					addList(realinfo[1]);
					// System.out.println("Log "+realinfo[0]+" "+realinfo[1]);
					// showArrayList();
				} else if (information.indexOf("_delete") != -1) {
					// ������ ������ ����� delete
					System.out.println("delete List");
					String[] realinfo = information.split("=");
					delList(realinfo[1]);
				} else if (information.indexOf("_refresh") != -1) {
					System.out.println("refresh List");
					// ������ ������ ����
					refreshList();
				}
				// refreshList();
				showArrayList();
				for (int i = 0; i < client_name.size(); i++) {
					if (!soc.isConnected()) {
						String[] realinfo = information.split("=");
						delList(realinfo[1]);
					}

				}
			} catch (Exception e) {
			}
		}
		// delList(soc.getInetAddress().toString());//������ ����� �����ش�.
		// refreshList();
	}

	void addList(String arg) {

		for (int i = 0; i < client_name.size(); i++) {
			// �ߺ� �˻�
			// System.out.println(arg);
			if (client_name.get(i).matches(arg))
				return;
		}
		client_name.add(arg);
		// client_name.add(arg);

	}

	void delList(String arg) {
		for (int i = 0; i < client_name.size(); i++) {
			if (client_name.get(i).equals(arg)) {
				client_name.remove(i);
			}

		}
	}

	void showArrayList() {
		for (int i = 0; i < client_name.size(); i++) {
			System.out.println(client_name.get(i).toString());
		}
	}

	void refreshList() {
		for (int i = 0; i < client_name.size(); i++) {
			spw.println(client_name.get(i).toString());
		}

	}
}
