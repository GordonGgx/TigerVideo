package cn.ittiger.video.http.parse;

import cn.ittiger.video.http.DataType;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class ResultParseFactory {

    public static ResultParse create(DataType type) {

        ResultParse parse = null;
        switch (type) {
            case NET_EASY:
                parse = new NetEasyResultParse();
                break;
            case CNEWS:
                parse = new CNewsResultParse();
                break;
            case TTKB:
                parse = new TtKbResultParse();
                break;
        }
        return parse;
    }
}