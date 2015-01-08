package com.example.travelmaker.gpsinfo.network;

import java.util.*;

import com.example.travelmaker.info.data.*;

public interface NetworkListener {

    /**
     * 네트워크로부터 받은 데이터를 전달하는 함수
     * 
     * @param result
     *            - 네트워크로부터 받은 데이터
     */
    public void contentDownloadComplete(Map<String, Object> result);
    public void contentDownloadComplete(COMNTourData result);
    public void contentDownloadComplete(ArrayList<String> result);
    

}
