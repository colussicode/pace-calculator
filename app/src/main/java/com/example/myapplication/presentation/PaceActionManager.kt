package com.example.myapplication.presentation

import com.example.myapplication.data.PaceDao


class PaceActionManager(private val paceDao: PaceDao) {

     fun deleteAllPaces() {
        paceDao.deleteAllPaces()
    }
}