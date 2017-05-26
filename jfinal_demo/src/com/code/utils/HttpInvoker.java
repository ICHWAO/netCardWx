package com.code.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * HTTP调用器<br>
 * 通过getResult获得调用结果
 * 
 * @author mqq
 * 
 */
public class HttpInvoker {

	private String result = null;
	/**
	 * 连接超时设置
	 */
	private int conntionTimeout = -1;
	/**
	 * 读取响应超时设置
	 */
	private int readTimeout = -1;

	public HttpInvoker() {

	}

	/**
	 * 初始化ＨＴＴＰ调用器
	 * 
	 * @param connectionTimeout
	 *            连接超时
	 * @param readTimeout
	 *            读取超时
	 */
	public HttpInvoker(int connectionTimeout, int readTimeout) {
		this.conntionTimeout = connectionTimeout > 0 ? connectionTimeout : -1;
		this.readTimeout = readTimeout > 0 ? readTimeout : -1;
	}

	/**
	 * 获得ＨＴＴＰ调用结果
	 * 
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 调用服务
	 * 
	 * @param url
	 * @param httpmethod
	 * @param params
	 * @return HTTP状态码
	 */
	public int invoke(String url, String httpmethod, String params) throws Exception {
		return this.invoke(url, httpmethod, params, null);
	}

	/**
	 * 执行调用,此方法允许传递多个头参数
	 * 
	 * @param url
	 * @param httpmethod
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public int invokeNew(String url, String httpmethod, String params, Map<String, String> headers) throws Exception {
		HttpURLConnection conn = null;

		URL destURL = new URL(url);
		//if (StringUtil.equal(destURL.getProtocol().toLowerCase(), "https", true)) {
			if(destURL.getProtocol().toLowerCase().equals("https")){
			// 创建HTTPS连接
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			}
		//}
		conn = (HttpURLConnection) destURL.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2)");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conntionTimeout > 0)
			conn.setConnectTimeout(conntionTimeout);
		if (readTimeout > 0)
			conn.setReadTimeout(readTimeout);
		conn.setRequestMethod(httpmethod.toUpperCase());
		// 设置headers
		if (headers != null && headers.size() > 0) {
			Iterator<Entry<String, String>> entries = headers.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, String> entry = entries.next();
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		try {
			conn.connect();
			if ("post".equalsIgnoreCase(httpmethod) && !StringUtil.isNullOrEmpty(params)) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(params.getBytes());
				outputStream.flush();
				outputStream.close();
			}
		} catch (SocketTimeoutException e) {
			// 连接异常，包装成平台异常
			//throw new HttpConnectionException(e);
		} catch (Exception e) {
			throw e;
		}

		try {
			// 等待返回数据
			InputStream rin = conn.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = rin.read(buf)) > 0) {
				bout.write(buf, 0, len);
			}
			rin.close();
			result = new String(bout.toByteArray());
			return conn.getResponseCode();
		} catch (SocketTimeoutException e) {
			//throw new HttpReadException(e);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.disconnect();
			conn = null;
		}
		return conntionTimeout;
	}

	/**
	 * 调用服务
	 * 
	 * @param url
	 * @param httpmethod
	 * @param params
	 * @param contentType
	 * @return HTTP状态码
	 */
	public int invoke(String url, String httpmethod, String params, String contentType) throws Exception {
		HttpURLConnection conn = null;

		URL destURL = new URL(url);
	/*	if (StringUtil.equal(destURL.getProtocol().toLowerCase(), "https", true)) {
			// 创建HTTPS连接
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}*/
		conn = (HttpURLConnection) destURL.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2)");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conntionTimeout > 0)
			conn.setConnectTimeout(conntionTimeout);
		if (readTimeout > 0)
			conn.setReadTimeout(readTimeout);
		conn.setRequestMethod(httpmethod.toUpperCase());
		// 设置contentType
		if (!StringUtil.isNullOrEmpty(contentType)) {
			conn.setRequestProperty("Content-Type", contentType);
		}
		try {
			conn.connect();
			if ("post".equalsIgnoreCase(httpmethod) && !StringUtil.isNullOrEmpty(params)) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(params.getBytes());
				outputStream.flush();
				outputStream.close();
			}
		} catch (SocketTimeoutException e) {
			// 连接异常，包装成平台异常
			//throw new HttpConnectionException(e);
		} catch (Exception e) {
			throw e;
		}

		try {
			// 等待返回数据
			InputStream rin = conn.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = rin.read(buf)) > 0) {
				bout.write(buf, 0, len);
			}
			rin.close();
			result = new String(bout.toByteArray());
			return conn.getResponseCode();
		} catch (SocketTimeoutException e) {
		//	throw new HttpReadException(e);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.disconnect();
			conn = null;
		}
		return 0;
	}

	/**
	 * 设置SSL的参数，信任所有证书
	 * 
	 * @throws Exception
	 */
	private void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new FlowTrustManager();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

}

/**
 * TrustManager Implement
 * 
 * @author Administrator
 *
 */
class FlowTrustManager implements TrustManager, X509TrustManager {
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
		return true;
	}

	public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
		return true;
	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			throws java.security.cert.CertificateException {
		return;
	}

	public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			throws java.security.cert.CertificateException {
		return;
	}
}
