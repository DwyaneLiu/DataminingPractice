import java.util.ArrayList;
import java.util.List;

public class Apriori {

    //设置最小支持度为2
    final static public int MINISupport = 2;
    //事务数据库类
    static public class DataBase{

        int sum; //事务的总数
        List<Item> items_list; //项集

        //构造函数初始化
        DataBase(){
            sum = 0;
            items_list = new ArrayList<>();
        }

        //添加项的函数
        public void addItem(Item item){
            items_list.add(item);
        }

    }
    //主函数
    static public void main(String args[]){
        //初始化数据库
        DataBase dataBase = new DataBase();
        int items[][] = {{1,3,4},{2,3,5},{1,2,3,5},{2,5}};
        for (int i = 0; i< items.length;i++){
            int temp[] = items[i];
            Item item = new Item(temp);
            dataBase.addItem(item);
            dataBase.sum++;
        }
        //找出一个项的项集
        List<Item> list_1 = find_frequent_1_item(dataBase);
        //找出两个项的项集
        List<Item> list_k = find_frequent_k_item(list_1, 1 ,dataBase);
        //创建一个List用来存储最后的结果
        List<Item> list_frequent_k = new ArrayList<>();
        //迭代寻找最大频繁项集
        for (int k = 2; k < list_1.size();k++) {
            if (list_k.size() != 0){
                list_frequent_k = list_k;
                System.out.println("找出的"+k+"频繁项集是");
                for (Item c : list_frequent_k){
                    System.out.println(c.commodity+"它的绝对支持度为："+c.frequent);
                }
            }
            list_k =find_frequent_k_item(list_k,k,dataBase);
        }
        //


    }
    //找出只包含一个商品的项的频繁项集
    static public List<Item> find_frequent_1_item(DataBase dataBase){

//        int i = 0;int j = 0;
        List<Item> list_1 = new ArrayList<>(); //获取每个项为一个商品的项集

        for (int i = 0;i<dataBase.sum;i++) {
            for (int j = 0;j<dataBase.items_list.get(i).c_num;j++ ) {  //如果商品不为空
                Integer temp = dataBase.items_list.get(i).commodity.get(j);//取到项中的单个商品
                Item item = new Item(); //并且封装成作为一个项
                item.c_num = 1;
                item.frequent = 1;
                item.commodity.add(temp);
                if (list_1.contains(item)) { //判断是否已经加入过list_1了
                    list_1.get(list_1.indexOf(item)).frequent++;//若项已经存入则项的频度+1
                } else {
                    list_1.add(item); //否则加入项集list_1
                }
            }
        }
        //往下扫描项
        int n = 0;
        for (int i = 0;i< list_1.size();i++){ //若list_1中的项小于最小支持度则删除掉
            if (list_1.get(i).frequent< MINISupport) {
                list_1.remove(i);
            }
        }
        return list_1;
    }
    //找出"最终"的k项的频繁项集
    static public List<Item> find_frequent_k_item(List<Item> list_p,int k,DataBase dataBase){

        List<Item> item_list_k = apriori_gen(list_p, k ,dataBase);//由apriori算法生成候选k 的项集

        for (int i = 0 ;i< item_list_k.size();i++){

            int item_frenquent = find_item_frequent(item_list_k.get(i),dataBase);//计算出k个商品的项的绝对频繁度

            //若小于最小支持度，则移除
            if (item_frenquent < MINISupport){
                item_list_k.remove(i);
                i = i - 1;
            }else {
                //否则写入最小支持度
                item_list_k.get(i).frequent = item_frenquent;
            }




        }
        return item_list_k;
    }
    //根据apriori算法连接出"候选"k+1频繁项集
    static public List<Item> apriori_gen(List<Item> list , int k, DataBase dataBase){  //k代表第几层，即每个项中的商品的数量
        //新建一个k+1层的项集
        List<Item> new_list = new ArrayList<>();
        //两层循环遍历k频繁项集，并判断是否可以连接
        for (int i=0;i < list.size();i++){
            for (int j = i+1;j < list.size();j++) {
                //判断是否可以连接
                if (connect_item(list.get(i),list.get(j))!= null) {
                    //连接产生候选
                    Item item_temp = new Item(connect_item(list.get(i), list.get(j)));
                    //判断连接后的k项是否有子集不在k-1的频繁项集中，不存在则不加入
                    if (if_all_exist_in_list(find_item_sublist(item_temp), list)) {
                        //加入项集
                        new_list.add(item_temp);
                    }
                }
            }
        }
        return new_list; // 返回k+1层的项集
    }
    //找出某个项在数据库中的频度是多少
    static public int find_item_frequent(Item item,DataBase dataBase){
        int sum = dataBase.sum;
        //注意：该处在运行过程中 可能会出现指针越界或者循环匹配不够等问题，需要进一步修改
        for (int i = 0;i< sum; i++) {
            boolean flag = true;
            for (int item_commodity : item.commodity) {

                if (!dataBase.items_list.get(i).commodity.contains(item_commodity)) {
                    flag = false;
                    continue; //判断item是否存在于数据库中第i项之中，若某一个商品不存在，则item全部不存在了
                }
            }
            if(flag){
                item.frequent ++;
            }
        }
        return item.frequent ; //返回该项在数据库中的频度
    }
    //连接两个项
    static public Item connect_list(Item item_1,Item item_2){

        Item item_new = new Item();
        for (int i = 0;i< item_1.c_num ;i++){
            item_new.commodity.add(item_1.commodity.get(i)); //将item_1的所有项加入新项集
        }
        item_new.commodity.add(item_2.commodity.get(item_2.c_num-1));//将item_2的最后一个元素加入连接后的项集

        item_new.c_num = item_1.c_num+1;//设置连接后的新item的商品数量

        return item_new; //返回连接后的项集
    }
    //找出一个k+1项的k项子集
    static public List<Item> find_item_sublist(Item item){
        List<Item> item_subList = new ArrayList<>();
        for (int i = 0;i < item.commodity.size() ; i++){
            Item item_sub = new Item(item);
            item_sub.commodity.remove(i);
            item_subList.add(item_sub);
        }
        return item_subList;
    }
    //判断项集中的项是否都存在于另一个项集中,即判断是否需要减枝
    static public boolean if_all_exist_in_list(List<Item> item_1_list,List<Item> item_2_list){

        for (int i = 0;i < item_1_list.size();i++){
            for (int j = 0;j< item_2_list.size();j++){
                if (item_2_list.get(j).contains(item_1_list.get(i))){
                    return true;
                }
            }
        }
        return false;
    }
    //判断两个项是否可以连接，并连接
    static public Item connect_item(Item item_1,Item item_2){
        for (int i = 0;i < item_1.commodity.size();i++){
            Item item = new Item(item_1);
            item.commodity.remove(i);
            boolean flag = true;
            //判断是否与item_2有重合
            for (int t : item.commodity){
                if (!item_2.commodity.contains(t))
                    flag = false;
                if (item.commodity.size()==0)
                    flag = true;
            }
            if (flag) {
                Item item_c = new Item(item_2);
                item_c.frequent = 0;
                item_c.commodity.add(item_1.commodity.get(i));
                item_c.c_num = item_c.commodity.size();
                return item_c;
            }
        }
        return null;
    }

}