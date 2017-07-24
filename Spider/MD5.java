/**
 * Created by Administrator on 2017/7/24.
 */
/*
* 传入参数，一个字节数组
* 传出参数，字节数组的MD5结果字符串
* */
public class MD5 {
    public static String getMD5(byte[] sourse){
        String s = null;
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};//用来将字节转换成十六进制表示的字符
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(sourse);
            byte tmp[]=md.digest();//MD5的计算结果是一个128位的长整数，用字节表示就是16个字节
            char str[] = new char[16*2];//每个字节用十六进制表示的话，使用两个字节
            int k =0;//表示转换结果中对应的字符位置
            for (int i=0;i<16;i++){
                //从第一个字节开始，将MD5的每个字节转换成十六进制字符
                //取第i个字节
                byte byte0 = tmp[i];
                //取字符中高4位的数字转换,>>>为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0>>>4&0xf];
                //取字符中的低4位的数字转换
                str[k++] = hexDigits[byte0&0xf];
            }
            s=new String(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
}
