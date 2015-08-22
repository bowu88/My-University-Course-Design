package cn.edu.fjnu.gis11.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckCodeImage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String checkcode = creatNumImg();
		request.getSession().setAttribute("checkcode", checkcode);
		
		//����ͷ�������������Ҫ����ͼƬ����
		response.setHeader("Expires","-1");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		
		//֪ͨ�������ͼƬ��ʽ��
		response.setHeader("Content-type", "image/jpeg");
		
		//���ڴ��д���һ��ͼƬ
		BufferedImage image=new BufferedImage(105,20,BufferedImage.TYPE_INT_RGB);
		
		//�õ�ͼƬ
		Graphics2D g=(Graphics2D) image.getGraphics();
		//���ñ���ɫ
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 105, 20);
		
		//��ͼƬ��д����
		g.setColor(Color.BLUE);
		g.setFont(new Font(null,Font.BOLD,20));
		g.drawString(checkcode,0,20);
		
		//��ͼƬд�������
		ImageIO.write(image, "jpeg", response.getOutputStream());
	}

	private String creatNumImg() {
		/*DIY
		int num=(int)(Math.random()*10000000);
		return String.valueOf(num);
		*/
		
		Random r=new Random();
		//�κ�����+�ַ�����Ϊ�ַ���
		String num=r.nextInt(9999999)+"";
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<7-num.length();i++){
			sb.append("0");
		}
		return sb.toString()+num;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
