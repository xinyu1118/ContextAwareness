package io.github.contextawareness.commons.time;

import android.Manifest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;

class HolidayChecker extends Function<Item, Boolean> {
    private String date;

    HolidayChecker(String date) {
        this.date = date;
        this.addRequiredPermissions(Manifest.permission.INTERNET);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        String request = "http://api.goseek.cn/Tools/holiday?date="+date;
        String holidayInfo = null;
        try {
            holidayInfo = getHtml(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holidayInfo = holidayInfo.replace("\"", "");
        holidayInfo = holidayInfo.replace("}", "");
        holidayInfo = holidayInfo.replace("{", "");
        String[] tempStr = holidayInfo.split(",");
        String[] ttempStr = tempStr[1].split(":");
        Integer rtnValue = Integer.parseInt(ttempStr[1]);

        if (rtnValue.equals(2))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }


    public static String getHtml(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();
        byte[] data = readInputStream(inStream);
        String html = new String(data, "UTF-8");
        return html;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}