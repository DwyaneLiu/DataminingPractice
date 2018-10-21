package Apriori2;

import java.util.ArrayList;
import java.util.List;

public class Test {

    static private Apriori2 apriori = new Apriori2();
    static public void main(String args[]){

        //初始化
        String items[][] = {{"a","c","d"},{"b","c","e"},{"a","b","c"},{"a","b","e","c","d","f"},{"a","c","f","e"}};
        List<ItemsList> lists  = new ArrayList<ItemsList>();
        for (int i = 0; i< items.length;i++) {
            String temp[] = items[i];
            ItemsList itemsList = new ItemsList();
            itemsList.setItemsByArray(temp);
            lists.add(itemsList);
        }
        ItemsList items1 = new ItemsList();
        items1.addItem("a");
        items1.addItem("b");
        items1.addItem("c");
//        ItemsList items2 = new ItemsList();
//        items2.addItem("a");
//        items2.addItem("e");
//        items2.addItem("p");
//        测试取出事务数据空中的项集集合中的单个项，并将单个项组成项集
//        testGet_1_item_lists(lists);
//        测试频繁项集集合的获取
//        testFrequency(apriori.get_1_item_lists(lists));
//        测试连接
//        testConnect(items1,items2);
//        测试频繁度获取
//        testFrequency(items1,lists);
//        测试子集
//        testSubList(items1);
//        测试是否包含
//        testIfContained(items1,lists);
//        测试apriori
        testApriori(lists);
    }
    static public void testGet_1_item_lists(List<ItemsList> lists){
        for (ItemsList itemsList: apriori.get_1_item_lists(lists)){
            for (String item : itemsList.getItems()){
                System.out.print(item);
            }
            System.out.println("频繁度为："+itemsList.getFrequency());
        }
    }

    static  public  void  testFrequency(List<ItemsList> lists){


        for (ItemsList itemsList: apriori.get_frequency_lists(lists)){
            for (String item : itemsList.getItems()){
                System.out.print("{"+item+"、}");
            }
            System.out.println("频繁度为："+itemsList.getFrequency());
        }
    }
    //
    static  public void testConnect(ItemsList itemsList1,ItemsList itemsList2){
        System.out.print("连接后的项集为：{");
        if (apriori.connect_items(itemsList1,itemsList2) !=  null)
         for (String s :apriori.connect_items(itemsList1,itemsList2).getItems()){
             System.out.print(s+"、");
         }
        System.out.print("}");

    }
    //
    static public void testFrequency(ItemsList itemsList1,List<ItemsList> itemsList2){
            System.out.println("该项集的频繁度为："+apriori.get_frequency(itemsList1,itemsList2));
    }
    //测试子集
    static public void testSubList(ItemsList itemsList){

        for (ItemsList items: apriori.get_subLists(itemsList)){
            for (String item : items.getItems()){
                System.out.print(item);
            }
            System.out.println();
        }
    }
    //测试是否包含
    static public void testIfContained(ItemsList itemsList,List<ItemsList> lists) {
        if (apriori.if_be_contained(itemsList, lists)) {
            System.out.println("是它的子集");
        } else System.out.println("不是");
    }

    //测试ap
    static public void testApriori(List<ItemsList> dbLists){

        apriori.get_ariori_lists(dbLists);

    }
}
