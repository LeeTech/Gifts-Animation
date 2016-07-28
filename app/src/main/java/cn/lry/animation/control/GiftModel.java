package cn.lry.animation.control;

/**
 * ClassName: GiftModel
 * Description TODO 礼物模型
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/6/23 10:59
 */
public class GiftModel {

    private String giftId;

    private String giftName;

    private int giftCuont;

    private String giftPic;

    private String sendUserId;

    private String sendUserName;

    private String sendUserPic;

    public static GiftModel create(String giftId, String giftName, int giftCont, String giftPic, String sendUserId,
                                   String sendUserName, String sendUserPic) {
        GiftModel giftModel = new GiftModel();
        giftModel.setGiftId(giftId);
        giftModel.setGiftName(giftName);
        giftModel.setGiftCuont(giftCont);
        giftModel.setGiftPic(giftPic);
        giftModel.setSendUserId(sendUserId);
        giftModel.setSendUserName(sendUserName);
        giftModel.setSendUserPic(sendUserPic);
        return giftModel;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftCuont() {
        return giftCuont;
    }

    public void setGiftCuont(int giftCuont) {
        this.giftCuont = giftCuont;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUserPic() {
        return sendUserPic;
    }

    public void setSendUserPic(String sendUserPic) {
        this.sendUserPic = sendUserPic;
    }

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }
}
