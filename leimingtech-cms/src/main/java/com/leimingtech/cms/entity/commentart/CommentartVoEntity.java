package com.leimingtech.cms.entity.commentart;

import java.util.Date;

/**
 * Created by sunyaoxu on 2017/4/7.
 */
public class CommentartVoEntity {
    private String memberName;  // 用户名
    private String memberPic;  //用户头像
    private String content;   // 评论内容
    private String relayName;  //回复姓名
    private String commentId;//评论Id
    private Date commentTime;  //评论时间

    private Integer supportcount;//赞数
    private Integer opposecount;//踩数
    private Integer replycount;//回复数
    private String memberid;//用户id

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Integer getSupportcount() {
        return supportcount;
    }

    public void setSupportcount(Integer supportcount) {
        this.supportcount = supportcount;
    }

    public Integer getOpposecount() {
        return opposecount;
    }

    public void setOpposecount(Integer opposecount) {
        this.opposecount = opposecount;
    }

    public Integer getReplycount() {
        return replycount;
    }

    public void setReplycount(Integer replycount) {
        this.replycount = replycount;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }




    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPic() {
        return memberPic;
    }

    public void setMemberPic(String memberPic) {
        this.memberPic = memberPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRelayName() {
        return relayName;
    }

    public void setRelayName(String relayName) {
        this.relayName = relayName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }




}
