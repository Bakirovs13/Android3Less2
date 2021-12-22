package kg.geektech.andrroid3less2.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {

    int id;
    String title;
    String content;
    @SerializedName("user")
    int userID;
    @SerializedName("group")
    int groupID;


    public Post(String title, String content, int userID,int groupId) {
        this.title = title;
        this.content = content;
        this.userID = userID;
        this.groupID = groupId;
    }


    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    @Override
    public String toString() {
        return "Post{" +
                "title=" + title +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", userID=" + userID
               ;
    }
}