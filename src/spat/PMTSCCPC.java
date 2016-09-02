package spat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;

public class PMTSCCPC extends Thread implements Data{
	int count = 0; // SQL����
	String msgblock = new String(); // ��־��
	String filepath = new String(); // ��־Ŀ¼
	String filter = new String(); // ��־�ؼ���
	Connection dbconn; // ���ݿ����ӳ�
	PreparedStatement ps; // Ԥ����SQL
	JTextArea textArea = null; // �����
	PMTSCCPCMAP pmtsccpcmap = new PMTSCCPCMAP(); // ��־�������

	class PMTSCCPCMAP {
		String msgtype;// ��������
		String pid;// ���̺�
		String msgdirection; // ���Ĵ��䷽��
		String msgsendtime; // ���ĵķ���ʱ��
		String premsgsendtime; // ǰһ�ڵ㷢��ʱ��
		String pmtsurecvtime; // NPC��¼�Ľ���ʱ��(Uͷ)
		long msgfwtime; // NPC����ת��ʱ��
		long msgdealtime; // NPCӦ�ô���ʱ��
	}

	// ��ʼ������
	public PMTSCCPC(Connection dbconn, String filepath, String filter, JTextArea textArea) {
		this.dbconn = dbconn;
		this.filepath = filepath;
		this.filter = filter;
		this.textArea = textArea;
	}

	// ��ʽ�����Ȳ���΢���ʱ�䴮
	public static String formatTime(String time) {
		int len = time.length();
		for (int i = 0; i < 26 - len; i++) {
			time = time + '0';
		}
		return time;
	}

	// ����2��ʱ��Ĳ�ֵ
	public static long compTimeDiff(String starttime, String endtime) {
		if (starttime.length() <= 0 || endtime.length() <= 0) {
			return 0;
		}
		starttime = formatTime(starttime);
		endtime = formatTime(endtime);

		long diff = -1;
		long time = -1;
		try {
			time = new Long(endtime.substring(23, 26)) - new Long(starttime.substring(23, 26));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			Date begin = df.parse(starttime.substring(0, 23));
			Date end = df.parse(endtime.substring(0, 23));
			diff = end.getTime() - begin.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff * 1000 + time;
	}

	// ɨ��ָ��Ŀ¼�µ������ļ�
	public static void getFiles(ArrayList<String> filelist, ArrayList<String> subdirlist, String filePath,
			String fileKey) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				getFiles(filelist, subdirlist, file.getAbsolutePath(), fileKey);
				subdirlist.add(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1));
			} else {
				String strFileName = file.getAbsolutePath();
				if (strFileName.indexOf(fileKey) >= 0)
					filelist.add(strFileName);
			}
		}
	}

	// ��ȡ���̺�
	public void getPid() {
		int pos1 = msgblock.indexOf("[", 2);
		int pos2 = msgblock.indexOf("]", pos1);
		if (pos1 != -1 && pos2 != -1) {
			pmtsccpcmap.pid = msgblock.substring(pos1 + 1, pos2);
		} else {
			pmtsccpcmap.pid = "";
		}
	}

	// ��ȡ���ķ���ʱ��
	public void getMsgSendTime() {
		int pos = msgblock.indexOf("[");
		if (pos != -1) {
			pmtsccpcmap.msgsendtime = msgblock.substring(pos + 1, pos + 27);
		} else {
			pmtsccpcmap.msgsendtime = "";
		}
	}

	// ��ȡǰһ�ڵ㷢��ʱ��
	public void getPreMsgSendTime() {
		int pos = msgblock.indexOf(PMTSPRENODETIMEFLAG);
		int pos1 = msgblock.indexOf("[", pos);
		int pos2 = msgblock.indexOf("]", pos1);
		if (pos != -1 && pos1 != -1 && pos2 != -1) {
			pmtsccpcmap.premsgsendtime = msgblock.substring(pos1 + 1, pos2);
		} else {
			pmtsccpcmap.premsgsendtime = "";
		}
	}

	// ��ȡ�������ͺͱ��Ĵ��䷽��
	public void getMsgType() {
		try {
			int pos = -1;
			if ((pos = msgblock.indexOf(IBPSMSGHEAD)) != -1) {
				pmtsccpcmap.msgtype = msgblock.substring(pos + 54, pos + 74);
				pmtsccpcmap.msgdirection = msgblock.substring(pos + 115, pos + 116);
			} else if ((pos = msgblock.indexOf(CNAPSMSGHEAD)) != 1) {
				pmtsccpcmap.msgtype = msgblock.substring(pos + 58, pos + 78);
				pmtsccpcmap.msgdirection = msgblock.substring(pos + 119, pos + 120);
			} else {
				throw new Exception("����ͷ������������ͷ���������ͷ��\n");
			}
			pmtsccpcmap.msgtype = pmtsccpcmap.msgtype.trim();

		} catch (Exception e) {
			textArea.append(e.getMessage() + "\n");
		}

	}

	public void getPmtsURecvTime() {
		int pos = -1;
		if ((pos = msgblock.indexOf(PMTSUHEADFLAG)) != -1 && pmtsccpcmap.msgdirection.equals("D")) {
			pmtsccpcmap.pmtsurecvtime = msgblock.substring(pos + 288, pos + 288 + 23);
			pmtsccpcmap.pmtsurecvtime = pmtsccpcmap.msgsendtime.substring(0, 8)
					+ pmtsccpcmap.pmtsurecvtime.trim().replace('T', ' ');
		} else {
			pmtsccpcmap.pmtsurecvtime = "";
		}
	}

	public void getMsgDealTime() {
		if (pmtsccpcmap.msgdirection.equals("D")) {
			pmtsccpcmap.msgfwtime = compTimeDiff(pmtsccpcmap.premsgsendtime, pmtsccpcmap.msgsendtime);
			pmtsccpcmap.msgdealtime = 0;
		} else {
			pmtsccpcmap.msgdealtime = compTimeDiff(pmtsccpcmap.pmtsurecvtime, pmtsccpcmap.msgsendtime);
			pmtsccpcmap.msgfwtime = 0;
		}
	}

	// ��ʼ����
	public static void initPMTSCCPCTab(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS TABPMTSCCPC");
			stmt.executeUpdate(
					"CREATE TABLE TABPMTSCCPC(MSGTYPE CHAR(15),PID CHAR(8),MSGDIRECTION CHAR(1),MSGSENDTIME CHAR(26),PREMSGSENDTIME CHAR(26),PMTSURECVTIME CHAR(26),MSGDEALTIME INT,MSGFWTIME INT);");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ����������ݿ�
	public void insertDataBase() {
		try {
			if (count == 0) {
				String sql = "INSERT INTO TABPMTSCCPC VALUES(?,?,?,?,?,?,?,?)";
				ps = dbconn.prepareStatement(sql);
			}
			ps.setString(1, pmtsccpcmap.msgtype);
			ps.setString(2, pmtsccpcmap.pid);
			ps.setString(3, pmtsccpcmap.msgdirection);
			ps.setString(4, pmtsccpcmap.msgsendtime);
			ps.setString(5, pmtsccpcmap.premsgsendtime);
			ps.setString(6, pmtsccpcmap.pmtsurecvtime);
			ps.setLong(7, pmtsccpcmap.msgdealtime);
			ps.setLong(8, pmtsccpcmap.msgfwtime);
			ps.addBatch();
			++count;
			if (count == 1000) {
				ps.executeBatch();
				ps.close();
				count = 0;
			}
		} catch (SQLException e) {
			textArea.append("���ݲ������ݿ�ʧ�ܣ�\n\n");
		}
	}

	// ������־��
	public void parseMCTL() {
		getPid();
		getMsgSendTime();
		getPreMsgSendTime();
		getMsgType();
		getPmtsURecvTime();
		if ((pmtsccpcmap.premsgsendtime.isEmpty() && pmtsccpcmap.msgdirection.equals("D"))
				|| (pmtsccpcmap.pmtsurecvtime.isEmpty() && pmtsccpcmap.msgdirection.equals("U"))) {
			return;
		}
		getMsgDealTime();
		insertDataBase();
	}

	// ������
	public void runMainMethod() {
		ArrayList<String> filelist = new ArrayList<String>();
		ArrayList<String> sdirlist = new ArrayList<String>();
		getFiles(filelist, sdirlist, filepath, filter);
		initPMTSCCPCTab(dbconn);
		try {
			boolean sign = false;
			String line = new String();
			for (String path : filelist) {
				File f = new File(path);
				FileInputStream fi = new FileInputStream(f);
				InputStreamReader in = new InputStreamReader(fi, "UTF-8");
				BufferedReader br = new BufferedReader(in);
				while ((line = br.readLine()) != null) {
					if (sign) {
						if (line.indexOf(MSGBODYFINSHEDFLAG) != -1) {
							msgblock = msgblock + line;
							parseMCTL();
							sign = false;
						} else if (line.indexOf(PMTSMSGSTARTFLAG) != -1) {
							msgblock = line;
						} else {
							msgblock = msgblock + line;
						}
					} else {
						if (line.indexOf(PMTSMSGSTARTFLAG) != -1) {
							sign = true;
							msgblock = line;
						}
					}
				}
				br.close();
				in.close();
				fi.close();
			}
			if (count != 0) {
				ps.executeBatch();
				ps.close();
				count = 0;
			}
		} catch (SQLException e) {
			textArea.append("���ݿ�����ʧ�ܣ�\n\n");
		} catch (FileNotFoundException e) {
			textArea.append("�ļ�δ�ҵ���\n\n");
		} catch (IOException e) {
			textArea.append("�ļ���ȡ����\n\n");
		} catch (Exception e) {
			textArea.append("��������\n\n");
			e.printStackTrace();
		}
	}
}