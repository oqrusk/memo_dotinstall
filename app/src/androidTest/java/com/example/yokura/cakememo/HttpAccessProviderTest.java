package com.example.yokura.cakememo;

import android.support.test.runner.AndroidJUnit4;

import com.example.yokura.cakememo.controller.dao.PrefDao;
import com.example.yokura.cakememo.controller.provider.HttpAccessProvider;
import com.example.yokura.cakememo.model.Prefecture;
import com.example.yokura.cakememo.model.system.MyContract;
import com.squareup.okhttp.internal.http.HttpEngine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by YOKURA on 4/19/15.
 */
@RunWith(Enclosed.class)
public class HttpAccessProviderTest {

    @RunWith(AndroidJUnit4.class)
    public static class 県名の一覧を取得する {
        String sut;

        @Before
        public void setUp() throws Exception {
        }

        @Test
        public void 県名が取得できること() throws Exception {
            HttpAccessProvider hap = new HttpAccessProvider();
            String sut = hap.getJsonString(MyContract.GNaviApis.URL_PREF_S);

            assertTrue(sut.contains("pref"));

        }

    }

}
