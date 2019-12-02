package com.leimingtech.core.entity;


public class ContentBean extends ContentsEntity implements java.io.Serializable {

	private ArticleEntity article;
	private PictureGroupEntity pictureGroup;
	private ContentLinkEntity link;
	private ContentVideoEntity video;
	private VoteEntity vote;
	private SurveyEntity survey;
	public ArticleEntity getArticle() {
		return article;
	}
	public void setArticle(ArticleEntity article) {
		this.article = article;
	}
	public PictureGroupEntity getPictureGroup() {
		return pictureGroup;
	}
	public void setPictureGroup(PictureGroupEntity pictureGroup) {
		this.pictureGroup = pictureGroup;
	}
	public ContentLinkEntity getLink() {
		return link;
	}
	public void setLink(ContentLinkEntity link) {
		this.link = link;
	}
	public ContentVideoEntity getVideo() {
		return video;
	}
	public void setVideo(ContentVideoEntity video) {
		this.video = video;
	}
	public VoteEntity getVote() {
		return vote;
	}
	public void setVote(VoteEntity vote) {
		this.vote = vote;
	}
	public SurveyEntity getSurvey() {
		return survey;
	}
	public void setSurvey(SurveyEntity survey) {
		this.survey = survey;
	}
	
	
}
