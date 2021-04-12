package org.jeecg.modules.activiti.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Zouzp
 * @create: 2021/3/10 17:53
 **/

@Component
public class TcpClient {
    /*Java变量的初始化顺序为：静态变量或静态语句块–>实例变量或初始化语句块–>构造方法–>@Autowired*/
    @Autowired
    private Constant constant;

    private static Constant con;

    @PostConstruct
    public void init (){
        con = constant;
        con.loadConst();
    }

    // private  String IP="192.168.90.232";
    private  String IP="192.200.200.12213";
    private  int PORT=6000;

    //发送
    public String send(String reqMessage) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            if(constant.getConstVal("FAKA_IP")!=""){
                IP = constant.getConstVal("FAKA_IP");
            }
            if(constant.getConstVal("FAKA_PORT")!=""){
                PORT = Integer.parseInt(constant.getConstVal("FAKA_PORT"));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else{
                    return null;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }
}
