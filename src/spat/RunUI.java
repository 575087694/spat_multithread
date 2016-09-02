package spat;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class RunUI implements Data{
	private static int winWidth = 900;
	private static int winHeight = 720;
	final static JTextArea textArea = new JTextArea();
	static Connection dbconn = null;

	
	public RunUI() {
		JFrame frame = new JFrame("����ϵͳ��־��������");
		JPanel panelContainer = new JPanel();
		panelContainer.setLayout(new GridLayout(0, 1));

		// �˵���
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		JMenu hitsMenu = new JMenu("����");
		JMenuItem item = new JMenuItem("ʹ��˵��");
		hitsMenu.add(item);
		menuBar.add(hitsMenu);
		hitsMenu.setToolTipText("�鿴ʹ��˵��");
		hitsMenu.setMnemonic(KeyEvent.VK_H);
		frame.setJMenuBar(menuBar);

		JPanel panelTop = new JPanel();
		panelTop.setLayout(new GridLayout(0, 1));
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new GridLayout(0, 1));
		panelContainer.add(panelTop);
		panelContainer.add(panelBottom);

		// ���������
		JPanel typePanel = new JPanel();
		typePanel.setLayout(null);
		typePanel.setBorder(BorderFactory.createTitledBorder("��־��������"));
		typePanel.setSize(winWidth, winHeight / 6);
		typePanel.setBounds(0, 0, winWidth - 20, winHeight / 7);
		final JRadioButton[] typeButton = new JRadioButton[3];
		typeButton[0] = new JRadioButton("MCTL��־", true);
		typeButton[1] = new JRadioButton("PMTSNPC��־");
		typeButton[2] = new JRadioButton("PMTSCCPC��־");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(typeButton[0]);
		buttonGroup.add(typeButton[1]);
		buttonGroup.add(typeButton[2]);
		buttonGroup.setSelected(null, true);
		typeButton[0].setFont(new Font(null, Font.ITALIC, 12));
		typeButton[1].setFont(new Font(null, Font.ITALIC, 12));
		typeButton[2].setFont(new Font(null, Font.ITALIC, 12));
		typeButton[0].setBounds(10, 25, 120, winHeight / 24);
		typeButton[1].setBounds(140, 25, 120, winHeight / 24);
		typeButton[2].setBounds(270, 25, 120, winHeight / 24);
		typePanel.add(typeButton[0]);
		typePanel.add(typeButton[1]);
		typePanel.add(typeButton[2]);
		panelTop.add(typePanel);

		// ���ݿ�������
		JPanel databasePanel = new JPanel();
		databasePanel.setLayout(null);
		databasePanel.setBorder(BorderFactory.createTitledBorder("���ݿⱣ�淽ʽ"));
		databasePanel.setSize(winWidth, winHeight / 6);
		databasePanel.setBounds(0, 0, winWidth - 20, winHeight / 7);
		final JRadioButton[] dataButton = new JRadioButton[3];
		dataButton[0] = new JRadioButton("MEMORY", true);
		dataButton[1] = new JRadioButton("DISK");
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(dataButton[0]);
		buttonGroup2.add(dataButton[1]);
		buttonGroup2.setSelected(null, true);
		dataButton[0].setFont(new Font(null, Font.ITALIC, 12));
		dataButton[1].setFont(new Font(null, Font.ITALIC, 12));
		dataButton[0].setBounds(10, 30, 90, winHeight / 24);
		dataButton[1].setBounds(140, 30, 90, winHeight / 24);
		JPanel publicPanel = new JPanel();
		publicPanel.setLayout(null);
		publicPanel.setSize(winWidth - 240, winHeight / 12);
		publicPanel.setBounds(200, 15, winWidth - 240, winHeight / 12);
		JLabel dbLabel0 = new JLabel("���ݿ���:");
		dbLabel0.setBounds(35, 0, 60, winHeight / 12);
		JTextField dbText0 = new JTextField("");
		dbText0.setBounds(95, 10, 60, winHeight / 20);
		JLabel dbLabel = new JLabel("�������ݿ�Ŀ¼:");
		dbLabel.setBounds(200, 0, 90, winHeight / 12);
		JTextField dbText1 = new JTextField("");
		dbText1.setBounds(300, 10, 115, winHeight / 20);
		JButton dbButton = new JButton("...");
		dbButton.setBounds(425, 10, 50, winHeight / 20);
		JButton confButton = new JButton("�������ݿ�");
		confButton.setBounds(525, 10, 100, winHeight / 20);
		publicPanel.add(dbLabel0);
		publicPanel.add(dbText0);
		publicPanel.add(dbLabel);
		publicPanel.add(dbText1);
		publicPanel.add(dbButton);
		publicPanel.add(confButton);
		databasePanel.add(dataButton[0]);
		databasePanel.add(dataButton[1]);
		databasePanel.add(publicPanel);
		publicPanel.setVisible(false);
		panelTop.add(databasePanel);
		panelTop.add(databasePanel);

		// ѡ��Ŀ¼��
		JPanel timePanel = new JPanel();
		timePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("��־����"));
		timePanel.setBounds(0, winHeight / 6 + 5, winWidth - 20, winHeight / 6);//
		timePanel.setLayout(null);
		JLabel dirLabel = new JLabel("��־Ŀ¼:");
		dirLabel.setBounds(15, 15, winWidth / 3 + 40, winHeight / 12);
		JTextField dirText = new JTextField("");
		dirText.setBounds(75, 25, winWidth / 3, winHeight / 20);
		JButton dirButton = new JButton("...");
		dirButton.setBounds(385, 25, 50, winHeight / 20);
		JLabel filekeyLabel = new JLabel("�ļ��ؼ���:");
		filekeyLabel.setBounds(465, 15, winWidth / 3 + 40, winHeight / 12);
		JComboBox<String> filekeyBox = new JComboBox<String>();
		filekeyBox.setEditable(true);
		filekeyBox.setBounds(535, 25, 150, winHeight / 20);
		changeView(filekeyBox, MCTLFILTER);
		JButton startButton = new JButton("��������");
		startButton.setBounds(725, 25, 100, winHeight / 20);
		timePanel.add(dirLabel);
		timePanel.add(dirText);
		timePanel.add(dirButton);
		timePanel.add(startButton);
		timePanel.add(filekeyBox);
		timePanel.add(filekeyLabel);
		panelTop.add(timePanel);

		// SQL������
		JPanel sqlPanel = new JPanel();
		sqlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("SQL����"));
		sqlPanel.setLayout(null);
		sqlPanel.setBounds(0, winHeight / 6 + 5, winWidth - 20, winHeight / 6);
		JLabel sqlLabel = new JLabel("SQL���:");
		sqlLabel.setBounds(15, 25, 100, winHeight / 20);
		JComboBox<String> sqlText = new JComboBox<String>();
		sqlText.setEditable(true);
		changeView(sqlText, MCTLSQL);
		sqlText.setBounds(70, 25, 615, winHeight / 20);
		JButton sqlButton = new JButton("ִ��SQL");
		sqlButton.setBounds(725, 25, 100, winHeight / 20);
		timePanel.add(sqlButton);
		sqlPanel.add(sqlLabel);
		sqlPanel.add(sqlText);
		sqlPanel.add(sqlButton);
		panelTop.add(sqlPanel);

		// ���������
		JPanel ioPanel = new JPanel();
		ioPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("�������"));
		ioPanel.setBounds(0, 2 * (winHeight / 6) + 85, winWidth - 20, winHeight / 3 + 10);//
		ioPanel.setLayout(new GridLayout(1, 1));
		textArea.setBounds(0, 5, winWidth - 20, winHeight / 3 - 20);
		ioPanel.add(textArea);
		JScrollPane scroll = new JScrollPane(textArea);
		// �ֱ�����ˮƽ�ʹ�ֱ���������ǳ���
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ioPanel.add(scroll);
		panelBottom.add(ioPanel);

		// ѡ�񱾵����ݿ��ļ���ť
		dbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser(new File("./"));// ����Ĭ�ϴ򿪵�ǰ·��
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// ����ѡ��Ŀ¼���ļ�
				int flag = fc.showOpenDialog(null);
				if (flag == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					String filepath = f.getPath();
					if (f.isDirectory()) {
						dbText1.setText(filepath);
					} else if (f.isFile()) {
						int pos = filepath.lastIndexOf("\\");
						if (-1 != pos) {
							String path = filepath.substring(0, pos);
							String filename = filepath.substring(pos + 1);
							int pos2 = filename.indexOf(".mv.db");
							if (-1 != pos2) {
								String dbname = filename.substring(0, pos2);
								dbText0.setText(dbname);
								dbText1.setText(path);
							}
						}
					} else {
						textArea.append("δ֪����\n");
					}
				}
			}
		});

		// ��־����ѡ��ť
		typeButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changeView(filekeyBox, MCTLFILTER);
				changeView(sqlText, MCTLSQL);
			}
		});
		typeButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changeView(filekeyBox, PMTSNPCFILTER);
				changeView(sqlText, PMTSNPCSQL);
			}
		});
		typeButton[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changeView(filekeyBox, PMTSCCPCFILTER);
				changeView(sqlText, PMTSCCPCSQL);
			}
		});

		// ���ݿⱣ�淽ʽ��ť(�����ʾ���ص�ѡ��)
		dataButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				publicPanel.setVisible(false);
			}
		});
		dataButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				publicPanel.setVisible(true);
			}
		});

		// ѡ����־Ŀ¼��ť
		dirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser(new File("./"));// ����Ĭ�ϴ򿪵�ǰ·��
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// ֻ��ѡ��Ŀ¼
				int flag = fc.showOpenDialog(null);
				if (flag == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					String path = f.getPath();
					dirText.setText(path);
				}
			}
		});

		// ȷ����ť
		confButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dbconn = createConnection(dbText0.getText(), dataButton[0].isSelected(), dbText1.getText());
			}
		});

		// ������־��ť
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				long aa = System.currentTimeMillis();
				dbconn = createConnection(dbText0.getText(), dataButton[0].isSelected(), dbText1.getText());
				String filepath = dirText.getText();
				String filter = filekeyBox.getSelectedItem().toString();
				ArrayList<String> filelist = new ArrayList<String>();
				ArrayList<String> sdirlist = new ArrayList<String>();
				getFiles(filelist, sdirlist, filepath, filter);
				if (typeButton[0].isSelected()) {
					MCTL.initMCTLTab(dbconn);
				}
				int initthreads = 4;
				Thread[] ts = new Thread[initthreads];
				for (int p = 0; p < initthreads; p++) {
					String logfile = new String();
					for (int i = 0; i < filelist.size(); i++) {
						if (p == i % initthreads) {
							logfile = logfile + filelist.get(i) + ",";
						}
					}
					if (typeButton[0].isSelected()) {
						ts[p] = new MCTL(dbconn, logfile, textArea);
					}
					ts[p].start();
				}

				int alive = initthreads;
				while (alive > 0) {
					alive = checkThreads(ts);
					try {
						Thread.sleep(50L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				long bb = System.currentTimeMillis();
				System.out.println(bb - aa);
				textArea.append("��־�������\n");
			}
		});

		// ִ��sql��䰴ť
		sqlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String sql = sqlText.getSelectedItem().toString();
					textArea.append(sql + "\n");
					if (sql.indexOf("##") != -1) {
						sql = sql.substring(sql.indexOf("##") + 2, sql.length());
					}
					Statement stmt = dbconn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					while (rs.next()) {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							textArea.append(rsmd.getColumnName(i) + ":" + rs.getString(rsmd.getColumnName(i)) + "    ");
						}
						textArea.append("\n");
					}
					textArea.append("\n");
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					textArea.append("��ѯ������!\n\n");
					e.printStackTrace();
				}
			}
		});

		// ������Ϣ
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				textArea.setText(HELPINFO);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(winWidth, winHeight));
		frame.setContentPane(panelContainer);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ���ô����������Ͼ���
	}

	// ��ʾsql���
	public static void changeView(JComboBox<String> combobox, String[] list) {
		combobox.removeAllItems();
		for (int i = 0; i < list.length; i++) {
			combobox.addItem(list[i]);
		}
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

	// �������ݿ�����
	public static Connection createConnection(String dbname, boolean type, String path) {
		Connection conn = null;
		String JDBC_URL = "";
		String USER = "mclogger";
		String PASSWORD = "123456";
		String DRIVER_CLASS = "org.h2.Driver";
		try {
			if (dbname == null || dbname.trim().length() <= 0) {
				dbname = "test";
			}
			if (path == null || path.trim().length() <= 0) {
				path = ".";
			}
			if (type == true) {
				JDBC_URL = "jdbc:h2:mem:" + dbname;
			} else {
				JDBC_URL = "jdbc:h2:file:" + path + "\\" + dbname;
			}
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
		} catch (Exception e) {
			textArea.append("���ݿ�����ʧ�ܣ�\n\n");
		}
		return conn;
	}

	// ����߳��Ƿ�������
	public static int checkThreads(Thread[] ts) {
		int ret = ts.length;
		for (Thread t : ts) {
			if ((!(t.equals(null))) && (!(t.isAlive())))
				--ret;
		}
		return ret;
	}

	// �������
	public static void main(String[] agrs) {
		try {
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new RunUI();
	}
}