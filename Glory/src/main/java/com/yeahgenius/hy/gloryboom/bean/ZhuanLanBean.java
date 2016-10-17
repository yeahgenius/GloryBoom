package com.yeahgenius.hy.gloryboom.bean;

import java.io.Serializable;

public class ZhuanLanBean implements Serializable
{
    @Override
    public String toString()
    {
        return "ZhuanLanBean{" +
                "isTitleImageFullScreen=" + isTitleImageFullScreen +
                ", rating='" + rating + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", publishedTime='" + publishedTime + '\'' +
                ", links=" + links +
                ", author=" + author +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", titleImage='" + titleImage + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                ", href='" + href + '\'' +
                ", meta=" + meta +
                ", commentPermission='" + commentPermission + '\'' +
                ", snapshotUrl='" + snapshotUrl + '\'' +
                ", canComment=" + canComment +
                ", slug=" + slug +
                ", commentsCount=" + commentsCount +
                ", likesCount=" + likesCount +
                '}';
    }

    /**
     * isTitleImageFullScreen : false
     * rating : none
     * sourceUrl :
     * publishedTime : 2016-06-01T08:14:32+08:00
     * links : {"comments":"/api/posts/21278873/comments"}
     * author : {"profileUrl":"https://www.zhihu.com/people/drlianggao","bio":"运动骨科医师，公众号: SportsMedChina","hash":"63c1a23873905ac60fef9976227daa3f","name":"高亮","isOrg":false,"slug":"drlianggao","avatar":{"id":"582242f68dd02d202f6976421985e92d","template":"https://pic2.zhimg.com/{id}_{size}.png"},"description":"运动骨科医师，医学/哲学博士 (MD/Phd)，谢绝任何有关个体的诊断、治疗、康复等话题邀请。《运动医学》专栏 (http://zhuanlan.zhihu.com/sportsmedicine)及微信公众号 (SportsMedChina) 不定时更新，欢迎关注。已委托维权骑士（http://www.rightknights.com）为本人知乎问答及文章进行维权。"}
     * url : /p/21278873
     * title : 【分答】开通，欢迎提问
     * titleImage : https://pic3.zhimg.com/8b3eb7f6b99cee99cbdddc978b303a06_r.jpg
     * summary :
     * content :
     * state : published
     * href : /api/posts/21278873
     * meta : {"previous":null,"next":null}
     * commentPermission : anyone
     * snapshotUrl :
     * canComment : false
     * slug : 21278873
     * commentsCount : 4
     * likesCount : 13
     */

    private boolean isTitleImageFullScreen;
    private String rating;
    private String sourceUrl;
    private String publishedTime;
    /**
     * comments : /api/posts/21278873/comments
     */

    private LinksBean links;
    /**
     * profileUrl : https://www.zhihu.com/people/drlianggao
     * bio : 运动骨科医师，公众号: SportsMedChina
     * hash : 63c1a23873905ac60fef9976227daa3f
     * name : 高亮
     * isOrg : false
     * slug : drlianggao
     * avatar : {"id":"582242f68dd02d202f6976421985e92d","template":"https://pic2.zhimg.com/{id}_{size}.png"}
     * description : 运动骨科医师，医学/哲学博士 (MD/Phd)，谢绝任何有关个体的诊断、治疗、康复等话题邀请。《运动医学》专栏 (http://zhuanlan.zhihu.com/sportsmedicine)及微信公众号 (SportsMedChina) 不定时更新，欢迎关注。已委托维权骑士（http://www.rightknights.com）为本人知乎问答及文章进行维权。
     */

    private AuthorBean author;
    private String url;
    private String title;
    private String titleImage;
    private String summary;
    private String content;
    private String state;
    private String href;
    /**
     * previous : null
     * next : null
     */

    private MetaBean meta;
    private String commentPermission;
    private String snapshotUrl;
    private boolean canComment;
    private int slug;
    private int commentsCount;
    private int likesCount;

    public boolean isIsTitleImageFullScreen() { return isTitleImageFullScreen;}

    public void setIsTitleImageFullScreen(boolean isTitleImageFullScreen) { this.isTitleImageFullScreen = isTitleImageFullScreen;}

    public String getRating() { return rating;}

    public void setRating(String rating) { this.rating = rating;}

    public String getSourceUrl() { return sourceUrl;}

    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl;}

    public String getPublishedTime() { return publishedTime;}

    public void setPublishedTime(String publishedTime) { this.publishedTime = publishedTime;}

    public LinksBean getLinks() { return links;}

    public void setLinks(LinksBean links) { this.links = links;}

    public AuthorBean getAuthor() { return author;}

    public void setAuthor(AuthorBean author) { this.author = author;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getTitleImage() { return titleImage;}

    public void setTitleImage(String titleImage) { this.titleImage = titleImage;}

    public String getSummary() { return summary;}

    public void setSummary(String summary) { this.summary = summary;}

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}

    public String getState() { return state;}

    public void setState(String state) { this.state = state;}

    public String getHref() { return href;}

    public void setHref(String href) { this.href = href;}

    public MetaBean getMeta() { return meta;}

    public void setMeta(MetaBean meta) { this.meta = meta;}

    public String getCommentPermission() { return commentPermission;}

    public void setCommentPermission(String commentPermission) { this.commentPermission = commentPermission;}

    public String getSnapshotUrl() { return snapshotUrl;}

    public void setSnapshotUrl(String snapshotUrl) { this.snapshotUrl = snapshotUrl;}

    public boolean isCanComment() { return canComment;}

    public void setCanComment(boolean canComment) { this.canComment = canComment;}

    public int getSlug() { return slug;}

    public void setSlug(int slug) { this.slug = slug;}

    public int getCommentsCount() { return commentsCount;}

    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount;}

    public int getLikesCount() { return likesCount;}

    public void setLikesCount(int likesCount) { this.likesCount = likesCount;}

    public static class LinksBean
    {
        private String comments;

        public String getComments() { return comments;}

        public void setComments(String comments) { this.comments = comments;}
    }

    public static class AuthorBean
    {
        private String profileUrl;
        private String bio;
        private String hash;
        private String name;
        private boolean isOrg;
        private String slug;
        /**
         * id : 582242f68dd02d202f6976421985e92d
         * template : https://pic2.zhimg.com/{id}_{size}.png
         */

        private AvatarBean avatar;
        private String description;

        public String getProfileUrl() { return profileUrl;}

        public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl;}

        public String getBio() { return bio;}

        public void setBio(String bio) { this.bio = bio;}

        public String getHash() { return hash;}

        public void setHash(String hash) { this.hash = hash;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public boolean isIsOrg() { return isOrg;}

        public void setIsOrg(boolean isOrg) { this.isOrg = isOrg;}

        public String getSlug() { return slug;}

        public void setSlug(String slug) { this.slug = slug;}

        public AvatarBean getAvatar() { return avatar;}

        public void setAvatar(AvatarBean avatar) { this.avatar = avatar;}

        public String getDescription() { return description;}

        public void setDescription(String description) { this.description = description;}

        public static class AvatarBean
        {
            private String id;
            private String template;

            public String getId() { return id;}

            public void setId(String id) { this.id = id;}

            public String getTemplate() { return template;}

            public void setTemplate(String template) { this.template = template;}
        }
    }

    public static class MetaBean
    {
        private Object previous;
        private Object next;

        public Object getPrevious() { return previous;}

        public void setPrevious(Object previous) { this.previous = previous;}

        public Object getNext() { return next;}

        public void setNext(Object next) { this.next = next;}
    }
}
