package Apriori2;
import java.util.*;

public class Apriori2 {

    //变量：最小支持度
    final static private int MiniSupport = 2;

    //函数：返回apriori算法生成的k项频繁集合
    static public List<ItemsList> get_ariori_lists(List<ItemsList> DBlists){

        //获取频繁1项集集合
        List<ItemsList> frequency_1_lists = get_frequency_lists(get_1_item_lists(DBlists));
        List<ItemsList> frequency_k_lists = frequency_1_lists;

        //迭代返回k频繁k项集
        int k = 1;
        while (frequency_k_lists.size() != 0) {
            System.out.print("频繁"+k+"项集有:");
            print_Lists(frequency_k_lists);
            //将经过剪枝的k项集中找出频繁的
            frequency_k_lists = get_frequency_lists( get_k_item_lists( frequency_k_lists,DBlists ) );
            k ++;
        }
        System.out.println();
        //返回频繁K项集集合
        return frequency_k_lists;
    }

    //函数：返回k项集合
    static public  List<ItemsList> get_k_item_lists(List<ItemsList> lists,List<ItemsList> DBlists){

        List<ItemsList> k_item_lists = new ArrayList<>();
        boolean flag = true;
        //传入的项集集合自连接，得到候选集合
        for (int i = 0 ;i<lists.size();i++){
            for (int j = i+1 ; j< lists.size();j++){
                flag = true;
                //调用连接函数
                ItemsList itemsList_temp = connect_items(lists.get(i),lists.get(j));
                if (itemsList_temp != null){
                    //如果连接后的集合不重复
                    if (!if_be_contained(itemsList_temp,k_item_lists)){
                        //如果连接后的k项集的k-1项子集是频繁(使用先验原则剪枝)的则加入k项集集合
                        for(ItemsList items:get_subLists(itemsList_temp)){
                            if (!if_be_contained(items,lists)) {
                                flag = false;
                                break;
                            }
                        }
                        if(flag) {
                            //设置这个项集的频繁度
                            itemsList_temp.setFrequency(get_frequency(itemsList_temp,DBlists));
                            //将这个项集加入项集集合中去
                            k_item_lists.add(itemsList_temp);
                        }
                    }
                }
            }
        }
        //返回得到的k项集集合
        return k_item_lists;
    }

    //函数：返回只包含 1 个项的项集的集合
    static public List<ItemsList> get_1_item_lists(List<ItemsList> itemsLists){

        //新建一个List类来存储频繁1项集的集合
        List<ItemsList> onelists_list = new ArrayList<>();
        //使用HashMap判断该项是否已经被扫描了
        HashMap<String,ItemsList> itemMap = new HashMap<>();
        //循环取出事务数据库中的项
        for(ItemsList itemsList: itemsLists){
            for ( String item : itemsList.getItems()){
                //如果项不存在于自定义的itemMap中，则加入其中
                //并且加入到频繁1项集集合中去
                if (!itemMap.containsKey(item)){
                    ItemsList itemsList_1 = new ItemsList(1,item);
                    itemMap.put(item,itemsList_1);
                }
                //否则频繁度+1
                else {
                    itemMap.get(item).addFrequency();
                }
            }
        }
        //通过迭代器取出HashMap里所有的value并加入频繁1项集的集合中
        Collection<ItemsList> values =  itemMap.values();
        Iterator<ItemsList> iter = values.iterator() ;
        while(iter.hasNext()){
            onelists_list.add(iter.next());
        }
        return  onelists_list;
    }

    //函数：返回传入的项集集合中的频繁项集
    static public List<ItemsList> get_frequency_lists(List<ItemsList> itemsLists){

        List<ItemsList> frequency_lists = new ArrayList<>();
        for (ItemsList itL : itemsLists){
            //如果大于等于最小支持度，则加入频繁项集集合中去
            if (itL.getFrequency() >= MiniSupport)
                frequency_lists.add(new ItemsList(itL));
        }
        return frequency_lists;
    }

    //函数：返回传入的两个项集连接后的项集，若无法连接返回空值
    static public ItemsList connect_items(ItemsList itemsList1,ItemsList itemsList2){
        //把itemList2放入HashMap中去
        HashMap<String,String> itemMap = new HashMap<>();
        for (String s : itemsList2.getItems()){
            itemMap.put(s,s);
        }
        //设置一个暂时的item，以备连接
        String temp_item = "";
        //设置一个两个项集中相同的项的数量的变量
        int the_same_num = 0;
        //设置这两个项若可以连接需要几个项相同
        int flag = itemsList1.getItems().size() - 1;
        for (int i = 0 ;i< itemsList1.getItems().size();i++){
            if(itemMap.containsKey(itemsList1.getItems().get(i))){
                the_same_num ++;
            }
            else{
                //若存在不相同的项，则暂时储存下来，已备连接使用
                temp_item = itemsList1.getItems().get(i);
            }
        }
        //判断是否相同项的个数是否达到需要的值
        if(the_same_num == flag){
            ItemsList newItemList = new ItemsList(itemsList2);
            newItemList.getItems().add(temp_item);
            return newItemList;
        }
        //否则返回空，代表不可连接
        return null;

    }

    //函数：返回传入的项集在事务数据库中的频繁度
    static public int get_frequency(ItemsList itemsList,List<ItemsList> DBlists){

        //把传入的项集itemList放入HashMap中去
        HashMap<String,String> itemMap = new HashMap<>();
        for (String s : itemsList.getItems()){
            itemMap.put(s,s);
        }
        //设置一个值，用来存储该项集的频繁度。
        int frequency = 0;
        //设置一个值，用来确定一个数据库项集中需要有几个项与传入的项集匹配
        int the_same_num = itemsList.getItems().size();
        //设置一个记录，判断被扫描的项集中有多少个于HashMap中匹配
        int flag = 0;
        //遍历数据库
        for (ItemsList itemsList_temp : DBlists){
            flag = 0;
            for (String item_temp : itemsList_temp.getItems()){
                if(itemMap.containsKey(item_temp)){
                    flag ++ ;
                }
            }
            if (flag == the_same_num){
                frequency ++;
            }
        }
        return frequency;
    }

    //函数：返回一个k项集的所有k-1子集
    static public List<ItemsList> get_subLists(ItemsList itemsList){

        List<ItemsList> itemsSubLists  = new ArrayList<>();
        for (int i = 0;i<itemsList.getItems().size();i++){
            ItemsList itemsList_sub = new ItemsList(itemsList);
            itemsList_sub.getItems().remove(i);
            itemsSubLists.add(itemsList_sub);
        }
        return itemsSubLists;

    }

    //函数：判断一个项集是否是另一个项集集合的子集
    static public boolean if_be_contained(ItemsList itemsList1,List<ItemsList> lists){

        //把传入的项集itemList1放入HashMap中去
        HashMap<String,String> itemMap = new HashMap<>();
        for (String s : itemsList1.getItems()){
            itemMap.put(s,s);
        }

        //设置一个标准，相同多少项两个项集才是相等
        int flag = itemsList1.getItems().size();
        //设置一个标记，记录每次与项集比较的相同的项的个数
        int the_same_num = 0;

        //遍历项集的集合，用标记法判断是否是其子集
        for(ItemsList itemsList_temp : lists){
            the_same_num = 0;
            for (String item : itemsList_temp.getItems()){
                if (itemMap.containsKey(item))
                    the_same_num ++ ;
            }
            if (the_same_num == flag){
                return true;
            }
        }
        return false;

    }

    //函数：打印传入的项集集合
    static public void print_Lists(List<ItemsList> lists){
        for (ItemsList items : lists){
            System.out.print("{");
            for (String item : items.getItems()){
                System.out.print(item+"、");
            }
            System.out.print("}");
        }
        System.out.println();
    }


}
