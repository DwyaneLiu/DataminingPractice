package Apriori2;

import java.util.ArrayList;
import java.util.List;

public class ItemsList {

    //项集频繁度
    private int frequency;
    //项集的项的集合
    private List<String> items;

    //无参构造
    ItemsList(){
        this.frequency = 0;
        this.items = new ArrayList<>();
    }

    //有参构造，用于复制一个相同的ItemList
    ItemsList(ItemsList itemsList){

        this.frequency = itemsList.getFrequency();
        this.items = new ArrayList<>();
        setItems(itemsList.getItems());

    }
    //有参构造，用于创建1项集
    ItemsList(Integer frequency , String item){
        this.frequency = frequency;
        this.items = new ArrayList<>();
        this.items.add(item);
    }
    //设置频繁度
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    //增加一次频繁度
    public void addFrequency(){
        this.frequency ++ ;
    }
    //设置项集
    public void setItems(List<String> items) {
        for (String t : items){
            this.items.add(t);
        }
    }
    //设置项集通过数组
    public void setItemsByArray(String items[]) {
        for (String t : items){
            this.items.add(t);
        }
    }
    //获取频繁度
    public int getFrequency() {
        return frequency;
    }
    //获取项的集合List
    public List<String> getItems() {
        return items;
    }
    //添加项
    public void  addItem(String s){
        this.getItems().add(s);
    }
}
