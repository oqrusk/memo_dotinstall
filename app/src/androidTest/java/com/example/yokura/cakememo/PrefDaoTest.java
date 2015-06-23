package com.example.yokura.cakememo;

import android.support.test.runner.AndroidJUnit4;

import com.example.yokura.cakememo.controller.dao.PrefDao;
import com.example.yokura.cakememo.controller.provider.HttpAccessProvider;
import com.example.yokura.cakememo.model.Prefecture;
import com.example.yokura.cakememo.model.system.MyContract;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by YOKURA on 4/19/15.
 */
@RunWith(Enclosed.class)
public class PrefDaoTest {

    @RunWith(AndroidJUnit4.class)
    public static class 県名の一覧を取得する {

        @Before
        public void setUp() throws Exception {
        }

        @Test
        public void 県名リストが取得できること() throws Exception {
            List<Prefecture> sut;
            sut = PrefDao.getPrefList();
            assertThat(sut.size(), is(47));
        }
    }
}
