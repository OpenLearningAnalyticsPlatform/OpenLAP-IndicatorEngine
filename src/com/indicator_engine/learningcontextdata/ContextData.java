/*
 *
 *  * Copyright (C) 2015  Tanmaya Mahapatra
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.indicator_engine.learningcontextdata;

/**
 * Created by Tanmaya Mahapatra on 22-02-2015.
 */


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.core.task.TaskExecutor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Hendrik Thüs
 *
 */
@SuppressWarnings({"unused", "unchecked"})
public class ContextData {

    private String server;
    private int version;
    private String username;
    private String password;
    private int appID;
    private String appSecret;
    private Listener mGETListener = null;
    private Listener mPOSTListener = null;
    private TaskExecutor taskExecutor; // Added for adding Threading support in Spring Framework

    private String TAG = "LearningContext";

    /**
     * Constructor for the ContextData class
     *
     * @param server
     *            The URL of the server with the API
     * @param version
     *            The version of the API
     * @param username
     *            The name of the user
     * @param password
     *            The password of the user
     * @param appID
     *            The ID of your application, is provided when this app is
     *            registered
     * @param appSecret
     *            The secret string of your application, is provided when this
     *            app is registered
     */
    public ContextData(String server, int version, String username,
                       String password, int appID, String appSecret, TaskExecutor taskExecutor) {
        super();
        if (server.endsWith("/"))
            this.server = server;
        else
            this.server = server + "/";
        this.version = version;
        this.username = username;
        this.password = password;
        this.appID = appID;
        this.appSecret = appSecret;
        this.taskExecutor = taskExecutor;
    }
    public ContextData() {}

    public interface Listener {
        public void onGETResult(String result);

        public void onPOSTResult(String result);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Listener for the result of a GET request
     *
     * @param listener
     */
    public void registerGETListener(Listener listener) {
        mGETListener = listener;
    }

    /**
     * Listener for the result of a POST request
     *
     * @param listener
     */
    public void registerPOSTListener(Listener listener) {
        mPOSTListener = listener;
    }

    /**
     * Sends a POST request to the API
     *
     * @param api
     *            The interface to communicate with, e.g. "events"
     * @param json
     *            The JSON string that should be sent to the interface
     */
    public void post(String api, String json) {
        if (api.startsWith("/"))
            api.replaceFirst("/", "");

        //new PostDataTask(new String[] { api, json }).execute();
        taskExecutor.execute(new PostDataTask(new String[] { api, json }));
    }

    /**
     * Sends a GET request to the API
     *
     * @param api
     *            The interface to communicate with, e.g. "events"
     * @param json
     *            The JSON string that should be sent to the interface
     */
    public void get(String api, String json) {
        if (api.startsWith("/"))
            api.replaceFirst("/", "");

        //new GetDataTask(new String[] { api, json }).execute();
        taskExecutor.execute(new GetDataTask(new String[] { api, json }));
    }

    private ArrayList<NameValuePair> getPostData(String data) {
        ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();

        String nonce = RandomString.randomString(55);
        nvp.add(new BasicNameValuePair("nonce", convertToUTF8(nonce)));
        nvp.add(new BasicNameValuePair("aid", convertToUTF8(String
                .valueOf(appID))));
        nvp.add(new BasicNameValuePair("user", convertToUTF8(username)));
        nvp.add(new BasicNameValuePair("data", convertToUTF8(data)));

        String hash;
        try {
            hash = sha1(urlEncode(data) + appID + urlEncode(username)
                    + urlEncode(nonce) + appSecret + sha1(password));
            nvp.add(new BasicNameValuePair("h", convertToUTF8(hash)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return nvp;
    }

    private String getGetData(String data) {
        String nonce = RandomString.randomString(55);
        String hash = "";
        try {
            hash = sha1(urlEncode(data) + appID + urlEncode(username)
                    + urlEncode(nonce) + appSecret + sha1(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String getString = "?nonce=" + urlEncode(nonce) + "&aid="
                + urlEncode(String.valueOf(appID)) + "&user="
                + urlEncode(username) + "&data=" + urlEncode(data) + "&h="
                + urlEncode(hash);

        return getString;
    }

    private String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    private String urlEncode(String string) {
        try {
            string = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return string;
    }

    private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

    private class GetDataTask  implements Runnable {
        private String[] data;
        public GetDataTask(String[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            String result = process();
            done(result);

        }
        public String process () {

            String params = getGetData(data[1]);

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(server + version + "/" + data[0]
                        + params);
                System.out.println("test " + params);
                HttpResponse response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    BufferedInputStream bis = new BufferedInputStream(instream);
                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                    }
                    String html = new String(baf.toByteArray());
                    System.out.println(html);

                    return html;
                }

            } catch (ClientProtocolException e) {
                System.out.println("There was a protocol based error");
            } catch (IOException e) {
                System.out.println("There was an IO Stream related error");
            }

            return "";

        }

        protected void  done(String result) {
            if (mGETListener != null)
                mGETListener.onGETResult(result);
        }

    }

    private class PostDataTask implements Runnable {
        private String[] data;
        public PostDataTask(String[] data) {
            this.data=data;
        }

        @Override
        public void run() {
            String result = process();
            done(result);

        }
        public String process() {
            ArrayList<NameValuePair> nvp = getPostData(data[1]);

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(server + version + "/"
                        + data[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nvp));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    BufferedInputStream bis = new BufferedInputStream(instream);
                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                    }
                    String html = new String(baf.toByteArray());
                    System.out.println(html);

                    return html;
                }

            } catch (ClientProtocolException e) {
                System.out.println("There was a protocol based error");
            } catch (IOException e) {
                System.out.println("There was an IO Stream related error");
            }
            return "";
        }

        protected void done(String result) {
            if (mPOSTListener != null)
                mPOSTListener.onPOSTResult(result);
        }
    }

}