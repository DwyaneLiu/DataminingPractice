import java.util.ArrayList;
import java.util.List;

public class Item{

    int c_num;      //项中的商品数量
    int frequent;   //项的频度
    List<Integer> commodity;    //项的商品，使用integer类型的1，2，3，4代表商品
    //空构造函数
    Item(){
        commodity = new ArrayList<>();
        c_num = 0;
        frequent = 0;
    }
    //由传入的item复制新建一个item 构造函数
    Item(Item item ){
        c_num = item.c_num;
        frequent = item.frequent;
        commodity = new ArrayList<>();
        for (Integer comm : item.commodity){
            this.commodity.add(comm);
        }
    }
    //用数组初始化一个包含商品的项
    Item( int commoditys[] ){
        commodity = new ArrayList<>();
        for (int i = 0 ; i< commoditys.length;i++) {

            commodity.add(commoditys[i]);
        }
        c_num = commoditys.length;
        frequent = 0;
    }

    @Override
    public boolean equals(Object obj) {
        Item item = new Item((Item) obj);
        if(this.contains(item))
        return true;
        return false;
    }
    public boolean contains(Item item_2){
        for (int c : item_2.commodity){
            if (!this.commodity.contains(c)){
                return false;
            };
        }
        return true;
    }
}