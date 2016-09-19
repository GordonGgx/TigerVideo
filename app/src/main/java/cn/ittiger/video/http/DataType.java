package cn.ittiger.video.http;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public enum DataType {

    NET_EASY(1),
    CNEWS(2),
    TTKB(3);

    int mValue;

    DataType(int value) {

        mValue = value;
    }

    public int value() {

        return mValue;
    }
}
