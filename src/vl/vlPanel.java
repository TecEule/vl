package vl;

import javax.swing.JPanel;

import ps.ParameterBase;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import javax.swing.SwingConstants;

public class vlPanel extends JPanel {

	JLabel lblObenLinks;
	JLabel lblObenRechts;
	JLabel lblUntenLinks;
	JLabel lblUntenRechts;

	private static String HereAppID = "";
	private static String HereAppCode = "";

	/**
	 * Create the panel.
	 */
	public vlPanel() {
		setLayout(null);

		lblObenLinks = new JLabel("New label");
		lblObenLinks.setBounds(10, 53, 151, 143);
		add(lblObenLinks);

		lblObenRechts = new JLabel("New label");
		lblObenRechts.setBounds(161, 53, 151, 143);
		add(lblObenRechts);

		lblUntenLinks = new JLabel("New label");
		lblUntenLinks.setBounds(10, 196, 151, 143);
		add(lblUntenLinks);

		lblUntenRechts = new JLabel("New label");
		lblUntenRechts.setBounds(161, 196, 151, 143);
		add(lblUntenRechts);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.WHITE));
		panel.setBounds(10, 11, 302, 37);
		add(panel);
		
		JLabel lblNewLabel = new JLabel("Verkehrslage");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblNewLabel);

		readHereAppKeys();

		setUrls();
	}
	
	String mapTileUR, mapTileUL, mapTileOL, mapTileOR;
	String trafficUR, trafficUL, trafficOL, trafficOR;
	private void setUrls()
	{
		mapTileUR = "https://1.base.maps.api.here.com/maptile/2.1/maptile/newest/normal.day/10/536/338/256/png8?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		mapTileUL = "https://1.base.maps.api.here.com/maptile/2.1/maptile/newest/normal.day/10/535/338/256/png8?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		mapTileOL = "https://1.base.maps.api.here.com/maptile/2.1/maptile/newest/normal.day/10/535/337/256/png8?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		mapTileOR = "https://1.base.maps.api.here.com/maptile/2.1/maptile/newest/normal.day/10/536/337/256/png8?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		
		trafficUR = "https://tiles.traffic.api.here.com/traffic/6.0/tiles/10/536/338/256/png32?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		trafficUL = "https://tiles.traffic.api.here.com/traffic/6.0/tiles/10/535/338/256/png32?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		trafficOL = "https://tiles.traffic.api.here.com/traffic/6.0/tiles/10/535/337/256/png32?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		trafficOR = "https://tiles.traffic.api.here.com/traffic/6.0/tiles/10/536/337/256/png32?app_id="
				+ HereAppID + "&app_code=" + HereAppCode;
		
	}

	private static void readHereAppKeys() {

		HereAppID = ParameterBase.getParameterValue("DeveloperKey", "HereAppID");
		HereAppCode = ParameterBase.getParameterValue("DeveloperKey", "HereAppCode");
	}
	
	private void setMapTrafficUR()
	{
		try
		{
		Image imgMap = null;
		Image imgTraffic = null;
					
		URL url_mapTile = new URL(mapTileUR);

		HttpURLConnection conn = (HttpURLConnection) url_mapTile.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		int responseCode = conn.getResponseCode();
					
		if (responseCode == HttpURLConnection.HTTP_OK) {
			imgMap = ImageIO.read(url_mapTile);
			ImageIcon icon = new ImageIcon(imgMap.getScaledInstance(lblUntenRechts.getWidth(),
					lblUntenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		}

		conn = null;
		url_mapTile = null;

		URL urlTraffic;
		urlTraffic = new URL(trafficUR);
		conn = (HttpURLConnection) urlTraffic.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);

		responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {

			imgTraffic = ImageIO.read(urlTraffic);
			ImageIcon icon = new ImageIcon(imgTraffic.getScaledInstance(lblUntenRechts.getWidth(),
					lblUntenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		}
		
		BufferedImage bufferMap = (BufferedImage) imgMap;
		BufferedImage bufferTraffic = (BufferedImage) imgTraffic;
		BufferedImage bufferImg = joinBufferedImage(bufferMap, bufferTraffic);
		
		Image joinImage = bufferImg;
		ImageIcon joinIcon = new ImageIcon(joinImage.getScaledInstance(lblUntenRechts.getWidth(),
					lblUntenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		
		
		lblUntenRechts.setIcon(joinIcon);
		lblUntenRechts.setText("");
		
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	private void setMapTrafficUL()
	{
		try
		{
		Image imgMap = null;
		Image imgTraffic = null;
					
		URL url_mapTile = new URL(mapTileUL);

		HttpURLConnection conn = (HttpURLConnection) url_mapTile.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		int responseCode = conn.getResponseCode();
					
		if (responseCode == HttpURLConnection.HTTP_OK) {
			imgMap = ImageIO.read(url_mapTile);
			ImageIcon icon = new ImageIcon(imgMap.getScaledInstance(lblUntenLinks.getWidth(),
					lblUntenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		}

		conn = null;
		url_mapTile = null;

		URL urlTraffic;
		urlTraffic = new URL(trafficUL);
		conn = (HttpURLConnection) urlTraffic.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);

		responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {

			imgTraffic = ImageIO.read(urlTraffic);
			ImageIcon icon = new ImageIcon(imgTraffic.getScaledInstance(lblUntenLinks.getWidth(),
					lblUntenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		}
		
		BufferedImage bufferMap = (BufferedImage) imgMap;
		BufferedImage bufferTraffic = (BufferedImage) imgTraffic;
		BufferedImage bufferImg = joinBufferedImage(bufferMap, bufferTraffic);
		
		Image joinImage = bufferImg;
		ImageIcon joinIcon = new ImageIcon(joinImage.getScaledInstance(lblUntenLinks.getWidth(),
				lblUntenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		
		
		lblUntenLinks.setIcon(joinIcon);
		lblUntenLinks.setText("");
		
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	private void setMapTrafficOR()
	{
		try
		{
		Image imgMap = null;
		Image imgTraffic = null;
					
		URL url_mapTile = new URL(mapTileOR);

		HttpURLConnection conn = (HttpURLConnection) url_mapTile.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		int responseCode = conn.getResponseCode();
					
		if (responseCode == HttpURLConnection.HTTP_OK) {
			imgMap = ImageIO.read(url_mapTile);
			ImageIcon icon = new ImageIcon(imgMap.getScaledInstance(lblObenRechts.getWidth(),
					lblObenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		}

		conn = null;
		url_mapTile = null;

		URL urlTraffic;
		urlTraffic = new URL(trafficOR);
		conn = (HttpURLConnection) urlTraffic.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);

		responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {

			imgTraffic = ImageIO.read(urlTraffic);
			ImageIcon icon = new ImageIcon(imgTraffic.getScaledInstance(lblObenRechts.getWidth(),
					lblObenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		}
		
		BufferedImage bufferMap = (BufferedImage) imgMap;
		BufferedImage bufferTraffic = (BufferedImage) imgTraffic;
		BufferedImage bufferImg = joinBufferedImage(bufferMap, bufferTraffic);
		
		Image joinImage = bufferImg;
		ImageIcon joinIcon = new ImageIcon(joinImage.getScaledInstance(lblObenRechts.getWidth(),
				lblObenRechts.getHeight(), Image.SCALE_AREA_AVERAGING));
		
		
		lblObenRechts.setIcon(joinIcon);
		lblObenRechts.setText("");
		
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	private void setMapTrafficOL()
	{
		try
		{
		Image imgMap = null;
		Image imgTraffic = null;
					
		URL url_mapTile = new URL(mapTileOL);

		HttpURLConnection conn = (HttpURLConnection) url_mapTile.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		int responseCode = conn.getResponseCode();
					
		if (responseCode == HttpURLConnection.HTTP_OK) {
			imgMap = ImageIO.read(url_mapTile);
			ImageIcon icon = new ImageIcon(imgMap.getScaledInstance(lblObenLinks.getWidth(),
					lblObenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		}

		conn = null;
		url_mapTile = null;

		URL urlTraffic;
		urlTraffic = new URL(trafficOL);
		conn = (HttpURLConnection) urlTraffic.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setDoOutput(true);

		responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {

			imgTraffic = ImageIO.read(urlTraffic);
			ImageIcon icon = new ImageIcon(imgTraffic.getScaledInstance(lblObenLinks.getWidth(),
					lblObenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		}
		
		BufferedImage bufferMap = (BufferedImage) imgMap;
		BufferedImage bufferTraffic = (BufferedImage) imgTraffic;
		BufferedImage bufferImg = joinBufferedImage(bufferMap, bufferTraffic);
		
		Image joinImage = bufferImg;
		ImageIcon joinIcon = new ImageIcon(joinImage.getScaledInstance(lblObenLinks.getWidth(),
				lblObenLinks.getHeight(), Image.SCALE_AREA_AVERAGING));
		
		
		lblObenLinks.setIcon(joinIcon);
		lblObenLinks.setText("");
		
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}


	public void setBilder() {

		try {

			setMapTrafficUR();
			setMapTrafficUL();
			setMapTrafficOR();
			setMapTrafficOL();
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private BufferedImage joinBufferedImage(BufferedImage imgMap, BufferedImage imgTraffic) {

		// do some calculate first

		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(imgMap.getWidth(), imgMap.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
//		Color oldColor = g2.getColor();
		// fill background
		//g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, imgMap.getWidth(), imgMap.getWidth());
		// draw image
//		g2.setColor(oldColor);
		
		
		g2.drawImage(imgMap, null, 0, 0);
		g2.drawImage(imgTraffic, null, 0, 0);
		
		g2.dispose();
		return newImage;
	}

	private BufferedImage getImage(BufferedImage img2) {

	    int width = img2.getWidth();
	    int height = img2.getHeight();

	    // Create a buffered image in which to draw
	    BufferedImage bufferedImage = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_ARGB);

	    // Create a graphics contents on the buffered image
	    Graphics2D g2d = bufferedImage.createGraphics();

	    // Draw graphics
	    //g2d.setColor(Color.WHITE);
//	    g2d.setComposite(AlphaComposite.Clear);
	    g2d.fillRect(0, 0, width, height);
	    g2d.drawImage(img2, null,0,0);

	    // Graphics context no longer needed so dispose it
	    g2d.dispose();

	    return bufferedImage;
	}
}
