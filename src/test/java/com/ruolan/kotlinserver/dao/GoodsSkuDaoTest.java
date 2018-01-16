package com.ruolan.kotlinserver.dao;


import com.ruolan.kotlinserver.model.GoodsSku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsSkuDaoTest {

    @Autowired
    private GoodsSkuDao skuDao;


    @Test
    public void testGoodsSkuDao() {
        GoodsSku goodsSku = skuDao.selectByPrimaryKey(6);
//        goodsSku.getId()
        System.out.println("查询出来的数据id:" + goodsSku.getId());
    }

}
