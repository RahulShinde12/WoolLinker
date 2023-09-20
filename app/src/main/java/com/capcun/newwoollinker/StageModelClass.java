package com.capcun.newwoollinker;

public class StageModelClass {

    String title,sub_tit,desc,stage_no;

    Boolean expandable;

    public StageModelClass(String title, String sub_tit, String desc,String stage_no) {
        this.title = title;
        this.sub_tit = sub_tit;
        this.desc = desc;
        this.stage_no = stage_no;
        this.expandable = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_tit() {
        return sub_tit;
    }

    public void setSub_tit(String sub_tit) {
        this.sub_tit = sub_tit;
    }

    public String getDesc() {
        return desc;
    }

    public String getStage_no() {
        return stage_no;
    }

    public void setStage_no(String stage_no) {
        this.stage_no = stage_no;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }
}
