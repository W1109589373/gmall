package com.atguigu.gmall.payment;

/**
 * @EnumName test001
 * @Description
 * @Author WangLL
 * @CreateDate 2019/8/5 13:52
 * @Version 1.0
 */
public enum test001 {

    XIAZAI(1,"xiazai"),FANGWEN(2,"fangwen");

    int index;
    String name;

    private test001(int index,String name){
        this.index = index;
        this.name = name;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index=index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
