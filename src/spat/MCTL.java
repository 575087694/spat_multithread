package spat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public class MCTL extends Thread implements Data{
	int count = 0; // SQL计数
	String buff = new String();
	String msgblock = new String(); // 日志块
	String logfile = new String(); // 日志文件名
	Connection dbconn; // 数据库连接池
	PreparedStatement ps; // 预处理SQL
	JTextArea textArea = null; // 输出框
	MCTLMAP mctlmap = new MCTLMAP(); // 日志分析结果

	class MCTLMAP {
		String msgtype;// 报文类型
		String pid;// 进程号
		String mqputtime; // 报文放置时间
		String mqrecvtime; // 报文读取时间
		String msgdealstime; // 处理报文开始时间
		String msgdealetime; // 处理报文结束时间
		long msgmqwaittime; // 业务MQ等待时间
		long msgwaittime; // 业务等待处理时间
		long msgdealtime; // 业务处理时间
		String procerrmesg; // 业务处理描述
	}

	// 初始化参数
	public MCTL(Connection dbconn, String logfile, JTextArea textArea) {
		this.dbconn = dbconn;
		this.logfile = logfile;
		this.textArea = textArea;
	}

	public int checkStr(String org, String str) {
		int ret = 0;
		if (org.equals(str)) {
			ret = 1;
		}
		if (org.indexOf(str) > 1) {
			ret = org.split(str).length;
		}
		return ret;
	}

	// 计算2个时间的差值
	public static long compTimeDiff(String starttime, String endtime) {
		if (starttime.length() <= 0 || endtime.length() <= 0) {
			return 0;
		}
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

	public static String readToString(String fileName, String encoding) {

		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	// 获取报文类型
	public void parseMsg() {
		try {
			int pos1 = msgblock.indexOf(IBPSMSGHEAD);
			if (-1 == pos1) {
				pos1 = msgblock.indexOf(CNAPSMSGHEAD);
				if (-1 == pos1) {
					pos1 = msgblock.indexOf(CNCCMSGHEAD);
				}
			}
			if (-1 == pos1) {
				throw new Exception("报文头不是网银报文头或二代报文头或CNCC报文头！");
			}
			int pos2 = msgblock.indexOf(MCTLPROC);
			if (-1 == pos2) {
				throw new Exception("没有找到报文结尾！,请确认报文格式！");
			}
			String msg = msgblock.substring(pos1, pos2);
			if (-1 != msg.indexOf(IBPSMSGHEAD)) {
				mctlmap.msgtype = msg.substring(54, 74);
			} else if (-1 != msg.indexOf(CNAPSMSGHEAD)) {
				mctlmap.msgtype = msg.substring(58, 78);
			} else if (-1 != msg.indexOf(CNCCMSGHEAD)) {
				mctlmap.msgtype = msg.substring(50, 70);
			} else {
				mctlmap.msgtype = "";
			}
			mctlmap.msgtype = mctlmap.msgtype.trim();
		} catch (Exception e) {
			textArea.append(e.getMessage() + "\n");
		}

	}

	// 获取进程号
	public void getPid() {
		int pos1 = msgblock.indexOf("[", 2);
		int pos2 = msgblock.indexOf("]", pos1);
		if (pos1 != -1 && pos2 != -1) {
			mctlmap.pid = msgblock.substring(pos1 + 1, pos2);
		} else {
			mctlmap.pid = "";
		}
	}

	// 获取报文放置时间
	public void getMqPutTime() {
		int pos = msgblock.indexOf(MCTLMQPUT);
		if (pos != -1) {
			mctlmap.mqputtime = msgblock.substring(pos + 8, pos + 34);
		} else {
			mctlmap.mqputtime = "";
		}
	}

	// 获取报文读取时间
	public void getMqRecvTime() {
		int pos = msgblock.indexOf(MCTLMQRECV);
		if (pos != -1) {
			mctlmap.mqrecvtime = msgblock.substring(pos + 8, pos + 34);
		} else {
			mctlmap.mqrecvtime = "";
		}
	}

	// 获取报文处理开始时间
	public void getMsgDealSTime() {
		int pos = msgblock.indexOf(MCTLMSGDEAL);
		if (pos != -1) {
			mctlmap.msgdealstime = msgblock.substring(pos + 8, pos + 34);
		} else {
			mctlmap.msgdealstime = "";
		}
	}

	// 获取报文处理结束时间
	public void getMsgDealETime() {
		int pos = msgblock.indexOf("[");
		if (pos != -1) {
			mctlmap.msgdealetime = msgblock.substring(pos + 1, pos + 27);
		} else {
			mctlmap.msgdealetime = "";
		}
	}

	// 获取业务处理描述
	public void getProcErrMsg() {
		int pos1 = msgblock.indexOf(MCTLPROC);
		int pos2 = msgblock.indexOf("[", pos1);
		int pos3 = msgblock.indexOf("]", pos1);
		if (pos1 != -1 && pos2 != -1 && pos3 != -1) {
			mctlmap.procerrmesg = msgblock.substring(pos2 + 1, pos3);
		} else {
			mctlmap.procerrmesg = "";
		}
	}

	// 初始化表
	public static void initMCTLTab(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS TABMCTL");
			stmt.executeUpdate(
					"CREATE TABLE TABMCTL(MSGTYPE CHAR(15),PID CHAR(8),MQPUTTIME CHAR(26),MQRECVTIME CHAR(26),MSGDEALSTIME CHAR(26),MSGDEALETIME CHAR(26),MSGMQWAITTIME INT,MSGWAITTIME INT,MSGDEALTIME INT,PROCERRMESG VARCHAR(512));");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 结果插入数据库
	public void insertDataBase() {
		try {
			if (count == 0) {
				String sql = "INSERT INTO TABMCTL VALUES(?,?,?,?,?,?,?,?,?,?)";
				ps = dbconn.prepareStatement(sql);
			}
			ps.setString(1, mctlmap.msgtype);
			ps.setString(2, mctlmap.pid);
			ps.setString(3, mctlmap.mqputtime);
			ps.setString(4, mctlmap.mqrecvtime);
			ps.setString(5, mctlmap.msgdealstime);
			ps.setString(6, mctlmap.msgdealetime);
			ps.setLong(7, mctlmap.msgmqwaittime);
			ps.setLong(8, mctlmap.msgwaittime);
			ps.setLong(9, mctlmap.msgdealtime);
			ps.setString(10, mctlmap.procerrmesg);
			ps.addBatch();
			++count;
			if (count == 1000) {
				ps.executeBatch();
				ps.close();
				count = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			textArea.append("数据插入数据库失败！\n\n");
		}
	}

	// 解析日志块
	public void parseMCTL() {
		parseMsg();
		getPid();
		getMqPutTime();
		getMqRecvTime();
		getMsgDealSTime();
		getMsgDealETime();
		getProcErrMsg();
		mctlmap.msgmqwaittime = compTimeDiff(mctlmap.mqputtime, mctlmap.mqrecvtime);
		mctlmap.msgwaittime = compTimeDiff(mctlmap.mqrecvtime, mctlmap.msgdealstime);
		mctlmap.msgdealtime = compTimeDiff(mctlmap.msgdealstime, mctlmap.msgdealetime);
		insertDataBase();
	}

	// 主方法
	public void run() {
		try {
			String[] spfiles = null;
			if (this.logfile.indexOf(",") > 1) {
				int len = checkStr(this.logfile, ",");
				spfiles = new String[len];
				spfiles = this.logfile.split(",");
			} else {
				spfiles = new String[1];
				spfiles[0] = this.logfile;
			}
			for (int i = 0; i < spfiles.length; ++i) {
				System.out.println(spfiles.length);
			}
			for (int i = 0; i < spfiles.length; ++i) {
				buff = readToString(spfiles[i], "UTF-8");
				int pos1 = -1;
				int pos2 = -1;
				int pos = -1;
				while ((pos = buff.indexOf("\n", pos2)) != -2 && (pos1 = buff.indexOf(MCTLMSGSTART, pos1 + 1)) != -1
						&& (pos2 = buff.indexOf(MCTLMSGEND, pos2 + 1)) != -1) {
					msgblock = buff.substring(pos, pos2);
					parseMCTL();
				}
			}
			if (count != 0) {
				ps.executeBatch();
				ps.close();
				count = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			textArea.append("数据库连接失败！\n\n");
		} catch (Exception e) {
			textArea.append("其他错误！\n\n");
		}
	}

}
